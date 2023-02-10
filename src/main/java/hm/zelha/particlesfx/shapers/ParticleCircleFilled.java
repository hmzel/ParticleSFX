package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;

public class ParticleCircleFilled extends ParticleCircle {
    public ParticleCircleFilled(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll, int particleFrequency) {
        super(particle, center, xRadius, zRadius, pitch, yaw, roll, particleFrequency);
    }

    public ParticleCircleFilled(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 150);
    }

    public ParticleCircleFilled(Particle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    public ParticleCircleFilled(Particle particle, LocationSafe center, double xRadius, double zRadius) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 150);
    }

    public ParticleCircleFilled(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, 0, 0, 0, 150);
    }

    //https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42
    @Override
    public void display() {
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        double count = particleFrequency * 100 / (100 - limit);

        if (!Double.isFinite(count)) {
            count = 0;
        }

        count = (int) count;

        for (int i = overallCount; i < count; i++) {
            //radian = PI * PHI * 2 * i
            //PHI = golden ratio = (1 + Math.sqrt(5)) / 2
            double radian = Math.PI * (1 + Math.sqrt(5)) * i;

            if (radian - ((int) (radian / (Math.PI * 2))) * (Math.PI * 2) < (Math.PI * 2 * limit / 100)) {
                overallCount++;

                continue;
            }

            if (limitInverse) {
                radian = -radian;
            }

            Particle particle = getCurrentParticle();
            double r = Math.sqrt((double) i / count);

            locationHelper.zero().add(getCenter());
            vectorHelper.setX(xRadius * r * Math.cos(radian));
            vectorHelper.setY(0);
            vectorHelper.setZ(zRadius * r * Math.sin(radian));
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
    public ParticleCircleFilled clone() {
        ParticleCircleFilled clone = new ParticleCircleFilled(particle, locations.get(0).clone(), xRadius, zRadius, getPitch(), getYaw(), getRoll(), particleFrequency);

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
}
