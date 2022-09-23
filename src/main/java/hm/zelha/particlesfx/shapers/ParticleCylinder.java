package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.CircleInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationS;
import hm.zelha.particlesfx.util.Rotation;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ParticleCylinder extends ParticleShaper {

    //TODO: make particles depend on radius like in ParticleSphere
    // make it so using rotation methods every tick doesnt cause wacky stuff to happen if all locations are evenly spaced

    private final List<CircleInfo> circles = new ArrayList<>();
    private final CircleInfo circleHelper = new CircleInfo(new LocationS(Bukkit.getWorld("world"), 0, 0, 0), 0, 0);
    private final Rotation rotHelper = new Rotation();
    private int circleFrequency;

    public ParticleCylinder(Particle particle, int circleFrequency, double particleFrequency, CircleInfo... circles) {
        super(particle, particleFrequency);

        Validate.isTrue(circles != null && circles.length >= 2, "Array must contain 2 or more CircleInfos!");

        World world = circles[0].getCenter().getWorld();

        setCircleFrequency(circleFrequency);
        setWorld(world);

        for (CircleInfo circle : circles) addCircle(circle);

        start();
    }

    public ParticleCylinder(Particle particle, CircleInfo... circles) {
        this(particle, circles.length * 5, 100, circles);
    }

    @Override
    public void display() {
        double increase = (Math.PI * 2) / (particleFrequency / circleFrequency);
        double totalDist = 0;
        double distToTravel = 0;
        double continuation = 0;

        circleHelper.inherit(circles.get(0));
        rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());

        //adding the distance between every circle to totalDist
        for (int i = 0; i < locations.size() - 1; i++) totalDist += locations.get(i).distance(locations.get(i + 1));

        for (int i = 0, circle = 0; i < circleFrequency; i++) {
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
                rotHelper.set(circleHelper.getPitch(), circleHelper.getYaw(), circleHelper.getRoll());
            }

            double control = distToTravel / circle1.getCenter().distance(circle2.getCenter());

            if (!Double.isFinite(control)) control = 1;

            //setting vectorHelper to (end - start) / (absolute vector sum / distToTravel)
            LVMath.divide(LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()), (Math.abs(vectorHelper.getX()) + Math.abs(vectorHelper.getY()) + Math.abs(vectorHelper.getZ())) * ((distToTravel != 0) ? (1 / distToTravel) : 0));
            circleHelper.getCenter().add(vectorHelper);
            //adding pitch, yaw, roll, x and z radius changes based on (circle2 - circle1) * (current position / distance between circles)
            circleHelper.setXRadius(circleHelper.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) * control));
            circleHelper.setZRadius(circleHelper.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) * control));
            rotHelper.add((circle2.getPitch() - circle1.getPitch()) * control, (circle2.getYaw() - circle1.getYaw()) * control, (circle2.getRoll() - circle1.getRoll()) * control);
            locationHelper.zero().add(circleHelper.getCenter());

            for (double radian = continuation; true; radian += increase) {
                if (radian > (Math.PI * 2) + continuation) {
                    continuation = radian - Math.PI * 2;
                    break;
                }
                //setting vectorHelper2 to where the current particle should be in correlation to the current circle's center (locationHelper)
                rotHelper.apply(vectorHelper.setX(circleHelper.getXRadius() * Math.cos(radian)).setY(0).setZ(circleHelper.getZRadius() * Math.sin(radian)));
                getCurrentParticle().display(locationHelper.add(vectorHelper));
                locationHelper.subtract(vectorHelper);
            }

            distToTravel = totalDist / (circleFrequency - 1);
        }
    }

    public void addCircle(CircleInfo circle) {
        Validate.notNull(circle, "Circles cant be null!");

        if (circles.size() != 0) {
            Validate.isTrue(circle.getCenter().getWorld().equals(circles.get(0).getCenter().getWorld()), "Circle's worlds must be the same!");
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            circle.getCenter().setChanged(true);
        }

        circles.add(circle);
        locations.add(circle.getCenter());
        origins.add(circle.getCenter().cloneToLocation());
        aroundOrigins.add(circle.getCenter().cloneToLocation());
    }

    public void removeCircle(int index) {
        Validate.isTrue(circles.size() - 1 >= 2, "List must contain 2 or more CircleInfos!");

        circles.remove(index);
        locations.remove(index);
        origins.remove(index);
        aroundOrigins.remove(index);

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            locations.get(0).setChanged(true);
        }
    }

    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency > 1, "circleFrequency must be greater than 1! if you only want one circle, use ParticleCircle");

        this.circleFrequency = circleFrequency;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        circleHelper.getCenter().setWorld(world);
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