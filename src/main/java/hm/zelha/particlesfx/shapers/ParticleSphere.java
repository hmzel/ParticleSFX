package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses surface area and circumference to position particles, as opposed to {@link ParticleSphereSFSA} which uses the
 * the <a href="https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42">Sunflower Seed Arrangement</a>
 * <br><br>
 * This class's sphere looks worse, but is much less chaotic and easier to color with secondary particles.
 */
public class ParticleSphere extends ParticleSphereSFSA {

    //circumference tracker
    protected final List<Double> cirTracker = new ArrayList<>();
    protected int circleFrequency;
    protected double surfaceArea = 0;
    protected boolean recalculate = true;

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphere
     */
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        super(particle, center, xRadius, yRadius, zRadius, pitch, yaw, roll, particleFrequency);

        setCircleFrequency(circleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphere
     */
    public ParticleSphere(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, circleFrequency, particleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphere
     */
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int circleFrequency, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**
     * Note: if circleFrequency is too high it can cause the sphere to look weird, if that's why you're looking at this then I'd suggest lowering it.
     *
     * @see ParticleSphere
     */
    public ParticleSphere(Particle particle, LocationSafe center, double radius, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, (int) (particleFrequency * 0.021), particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (particleFrequency * 0.021), particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius) {
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
                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                rot.apply(vectorHelper);
                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

                overallCount++;

                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);

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
    public ParticleSphere clone() {
        ParticleSphere clone = new ParticleSphere(particle, locations.get(0).clone(), xRadius, yRadius, zRadius, getPitch(), getYaw(), getRoll(), circleFrequency, particleFrequency);

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setLimit(limit);
        clone.setLimitInverse(limitInverse);

        if (animator == null) {
            clone.stop();
        }

        return clone;
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
                double x = Math.abs(xRadius * curve);
                double z = Math.abs(zRadius * curve);

                circumference = Math.PI * 2 * Math.sqrt((Math.pow(x, 2) + Math.pow(z, 2)) / 2);
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
    public void setLimit(double limit) {
        super.setLimit(limit);

        recalculate = true;
    }

    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    public int getCircleFrequency() {
        return circleFrequency;
    }
}