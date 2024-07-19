package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleShapeCompound;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;

public class ParticleCircleFilled extends ParticleCircle {

    private int iterations = 0;

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
        int count = (int) (particleFrequency * 100 / (100 - limit));

        for (; iterations < count; iterations++) {
            //radian = PI * PHI * 2 * i
            //PHI = golden ratio = (1 + Math.sqrt(5)) / 2
            double radian = Math.PI * (1 + Math.sqrt(5)) * iterations;

            if (radian - ((int) (radian / (Math.PI * 2))) * (Math.PI * 2) < (Math.PI * 2 * limit / 100)) continue;

            if (limitInverse) {
                radian = -radian;
            }

            Particle particle = getCurrentParticle();
            double r = Math.sqrt((double) iterations / count);

            locationHelper.zero().add(getCenter());
            vectorHelper.setX(xRadius * r * Math.cos(radian));
            vectorHelper.setY(0);
            vectorHelper.setZ(zRadius * r * Math.sin(radian));

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

        applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);

        if (!trackCount || !hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_FULL, particle, locationHelper, vectorHelper);

            overallCount = 0;
            currentCount = 0;
            iterations = 0;
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

    @Override
    public void setDisplayPosition(int position) {
        iterations = (int) ((int) (particleFrequency * 100 / (100 - limit)) * ((double) overallCount / particleFrequency));

        super.setDisplayPosition(position);
    }
}