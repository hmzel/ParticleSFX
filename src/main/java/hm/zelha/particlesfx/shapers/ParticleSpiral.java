package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleSpiral extends ParticleShaper {

    protected final List<CircleInfo> circles = new ArrayList<>();
    protected final Vector vectorHelper2 = new Vector();
    protected final Rotation rotHelper = new Rotation();
    protected final CircleInfo circleHelper;
    protected boolean rotateCircles = true;
    protected double spin;
    protected int count;

    public ParticleSpiral(Particle particle, double spin, int count, int particleFrequency, CircleInfo... circles) {
        super(particle, particleFrequency);

        Validate.isTrue(circles != null && circles.length >= 2, "Array must contain 2 or more CircleInfos!");

        setCount(count);
        setSpin(spin);

        for (CircleInfo circle : circles) {
            addCircle(circle);
        }

        this.circleHelper = this.circles.get(0).clone();

        setWorld(circles[0].getCenter().getWorld());
        start();
    }

    public ParticleSpiral(Particle particle, double spin, int count, CircleInfo... extraCircles) {
        this(particle, spin, count, 500, extraCircles);
    }

    public ParticleSpiral(Particle particle, double spin, CircleInfo... extraCircles) {
        this(particle, spin, 1, 500, extraCircles);
    }

    public ParticleSpiral(Particle particle, int count, CircleInfo... extraCircles) {
        this(particle, extraCircles.length, count, 500, extraCircles);
    }

    public ParticleSpiral(Particle particle, CircleInfo... extraCircles) {
        this(particle, extraCircles.length, 1, 500, extraCircles);
    }

    @Override
    public void display() {
        double particleAmount = (double) particleFrequency / count / (circles.size() - 1);
        double endRotation = Math.PI * 2 * spin / (circles.size() - 1);
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;

        main:
        for (int c = 0; c < count; c++) {
            for (int i = 0; i < circles.size() - 1; i++) {
                if (current >= particleAmount) {
                    current -= particleAmount;

                    continue;
                }

                CircleInfo circle1 = circles.get(i);
                CircleInfo circle2 = circles.get(i + 1);

                LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).multiply(1 / particleAmount);
                circleHelper.inherit(circle1);
                rotHelper.set(circle1.getPitch(), circle1.getYaw(), circle1.getRoll());

                if (trackCount && current != 0) {
                    circleHelper.getCenter().add(vectorHelper.multiply(current));
                    vectorHelper.multiply(1 / current);
                }

                for (int k = current; k < particleAmount; k++) {
                    //adding (Math.PI * 2 / count * c) makes it so each spiral is evenly spaced
                    double radian = (endRotation * i) + (Math.PI * 2 / count * c) + (endRotation / particleAmount * k);
                    Particle particle = getCurrentParticle();

                    vectorHelper2.setX(circleHelper.getXRadius() * Math.cos(radian));
                    vectorHelper2.setY(0);
                    vectorHelper2.setZ(circleHelper.getZRadius() * Math.sin(radian));
                    locationHelper.zero().add(circleHelper.getCenter());
                    applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper2);
                    rotHelper.apply(vectorHelper2);
                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper2);
                    locationHelper.add(vectorHelper2);

                    if (!players.isEmpty()) {
                        particle.displayForPlayers(locationHelper, players);
                    } else {
                        particle.display(locationHelper);
                    }

                    circleHelper.getCenter().add(vectorHelper);
                    circleHelper.setXRadius(circle1.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) / particleAmount * k));
                    circleHelper.setZRadius(circle1.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) / particleAmount * k));
                    //using Math.abs() because it looks wonky in cases where the rotation is negative
                    rotHelper.setPitch(circle1.getPitch() + (Math.abs(circle1.getPitch() - circle2.getPitch()) / particleAmount * k));
                    rotHelper.setYaw(circle1.getYaw() + (Math.abs(circle1.getYaw() - circle2.getYaw()) / particleAmount * k));
                    rotHelper.setRoll(circle1.getRoll() + (Math.abs(circle1.getRoll() - circle2.getRoll()) / particleAmount * k));

                    overallCount++;

                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper2);

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
    public ParticleSpiral clone() {
        CircleInfo[] circles = new CircleInfo[this.circles.size()];

        for (int i = 0; i < this.circles.size(); i++) {
            circles[i] = this.circles.get(i).clone();
        }

        ParticleSpiral clone = new ParticleSpiral(particle, spin, count, particleFrequency, circles);

        clone.setRotateCircles(rotateCircles);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    public void addCircle(CircleInfo circle) {
        Validate.notNull(circle, "Circles cant be null!");

        if (circles.size() != 0) {
            Validate.isTrue(circle.getCenter().getWorld().equals(circles.get(0).getCenter().getWorld()), "Circle's worlds must be the same!");
        }

        circles.add(circle);
        locations.add((LocationSafe) circle.getCenter());
        origins.add(((LocationSafe) circle.getCenter()).clone());
        ((LocationSafe) circle.getCenter()).setChanged(true);
    }

    public void removeCircle(int index) {
        Validate.isTrue(circles.size() - 1 >= 2, "List must contain 2 or more CircleInfos!");

        circles.remove(index);
        locations.remove(index);
        origins.remove(index);
        locations.get(0).setChanged(true);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        circleHelper.getCenter().setWorld(world);
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public void setCount(int count) {
        Validate.isTrue(count > 0, "Count cant be 0 or less!");

        this.count = count;
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

    public double getSpin() {
        return spin;
    }

    public int getCount() {
        return count;
    }
}
