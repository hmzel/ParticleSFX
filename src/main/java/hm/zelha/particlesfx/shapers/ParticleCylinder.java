package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ParticleCylinder extends ParticleShaper {

    protected final List<CircleInfo> circleInfos = new ArrayList<>();
    //circumference tracker
    protected final List<Double> cirTracker = new ArrayList<>();
    protected final Rotation rotHelper2 = new Rotation();
    protected final CircleInfo circleHelper;
    protected boolean rotateCircles = true;
    protected boolean recalculate = true;
    protected double surfaceArea = 0;
    protected int circleFrequency;

    public ParticleCylinder(Particle particle, int circleFrequency, int particleFrequency, CircleInfo... circleInfos) {
        super(particle, particleFrequency);

        Validate.isTrue(circleInfos != null && circleInfos.length >= 2, "Array must contain 2 or more CircleInfos!");

        for (CircleInfo circle : circleInfos) {
            addCircleInfo(circle);
        }

        this.circleHelper = this.circleInfos.get(0).clone();

        setCircleFrequency(circleFrequency);
        setWorld(this.circleInfos.get(0).getCenter().getWorld());
        start();
    }

    public ParticleCylinder(Particle particle, int particleFrequency, CircleInfo... circleInfos) {
        this(particle, circleInfos.length * 5, particleFrequency, circleInfos);
    }

    public ParticleCylinder(Particle particle, CircleInfo... circleInfos) {
        this(particle, circleInfos.length * 5, circleInfos.length * 250, circleInfos);
    }

    @Override
    public void display() {
        double totalDist = getTotalDistance();
        int circleIndex = 0;
        int current = overallCount;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        //need to do this here to make sure circumference & surface area aren't screwed up by outside modification of locations
        recalculateIfNeeded(null);

        for (int i = 0; i < circleInfos.size(); i++) {
            if (circleInfos.get(i).isModified()) {
                circleInfos.get(i).setModified(false);

                recalculate = true;
            }
        }

        if (recalculate) {
            recalcCircumferenceAndArea();
        }

        circleHelper.inherit(circleInfos.get(0));
        rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());

        main:
        for (int i = 0; i < circleFrequency; i++) {
            CircleInfo circle1 = circleInfos.get(circleIndex);
            CircleInfo circle2 = circleInfos.get(circleIndex + 1);
            double distance = circleHelper.getCenter().distance(circle2.getCenter());
            double particleAmount = Math.max(Math.floor(particleFrequency * (cirTracker.get(i) / surfaceArea)), 1);
            double distToTravel = totalDist / (circleFrequency - 1);

            if (i == 0) {
                distToTravel = 0;
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
                //in every case where this breaks the while loop the final circle winds up exactly where it should be, so it's fine
                if (circleIndex + 2 >= circleInfos.size()) break;

                circleIndex++;
                distToTravel -= distance;
                circle1 = circleInfos.get(circleIndex);
                circle2 = circleInfos.get(circleIndex + 1);
                distance = circle1.getCenter().distance(circle2.getCenter());

                circleHelper.inherit(circle1);
                rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());
            }

            double control = distToTravel / circle1.getCenter().distance(circle2.getCenter());

            if (!Double.isFinite(control)) {
                control = 1;

                vectorHelper.zero();
            } else {
                LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply(distToTravel);
            }

            circleHelper.getCenter().add(vectorHelper);
            //adding pitch, yaw, roll, x and z radius changes based on (circle2 - circle1) * (current position / distance between circles)
            circleHelper.setXRadius(circleHelper.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) * control));
            circleHelper.setZRadius(circleHelper.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) * control));
            rotHelper.add((circle2.getPitch() - circle1.getPitch()) * control, (circle2.getYaw() - circle1.getYaw()) * control, (circle2.getRoll() - circle1.getRoll()) * control);

            for (int k = current; k < particleAmount; k++) {
                Particle particle = getCurrentParticle();
                double radian = Math.PI * 2 / particleAmount * k;

                vectorHelper.setX(circleHelper.getXRadius() * Math.cos(radian));
                vectorHelper.setY(0);
                vectorHelper.setZ(circleHelper.getZRadius() * Math.sin(radian));
                locationHelper.zero().add(circleHelper.getCenter());

                if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper);
                if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper);

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                rotHelper.apply(vectorHelper);

                if (rotateCircles) {
                    rot.apply(vectorHelper);

                    for (ParticleShapeCompound compound : compounds) {
                        rotHelper2.inherit(compound).apply(vectorHelper);
                    }
                }

                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

                overallCount++;
                currentCount++;

                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_PARTICLE, particle, locationHelper, vectorHelper);

                if (trackCount && currentCount >= particlesPerDisplay) {
                    currentCount = 0;
                    hasRan = true;

                    break main;
                }
            }

            current = 0;
        }

        if (!trackCount || hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);
        }

        if (!trackCount || !hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_FULL, particle, locationHelper, vectorHelper);

            overallCount = 0;

            if (trackCount) {
                display();

                return;
            }

            currentCount = 0;
        }
    }

    @Override
    public ParticleCylinder clone() {
        return ((ParticleCylinder) super.clone()).setRotateCircles(rotateCircles);
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        CircleInfo[] circles = new CircleInfo[this.circleInfos.size()];

        for (int i = 0; i < this.circleInfos.size(); i++) {
            circles[i] = this.circleInfos.get(i).clone();
        }

        return new ParticleCylinder(particle, circleFrequency, particleFrequency, circles);
    }

    @Override
    public void scale(double x, double y, double z) {
        super.scale(x, y, z);

        for (CircleInfo circle : circleInfos) {
            circle.setXRadius(circle.getXRadius() * x);
            circle.setZRadius(circle.getZRadius() * z);
        }
    }

    @Override
    protected boolean recalculateIfNeeded(@Nullable Location around) {
        for (int i = 0; i < circleInfos.size(); i++) {
            if (circleInfos.get(i).getCenter() != locations.get(i)) {
                locations.remove(i);
                locations.add(i, (LocationSafe) circleInfos.get(i).getCenter());
            }
        }

        boolean recalc = super.recalculateIfNeeded(around);

        if (recalc) {
            recalculate = true;
        }

        return recalc;
    }

    protected void recalcCircumferenceAndArea() {
        cirTracker.clear();

        surfaceArea = 0;
        recalculate = false;
        double totalDist = getTotalDistance();
        double distToTravel = 0;
        int circleIndex = 0;

        circleHelper.inherit(circleInfos.get(0));

        for (int i = 0; i < circleFrequency; i++) {
            CircleInfo circle1 = circleInfos.get(circleIndex);
            CircleInfo circle2 = circleInfos.get(circleIndex + 1);
            double distance = circleHelper.getCenter().distance(circle2.getCenter());

            while (distToTravel > distance) {
                //in every case where this breaks the while loop the final circle winds up exactly where it should be, so it's fine
                if (circleIndex + 2 >= circleInfos.size()) break;

                circleIndex++;
                distToTravel -= distance;
                circle1 = circleInfos.get(circleIndex);
                circle2 = circleInfos.get(circleIndex + 1);
                distance = circle1.getCenter().distance(circle2.getCenter());

                circleHelper.inherit(circle1);
            }

            double control = distToTravel / circle1.getCenter().distance(circle2.getCenter());

            if (!Double.isFinite(control)) {
                control = 1;

                vectorHelper.zero();
            } else {
                LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply(distToTravel);
            }

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
            surfaceArea += circumference;
        }
    }

    public void addCircleInfo(int index, CircleInfo circleInfo) {
        Validate.notNull(circleInfo, "CircleInfo cant be null!");

        if (circleInfos.size() != 0) {
            Validate.isTrue(circleInfo.getCenter().getWorld().equals(circleInfos.get(0).getCenter().getWorld()), "CircleInfo's worlds must be the same!");
        }

        circleInfos.add(index, circleInfo);
        locations.add(index, (LocationSafe) circleInfo.getCenter());
        origins.add(index, ((LocationSafe) circleInfo.getCenter()).clone());
        ((LocationSafe) circleInfo.getCenter()).setChanged(true);

        recalculate = true;
    }

    public void addCircleInfo(CircleInfo circleInfo) {
        addCircleInfo(circleInfos.size(), circleInfo);
    }

    public void removeCircleInfo(int index) {
        Validate.isTrue(circleInfos.size() - 1 >= 2, "List must contain 2 or more CircleInfos!");

        circleInfos.remove(index);
        locations.remove(index);
        origins.remove(index);
        locations.get(0).setChanged(true);

        recalculate = true;
    }

    /**
     * @param circleFrequency amount of circles in the cylinder
     */
    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency > 1, "circleFrequency must be greater than 1! if you only want one circle, use ParticleCircle");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    @Override
    public void setWorld(World world) {
        lastRotatedAround.setWorld(world);
        centroid.setWorld(world);
        rhLocationHelper.setWorld(world);
        originalCentroid.setWorld(world);
        locationHelper.setWorld(world);
        circleHelper.getCenter().setWorld(world);
        //if this isn't here it might cause errors if setWorld() is called after a circleInfo's center is
        //set to a new location with a different world
        recalculateIfNeeded(null);

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);
            origins.get(i).setWorld(world);
        }
    }

    /**
     * @param rotateCircles whether circles are rotated with the shape, defaults to true
     */
    public ParticleCylinder setRotateCircles(boolean rotateCircles) {
        this.rotateCircles = rotateCircles;

        return this;
    }

    /**
     * @return whether circles are rotated with the shape, defaults to true
     */
    public boolean isRotatingCircles() {
        return rotateCircles;
    }

    public CircleInfo getCircleInfo(int index) {
        return circleInfos.get(index);
    }

    public CircleInfo[] getCircleInfos() {
        return circleInfos.toArray(new CircleInfo[0]);
    }

    /**
     * @return amount of circles in the cylinder
     */
    public int getCircleFrequency() {
        return circleFrequency;
    }

    public int getCircleInfoAmount() {
        return circleInfos.size();
    }
}