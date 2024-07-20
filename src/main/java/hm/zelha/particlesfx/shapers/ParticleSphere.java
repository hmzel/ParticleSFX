package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleShapeCompound;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;

/**
 * This class uses the <a href="https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42">Sunflower Seed Arrangement</a>
 * to form a sphere, as opposed to {@link ParticleSphereCSA} which uses surface area and circumference.
 * <br><br>
 * Using the sunflower seed arrangement uses almost the same processing power, but makes the sphere look much prettier and makes it so you
 * don't have to worry about CircleFrequency. <br>
 * however, using this makes it much harder to color the sphere with secondary particles, because the points are drawn pretty chaotically.
 * <br><br>
 * TLDR: Use this class if you just want a normal sphere or ellipsoid, otherwise use {@link ParticleSphereCSA}.
 */
public class ParticleSphere extends ParticleCircle {

    protected double yRadius;

    /**@see ParticleSphere */
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int particleFrequency) {
        super(particle, center, xRadius, zRadius, pitch, yaw, roll, particleFrequency);

        setYRadius(yRadius);
    }

    /**@see ParticleSphere */
    public ParticleSphere(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, particleFrequency);
    }

    /**@see ParticleSphere */
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    /**@see ParticleSphere */
    public ParticleSphere(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, particleFrequency);
    }

    /**@see ParticleSphere */
    public ParticleSphere(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (radius * 75));
    }

    @Override
    public void display() {
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            double limit = particleFrequency - (i - (i * (this.limit / 100)));

            if (limitInverse) {
                limit = i - (i * (this.limit / 100));
            }

            double curveRadian = Math.acos(1 - 2D * limit / particleFrequency);
            double radian = Math.PI * (1 + Math.sqrt(5)) * i;

            vectorHelper.setX(xRadius * Math.sin(curveRadian) * Math.cos(radian));
            vectorHelper.setY(yRadius * Math.cos(curveRadian));
            vectorHelper.setZ(zRadius * Math.sin(curveRadian) * Math.sin(radian));
            locationHelper.zero().add(getCenter());

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

                break;
            }
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
    public ParticleSphere clone() {
        return (ParticleSphere) super.clone();
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        return new ParticleSphere(particle, locations.get(0).clone(), xRadius, yRadius, zRadius, getPitch(), getYaw(), getRoll(), particleFrequency);
    }

    @Override
    public void scale(double x, double y, double z) {
        super.scale(x, y, z);
        setYRadius(getYRadius() * y);
    }

    @Override
    public ParticleSphere setLimit(double limit) {
        return (ParticleSphere) super.setLimit(limit);
    }

    /**
     * @param limitInverse determines if the limit cuts off at the top or bottom. default false (top)
     */
    @Override
    public ParticleSphere setLimitInverse(boolean limitInverse) {
        return (ParticleSphere) super.setLimitInverse(limitInverse);
    }

    public void setYRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    public double getYRadius() {
        return yRadius;
    }
}