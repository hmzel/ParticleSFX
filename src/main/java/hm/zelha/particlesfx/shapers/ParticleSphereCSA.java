package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleShapeCompound;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses surface area and circumference to position particles, as opposed to {@link ParticleSphere} which uses the
 * the <a href="https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42">Sunflower Seed Arrangement</a>
 * <br><br>
 * This class's sphere looks worse, but is much less chaotic and easier to color with secondary particles.
 */
public class ParticleSphereCSA extends ParticleSphere {

    //circumference tracker
    protected final List<Double> cirTracker = new ArrayList<>();
    protected int circleFrequency;
    protected double surfaceArea = 0;
    protected boolean recalculate = true;

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphereCSA
     */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        super(particle, center, xRadius, yRadius, zRadius, pitch, yaw, roll, particleFrequency);

        setCircleFrequency(circleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphereCSA
     */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, circleFrequency, particleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphereCSA
     */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int circleFrequency, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphereCSA
     */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double radius, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**@see ParticleSphereCSA */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, (int) (particleFrequency * 0.021), particleFrequency);
    }

    /**@see ParticleSphereCSA */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (particleFrequency * 0.021), particleFrequency);
    }

    /**@see ParticleSphereCSA */
    public ParticleSphereCSA(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (radius * 75 * 0.021), (int) (radius * 75));
    }

    @Override
    public void display() {
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;

        if (recalculate) {
            recalcCircumferenceAndArea();
        }

        main:
        for (int i = 0; i < circleFrequency; i++) {
            double curveRadian = (Math.PI - (Math.PI * limit / 100)) / (circleFrequency - 1) * i;
            int particleAmount = (int) Math.max((particleFrequency - (circleFrequency / 2D)) * (cirTracker.get(i) / surfaceArea), 1);

            if (!limitInverse) {
                curveRadian += Math.PI * limit / 100;
            }

            if (current >= particleAmount) {
                current -= particleAmount;

                continue;
            }

            for (int k = current; k < particleAmount; k++) {
                Particle particle = getCurrentParticle();
                double radian = Math.PI * 2 / particleAmount * k;

                locationHelper.zero().add(getCenter());
                vectorHelper.setX(xRadius * Math.sin(curveRadian) * Math.cos(radian));
                vectorHelper.setY(yRadius * Math.cos(curveRadian));
                vectorHelper.setZ(zRadius * Math.sin(curveRadian) * Math.sin(radian));

                if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper);
                if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper);

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                rot.apply(vectorHelper);

                for (ParticleShapeCompound compound : compounds) {
                    rotHelper.inherit(compound).apply(vectorHelper);
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
    public ParticleSphereCSA clone() {
        return (ParticleSphereCSA) super.clone();
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        return new ParticleSphereCSA(particle, locations.get(0).clone(), xRadius, yRadius, zRadius, getPitch(), getYaw(), getRoll(), circleFrequency, particleFrequency);
    }

    protected void recalcCircumferenceAndArea() {
        cirTracker.clear();

        surfaceArea = 0;

        for (int i = 0; i < circleFrequency; i++) {
            double curveRadian = (Math.PI - (Math.PI * limit / 100)) / (circleFrequency - 1) * i;

            if (!limitInverse) {
                curveRadian += Math.PI * limit / 100;
            }

            double curve = Math.sin(curveRadian);
            double circumference;

            if (xRadius == zRadius) {
                circumference = Math.PI * 2 * Math.abs(xRadius * curve);
            } else {
                circumference = Math.PI * 2 * Math.sqrt((Math.pow(xRadius * curve, 2) + Math.pow(zRadius * curve, 2)) / 2);
            }

            cirTracker.add(circumference);

            surfaceArea += circumference;
        }

        recalculate = false;
    }

    @Override
    public void setXRadius(double xRadius) {
        super.setXRadius(xRadius);

        recalculate = true;
    }

    @Override
    public void setZRadius(double zRadius) {
        super.setZRadius(zRadius);

        recalculate = true;
    }

    @Override
    public ParticleSphere setLimit(double limit) {
        recalculate = true;

        return super.setLimit(limit);
    }

    /**
     * @param circleFrequency amount of circles in the sphere
     */
    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    /**
     * @return amount of circles in the sphere
     */
    public int getCircleFrequency() {
        return circleFrequency;
    }
}