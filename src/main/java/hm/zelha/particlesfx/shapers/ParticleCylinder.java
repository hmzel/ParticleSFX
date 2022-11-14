package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.CircleInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.Rotation;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ParticleCylinder extends ParticleShaper {

    //TODO: make it so using rotation methods every tick doesnt cause wacky stuff to happen if all locations are evenly spaced

    private final List<CircleInfo> circles = new ArrayList<>();
    //circumference tracker
    private final List<Double> cirTracker = new ArrayList<>();
    private final CircleInfo circleHelper = new CircleInfo(new LocationSafe(Bukkit.getWorld("world"), 0, 0, 0), 0, 0);
    private final Rotation rotHelper = new Rotation();
    private boolean rotateCircles = true;
    private boolean recalculate = true;
    private double totalArea = 0;
    private int circleFrequency;

    public ParticleCylinder(Particle particle, int circleFrequency, double particleFrequency, CircleInfo... circles) {
        super(particle, particleFrequency);

        Validate.isTrue(circles != null && circles.length >= 2, "Array must contain 2 or more CircleInfos!");

        for (CircleInfo circle : circles) {
            addCircle(circle);
        }

        setCircleFrequency(circleFrequency);
        setWorld(this.circles.get(0).getCenter().getWorld());
        start();
    }

    public ParticleCylinder(Particle particle, CircleInfo... circles) {
        this(particle, circles.length * 5, 100, circles);
    }

    @Override
    public void display() {
        double totalDist = getTotalDistance();
        double distToTravel = 0;
        double continuation = 0;
        int circle = 0;
        int current = overallCount;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        //need to do this here to make sure circumference & total area aren't screwed up by outside modification of locations
        recalculateIfNeeded(null);

        for (int i = 0; i < circles.size(); i++) {
            if (circles.get(i).isModified()) {
                recalculate = true;
            }
        }

        if (recalculate) {
            recalcCircumferenceAndArea();
        }

        circleHelper.inherit(circles.get(0));
        rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());

        main:
        for (int i = 0; i < circleFrequency; i++, distToTravel = totalDist / (circleFrequency - 1)) {
            CircleInfo circle1 = circles.get(circle);
            CircleInfo circle2 = circles.get(circle + 1);
            double distance = circleHelper.getCenter().distance(circle2.getCenter());
            double particleAmount = Math.floor(particleFrequency * (cirTracker.get(i) / totalArea));

            if (particleAmount <= 0) {
                particleAmount = 1;
            }

            if (trackCount) {
                if (current >= particleAmount) {
                    current -= particleAmount;
                    continue;
                }

                if (!hasRan) {
                    distToTravel *= i;
                }
            }

            while (distToTravel > distance) {
                //i think this only happens because of double inconsistency so its fine to just ignore
                //in every case where this breaks the while loop the final circle winds up exactly where it should be,
                //anything off has to be in the millionths of a decimal
                if (circle + 2 >= circles.size()) break;

                distToTravel -= distance;
                circle++;
                circle1 = circles.get(circle);
                circle2 = circles.get(circle + 1);
                distance = circle1.getCenter().distance(circle2.getCenter());

                circleHelper.inherit(circle1);
                rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());
            }

            double control = distToTravel / circle1.getCenter().distance(circle2.getCenter());

            if (!Double.isFinite(control)) {
                control = 1;
            }

            LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply(distToTravel);
            circleHelper.getCenter().add(vectorHelper);
            //adding pitch, yaw, roll, x and z radius changes based on (circle2 - circle1) * (current position / distance between circles)
            circleHelper.setXRadius(circleHelper.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) * control));
            circleHelper.setZRadius(circleHelper.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) * control));
            rotHelper.add((circle2.getPitch() - circle1.getPitch()) * control, (circle2.getYaw() - circle1.getYaw()) * control, (circle2.getRoll() - circle1.getRoll()) * control);

            double increase = Math.PI * 2 / particleAmount;
            double start = 0;

            if (!Double.isFinite(increase)) {
                increase = Math.PI * 2;
            }

            if (trackCount) {
                start = increase * current;
            }

            for (double radian = start + continuation; true; radian += increase) {
                if (radian > (Math.PI * 2) + continuation) {
                    continuation = radian - Math.PI * 2;
                    break;
                }

                Particle particle = getCurrentParticle();

                vectorHelper.setX(circleHelper.getXRadius() * Math.cos(radian));
                vectorHelper.setY(0);
                vectorHelper.setZ(circleHelper.getZRadius() * Math.sin(radian));
                locationHelper.zero().add(circleHelper.getCenter());

                if (mechanic != null) {
                    mechanic.apply(particle, locationHelper, vectorHelper);
                }

                rotHelper.apply(vectorHelper);
                locationHelper.add(vectorHelper);
                particle.display(locationHelper);

                overallCount++;

                if (trackCount) {
                    currentCount++;
                    hasRan = true;

                    if (currentCount >= particlesPerDisplay) {
                        currentCount = 0;
                        break main;
                    }
                }
            }

            current = 0;
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        super.rotate(pitch, yaw, roll);

        if (rotateCircles) {
            for (CircleInfo circle : circles) {
                circle.setPitch(circle.getPitch() + pitch);
                circle.setYaw(circle.getYaw() + yaw);
                circle.setRoll(circle.getRoll() + roll);
            }
        }
    }

    @Override
    public ParticleCylinder clone() {
        CircleInfo[] circles = new CircleInfo[this.circles.size()];

        for (int i = 0; i < this.circles.size(); i++) {
            circles[i] = this.circles.get(i).clone();
        }

        ParticleCylinder clone = new ParticleCylinder(particle, circleFrequency, particleFrequency, circles);

        clone.setRotateCircles(rotateCircles);

        for (Pair<Particle, Integer> pair : secondaryParticles) {
            clone.addParticle(pair.getKey(), pair.getValue());
        }

        clone.setMechanic(mechanic);
        clone.setParticlesPerDisplay(particlesPerDisplay);

        return clone;
    }

    @Override
    protected boolean recalculateIfNeeded(@Nullable Location around) {
        boolean recalc = super.recalculateIfNeeded(around);

        if (recalc) {
            recalculate = true;
        }

        return recalc;
    }

    private void recalcCircumferenceAndArea() {
        cirTracker.clear();

        totalArea = 0;
        recalculate = false;
        double totalDist = getTotalDistance();
        double distToTravel = 0;
        int circle = 0;

        circleHelper.inherit(circles.get(0));

        for (int i = 0; i < circleFrequency; i++) {
            CircleInfo circle1 = circles.get(circle);
            CircleInfo circle2 = circles.get(circle + 1);
            double distance = circleHelper.getCenter().distance(circle2.getCenter());

            while (distToTravel > distance) {
                //i think this only happens because of double inconsistency so its fine to just ignore
                //in every case where this breaks the while loop the final circle winds up exactly where it should be,
                //anything off has to be in the millionths of a decimal
                if (circle + 2 >= circles.size()) break;

                distToTravel -= distance;
                circle++;
                circle1 = circles.get(circle);
                circle2 = circles.get(circle + 1);
                distance = circle1.getCenter().distance(circle2.getCenter());

                circleHelper.inherit(circle1);
            }

            double control = distToTravel / circle1.getCenter().distance(circle2.getCenter());

            if (!Double.isFinite(control)) {
                control = 1;
            }

            LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply(distToTravel);
            circleHelper.getCenter().add(vectorHelper);
            //adding x and z radius changes based on (circle2 - circle1) * (current position / distance between circles)
            circleHelper.setXRadius(circleHelper.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) * control));
            circleHelper.setZRadius(circleHelper.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) * control));

            distToTravel = totalDist / (circleFrequency - 1);
            double xRadius = Math.abs(circleHelper.getXRadius());
            double zRadius = Math.abs(circleHelper.getZRadius());
            double circumference;

            if (xRadius == zRadius) {
                circumference = Math.PI * 2 * xRadius;
            } else {
                circumference = Math.PI * 2 * Math.sqrt((Math.pow(xRadius, 2) + Math.pow(zRadius, 2)) / 2);
            }

            cirTracker.add(circumference);
            totalArea += circumference;
        }
    }

    public void addCircle(CircleInfo circle) {
        Validate.notNull(circle, "Circles cant be null!");

        if (circles.size() != 0) {
            Validate.isTrue(circle.getCenter().getWorld().equals(circles.get(0).getCenter().getWorld()), "Circle's worlds must be the same!");
        }

        circles.add(circle);
        locations.add((LocationSafe) circle.getCenter());
        origins.add(((LocationSafe) circle.getCenter()).cloneToLocation());
        ((LocationSafe) circle.getCenter()).setChanged(true);

        recalculate = true;
    }

    public void removeCircle(int index) {
        Validate.isTrue(circles.size() - 1 >= 2, "List must contain 2 or more CircleInfos!");

        circles.remove(index);
        locations.remove(index);
        origins.remove(index);
        locations.get(0).setChanged(true);

        recalculate = true;
    }

    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency > 1, "circleFrequency must be greater than 1! if you only want one circle, use ParticleCircle");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        circleHelper.getCenter().setWorld(world);
    }

    /**
     * @param rotateCircles whether circles are rotated with the shape, defaults to true
     */
    public void setRotateCircles(boolean rotateCircles) {
        this.rotateCircles = rotateCircles;
    }

    /**
     * @return whether circles are rotated with the shape, defaults to true
     */
    public boolean isRotatingCircles() {
        return rotateCircles;
    }

    public CircleInfo getCircle(int index) {
        return circles.get(index);
    }

    public int getCircleFrequency() {
        return circleFrequency;
    }

    public int getCircleAmount() {
        return circles.size();
    }
}