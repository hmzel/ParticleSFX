package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.CircleInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticleSpiral extends ParticleShaper {

    private final List<CircleInfo> circles = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final Vector vectorHelper2 = new Vector(0, 0, 0);
    private final CircleInfo circleHelper;
    private double spin;
    private int count;

    public ParticleSpiral(Particle particle, double spin, int count, double frequency, int particlesPerDisplay, CircleInfo... circles) {
        super(particle, 0, 0, 0, frequency, particlesPerDisplay);

        Validate.isTrue(count != 0, "Count cant be 0 or less!");
        Validate.isTrue(circles != null && circles.length >= 2, "Array must contain 2 or more CircleInfos!");

        World world = circles[0].getCenter().getWorld();

        for (CircleInfo circle : circles) {
            Validate.notNull(circle, "Circles cant be null!");
            Validate.isTrue(circle.getCenter().getWorld().equals(world), "Circle's worlds must be the same!");
            locations.add(circle.getCenter());
            rot.addOrigins(circle.getCenter());
            rot2.addOrigins(circle.getCenter());
        }

        this.circles.addAll(Arrays.asList(circles));
        locationHelper.setWorld(world);

        this.circleHelper = circles[0].clone();
        this.spin = spin;
        this.count = count;
    }

    public ParticleSpiral(Particle particle, double spin, int count, double frequency, CircleInfo... extraCircles) {
        this(particle, spin, count, frequency, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, double spin, int count, int particlesPerDisplay, CircleInfo... extraCircles) {
        this(particle, spin, count, 100, particlesPerDisplay, extraCircles);
    }

    public ParticleSpiral(Particle particle, double spin, int count, CircleInfo... extraCircles) {
        this(particle, spin, count, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, double spin, CircleInfo... extraCircles) {
        this(particle, spin, 1, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, int count, CircleInfo... extraCircles) {
        this(particle, 1, count, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo... extraCircles) {
        this(particle, 1, 1, 100, 0, extraCircles);
    }

    @Override
    public void display() {
        //used to avoid potential cases of dividing by zero without adding a bunch of if statements
        //ex: (((distance / frequency) * count) * circles.size()) is the same as (distance * control)
        double control = ((frequency / (frequency * frequency)) * count) * circles.size();
        double endRotation = (Math.PI * 2) * (spin / (circles.size() - 1));
        double increase = endRotation * control;

        for (int c = 0; c < count; c++)
        for (int i = 0; i < circles.size() - 1; i++) {
            CircleInfo circle1 = circles.get(i);
            CircleInfo circle2 = circles.get(i + 1);
            RotationHandler rot = circleHelper.getRotationHandler();
            double start = (endRotation * i) + (((Math.PI * 2) / count) * c);
            double end = (endRotation * (i + 1)) + (((Math.PI * 2) / count) * c);
            //using Math.abs() because it looks wonky in cases where the rotation is negative
            double pitchInc = Math.abs(circle1.getPitch() - circle2.getPitch()) * control;
            double yawInc = Math.abs(circle1.getYaw() - circle2.getYaw()) * control;
            double rollInc = Math.abs(circle1.getRoll() - circle2.getRoll()) * control;
            double xRadiusInc = (circle2.getXRadius() - circle1.getXRadius()) * control;
            double zRadiusInc = (circle2.getZRadius() - circle1.getZRadius()) * control;

            locationHelper.zero().add(circle1.getCenter());
            //setting vectorHelper to (end - start).normalize() * (distance * control)
            LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply(circle1.getCenter().distance(circle2.getCenter()) * control);
            circleHelper.inherit(circle1);

            for (double radian = start; ((spin > 0) ? radian < end : radian > end); radian += increase) {
                //setting vectorHelper2 to where the current particle should be in correlation to the current circle's center (locationHelper)
                rot.apply(vectorHelper2.setX(circleHelper.getXRadius() * Math.cos(radian)).setY(0).setZ(circleHelper.getZRadius() * Math.sin(radian)));
                getCurrentParticle().display(locationHelper.add(vectorHelper2));
                circleHelper.getCenter().add(vectorHelper);
                circleHelper.setXRadius(circleHelper.getXRadius() + xRadiusInc);
                circleHelper.setZRadius(circleHelper.getZRadius() + zRadiusInc);
                rot.add(pitchInc, yawInc, rollInc);
                locationHelper.zero().add(circleHelper.getCenter());
            }
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);
        rot2.apply(around, locations);
        rot2.apply(around, rot.getOrigins());
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location centroid = locationHelper.zero();
        int amount = circles.size();

        for (int i = 0; i < amount; i++) centroid.add(rot.getOrigins().get(i));

        centroid.setX(centroid.getX() / amount);
        centroid.setY(centroid.getY() / amount);
        centroid.setZ(centroid.getZ() / amount);
        rot.add(pitch, yaw, roll);
        rot.apply(centroid, locations);
    }

    @Override
    public void move(double x, double y, double z) {
        rot.moveOrigins(x, y, z);
        rot2.moveOrigins(x, y, z);

        for (CircleInfo circle : circles) circle.getCenter().add(x, y, z);
    }

    public void addCircle(CircleInfo circle) {
        Validate.notNull(circle, "Circles cant be null!");
        Validate.isTrue(circle.getCenter().getWorld().equals(circles.get(0).getCenter().getWorld()), "Circle's worlds must be the same!");
        circles.add(circle);
        rot.addOrigins(circle.getCenter());
        rot2.addOrigins(circle.getCenter());
    }

    public List<CircleInfo> getCircles() {
        return circles;
    }

    public double getSpin() {
        return spin;
    }

    public int getCount() {
        return count;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
