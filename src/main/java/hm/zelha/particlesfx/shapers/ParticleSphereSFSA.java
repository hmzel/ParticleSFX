package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;

/**
 * This class uses the <a href="https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42">Sunflower Seed Arrangement</a>
 * to form a sphere, as opposed to {@link ParticleSphere} which uses surface area and circumference.
 * <br><br>
 * Using the sunflower seed arrangement uses almost the same processing power, but makes the sphere look much prettier and makes it so you
 * don't have to worry about CircleFrequency. <br>
 * however, using this makes it much harder to color the sphere with secondary particles, because the points are drawn pretty chaotically.
 * <br><br>
 * TLDR: Use this class if you just want a normal sphere or ellipsoid, otherwise use {@link ParticleSphere}.
 */
public class ParticleSphereSFSA extends ParticleCircle {

    protected double yRadius;

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int particleFrequency) {
        super(particle, center, xRadius, zRadius, pitch, yaw, roll, particleFrequency);

        setYRadius(yRadius);
    }

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, particleFrequency);
    }

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, particleFrequency);
    }

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double radius) {
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

                    break;
                }
            }
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public ParticleSphereSFSA clone() {
        ParticleSphereSFSA clone = new ParticleSphereSFSA(particle, locations.get(0).clone(), xRadius, yRadius, zRadius, getPitch(), getYaw(), getRoll(), particleFrequency);

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

    public void setYRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    public double getYRadius() {
        return yRadius;
    }
}