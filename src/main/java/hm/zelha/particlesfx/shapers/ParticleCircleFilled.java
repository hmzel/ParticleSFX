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
        int count = particleFrequency;
        double limitation = zRadius * 2 * limit / 100;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < count; i++) {
            //somehow evenly increments the radius idk what to name this
            double r = Math.sqrt((double) i / particleFrequency);
            //theta = PI * PHI * 2 * i
            //PHI = golden ratio = (1 + Math.sqrt(5)) / 2
            double theta = Math.PI * (1 + Math.sqrt(5)) * i;
            double z = zRadius * r * Math.sin(theta);

            if ((!limitInverse && z + zRadius > limitation) || (limitInverse && z + zRadius < limitation)) {
                count++;
                continue;
            }

            Particle particle = getCurrentParticle();
            double x = xRadius * r * Math.cos(theta);

            locationHelper.zero().add(getCenter());
            vectorHelper.setX(x).setY(0).setZ(z);
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
