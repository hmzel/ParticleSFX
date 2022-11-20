package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.tuple.Pair;

import java.util.UUID;

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

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            //somehow evenly increments the radius idk what to name this
            double r = Math.sqrt((double) i / particleFrequency);
            //theta = PI * PHI * 2 * i
            //PHI = golden ratio = (1 + Math.sqrt(5)) / 2
            double theta = Math.PI * (1 + Math.sqrt(5)) * i;

            vectorHelper.setX(xRadius * r * Math.cos(theta));
            vectorHelper.setY(0);
            vectorHelper.setZ(zRadius * r * Math.sin(theta));
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

        for (Pair<Particle, Integer> pair : secondaryParticles) {
            clone.addParticle(pair.getKey(), pair.getValue());
        }

        for (Pair<ShapeDisplayMechanic, ShapeDisplayMechanic.Phase> pair : mechanics) {
            clone.addMechanic(pair.getValue(), pair.getKey());
        }

        for (UUID id : players) {
            clone.addPlayer(id);
        }

        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setLimit(limit);
        clone.setLimitInverse(limitInverse);

        return clone;
    }
}
