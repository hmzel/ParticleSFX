package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ParticleSpiral extends ParticleShaper {

    protected final List<CircleInfo> circleInfos = new ArrayList<>();
    protected final Vector vectorHelper2 = new Vector();
    protected final CircleInfo circleHelper;
    protected boolean rotateCircles = true;
    protected double spin;
    protected int count;

    public ParticleSpiral(Particle particle, double spin, int count, int particleFrequency, CircleInfo... circleInfos) {
        super(particle, particleFrequency);

        Validate.isTrue(circleInfos != null && circleInfos.length >= 2, "Array must contain 2 or more CircleInfos!");

        for (CircleInfo circle : circleInfos) {
            addCircleInfo(circle);
        }

        this.circleHelper = this.circleInfos.get(0).clone();

        setSpin(spin);
        setCount(count);
        setWorld(circleHelper.getCenter().getWorld());
        start();
    }

    public ParticleSpiral(Particle particle, double spin, int count, CircleInfo... circleInfos) {
        this(particle, spin, count, 500, circleInfos);
    }

    public ParticleSpiral(Particle particle, double spin, CircleInfo... circleInfos) {
        this(particle, spin, 1, 500, circleInfos);
    }

    public ParticleSpiral(Particle particle, int count, CircleInfo... circleInfos) {
        this(particle, circleInfos.length, count, 500, circleInfos);
    }

    public ParticleSpiral(Particle particle, CircleInfo... circleInfos) {
        this(particle, circleInfos.length, 1, 500, circleInfos);
    }

    @Override
    public void display() {
        double particleAmount = (double) particleFrequency / count / (circleInfos.size() - 1);
        double endRotation = Math.PI * 2 * spin / (circleInfos.size() - 1);
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;

        main:
        for (int c = 0; c < count; c++) {
            for (int i = 0; i < circleInfos.size() - 1; i++) {
                if (current >= particleAmount) {
                    current -= particleAmount;

                    continue;
                }

                CircleInfo circle1 = circleInfos.get(i);
                CircleInfo circle2 = circleInfos.get(i + 1);

                LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()).multiply(1 / particleAmount);
                circleHelper.inherit(circle1);
                rotHelper.set(circle1.getPitch(), circle1.getYaw(), circle1.getRoll());
                circleHelper.getCenter().add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);

                for (int k = current; k < particleAmount; k++) {
                    //adding (Math.PI * 2 / count * c) makes it so each spiral is evenly spaced
                    double radian = (endRotation * i) + (Math.PI * 2 / count * c) + (endRotation / particleAmount * k);
                    Particle particle = getCurrentParticle();

                    vectorHelper2.setX(circleHelper.getXRadius() * Math.cos(radian));
                    vectorHelper2.setY(0);
                    vectorHelper2.setZ(circleHelper.getZRadius() * Math.sin(radian));
                    locationHelper.zero().add(circleHelper.getCenter());

                    if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper2);
                    if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper2);

                    applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper2);
                    rotHelper.apply(vectorHelper2);

                    if (rotateCircles) {
                        rot.apply(vectorHelper2);

                        for (ParticleShapeCompound compound : compounds) {
                            rotHelper.inherit(compound).apply(vectorHelper2);
                        }
                    }

                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper2);
                    locationHelper.add(vectorHelper2);

                    if (!players.isEmpty()) {
                        particle.displayForPlayers(locationHelper, players);
                    } else {
                        particle.display(locationHelper);
                    }

                    circleHelper.getCenter().add(vectorHelper);
                    circleHelper.setXRadius(circle1.getXRadius() + ((circle2.getXRadius() - circle1.getXRadius()) / particleAmount * (k + 1)));
                    circleHelper.setZRadius(circle1.getZRadius() + ((circle2.getZRadius() - circle1.getZRadius()) / particleAmount * (k + 1)));
                    //using Math.abs() because it looks wonky in cases where the rotation is negative
                    rotHelper.setPitch(circle1.getPitch() + (Math.abs(circle1.getPitch() - circle2.getPitch()) / particleAmount * (k + 1)));
                    rotHelper.setYaw(circle1.getYaw() + (Math.abs(circle1.getYaw() - circle2.getYaw()) / particleAmount * (k + 1)));
                    rotHelper.setRoll(circle1.getRoll() + (Math.abs(circle1.getRoll() - circle2.getRoll()) / particleAmount * (k + 1)));

                    overallCount++;
                    currentCount++;

                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_PARTICLE, particle, locationHelper, vectorHelper2);

                    if (trackCount && currentCount >= particlesPerDisplay) {
                        currentCount = 0;
                        hasRan = true;

                        break main;
                    }
                }

                current = 0;
            }
        }

        if (!trackCount || hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper2);
        }

        if (!trackCount || !hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_FULL, particle, locationHelper, vectorHelper2);

            overallCount = 0;

            if (trackCount) {
                display();

                return;
            }

            currentCount = 0;
        }
    }

    @Override
    public ParticleSpiral clone() {
        return ((ParticleSpiral) super.clone()).setRotateCircles(rotateCircles);
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        CircleInfo[] circles = new CircleInfo[this.circleInfos.size()];

        for (int i = 0; i < this.circleInfos.size(); i++) {
            circles[i] = this.circleInfos.get(i).clone();
        }

        return new ParticleSpiral(particle, spin, count, particleFrequency, circles);
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

        return super.recalculateIfNeeded(around);
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
    }

    @Override
    public void setWorld(World world) {
        locationHelper.setWorld(world);
        lastRotatedAround.setWorld(world);
        centroid.setWorld(world);
        rhLocationHelper.setWorld(world);
        originalCentroid.setWorld(world);
        circleHelper.getCenter().setWorld(world);
        //if this isn't here it might cause errors if setWorld() is called after a circleInfo's center is set to
        //a new location with a different world
        recalculateIfNeeded(null);

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);
            origins.get(i).setWorld(world);
        }
    }

    /**
     * @param spin how many times the spiral goes in a circle throughout the whole shape
     */
    public void setSpin(double spin) {
        this.spin = spin;
    }

    /**
     * @param count how many spirals this shape displays
     */
    public void setCount(int count) {
        Validate.isTrue(count > 0, "Count cant be 0 or less!");

        this.count = count;
    }

    /**
     * @param rotateCircles whether circles are rotated with the shape, defaults to true
     */
    public ParticleSpiral setRotateCircles(boolean rotateCircles) {
        this.rotateCircles = rotateCircles;

        return this;
    }

    public CircleInfo getCircleInfo(int index) {
        return circleInfos.get(index);
    }

    public CircleInfo[] getCircleInfos() {
        return circleInfos.toArray(new CircleInfo[0]);
    }

    /**
     * @return how many times the spiral goes in a circle throughout the whole shape
     */
    public double getSpin() {
        return spin;
    }

    /**
     * @return how many spirals this shape displays
     */
    public int getCount() {
        return count;
    }

    /**
     * @return whether circles are rotated with the shape, defaults to true
     */
    public boolean isRotatingCircles() {
        return rotateCircles;
    }

    public int getCircleInfoAmount() {
        return circleInfos.size();
    }
}