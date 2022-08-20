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
    private final CircleInfo circleHelper;
    private final Vector vectorHelper2 = new Vector(0, 0, 0);
    private double spin;
    private int count;

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, double spin, int count, double frequency, int particlesPerDisplay, CircleInfo... extraCircles) {
        super(particle, 0, 0, 0, frequency, particlesPerDisplay);

        Validate.notNull(circle1, "Circles cant be null!");
        Validate.notNull(circle2, "Circles cant be null!");

        for (CircleInfo extraCircle : extraCircles) Validate.notNull(extraCircle, "Circles cant be null!");

        circles.add(circle1);
        circles.add(circle2);
        circles.addAll(Arrays.asList(extraCircles));

        World world = circle1.getCenter().getWorld();

        for (CircleInfo circle : circles) {
            Validate.isTrue(circle.getCenter().getWorld().equals(world), "Circle's worlds must be the same!");
        }

        locationHelper.setWorld(world);
        circleHelper = circle1.clone();
        this.spin = spin;
        this.count = count;
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, double spin, int count, double frequency, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, spin, count, frequency, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, double spin, int count, int particlesPerDisplay, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, spin, count, 100, particlesPerDisplay, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, double spin, int count, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, spin, count, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, double spin, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, spin, 1, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, int count, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, 1, count, 100, 0, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo circle1, CircleInfo circle2, CircleInfo... extraCircles) {
        this(particle, circle1, circle2, 1, 1, 100, 0, extraCircles);
    }

    @Override
    public void display() {
        double increase = (((Math.PI * 2) * spin) / frequency) * count;

        for (int c = 0; c < count; c++)
        for (int i = 0; i < circles.size() - 1; i++) {
            CircleInfo circle1 = circles.get(i);
            CircleInfo circle2 = circles.get(i + 1);
            RotationHandler rot = circleHelper.getRotationHandler();
            double start = (((Math.PI * 2) * spin) * i) + (((Math.PI * 2) / count) * c);
            double end = (((Math.PI * 2) * spin) * (i + 1)) + (((Math.PI * 2) / count) * c);
            //using Math.abs() because it looks wonky in cases where the rotation is negative
            double pitchInc = Math.abs(circle1.getPitch() - circle2.getPitch());
            double yawInc = Math.abs(circle1.getYaw() - circle2.getYaw());
            double rollInc = Math.abs(circle1.getRoll() - circle2.getRoll());

            if (pitchInc != 0) pitchInc /= frequency;
            if (yawInc != 0) yawInc /= frequency;
            if (rollInc != 0) rollInc /= frequency;

            locationHelper.zero().add(circle1.getCenter());
            //setting vectorHelper to (end - start).normalize() * ((distance / frequency) * count)
            LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).normalize().multiply((circle1.getCenter().distance(circle2.getCenter()) / frequency) * count);
            circleHelper.inherit(circle1);

            for (double radian = start; radian <= end; radian += increase) {
                //setting vectorHelper2 to where the current particle should be in correlation to the current circle's center
                rot.apply(vectorHelper2.setX(circleHelper.getXRadius() * Math.cos(radian)).setY(0).setZ(circleHelper.getZRadius() * Math.sin(radian)));
                getCurrentParticle().display(locationHelper.add(vectorHelper2));
                circleHelper.getCenter().add(vectorHelper);
                rot.add(pitchInc, yawInc, rollInc);
                locationHelper.zero().add(circleHelper.getCenter());
            }
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {

    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {

    }

    @Override
    public void move(double x, double y, double z) {

    }

    public void addCircle(CircleInfo circle) {
        circles.add(circle);
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
