package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleShapeCompound;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;

public class ParticleCircle extends ParticleShaper {

    protected double xRadius;
    protected double zRadius;
    protected double limit = 0;
    protected boolean limitInverse = false;

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        rot.set(pitch, yaw, roll);
        start();
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 50);
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 50);
    }

    public ParticleCircle(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, 0, 0, 0, 50);
    }

    @Override
    public void display() {
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            double radian = Math.PI * 2 * (100 - limit) / 100 / particleFrequency * i;

            if (!limitInverse) {
                radian = -radian;
            }

            vectorHelper.setX(xRadius * Math.cos(radian));
            vectorHelper.setY(0);
            vectorHelper.setZ(zRadius * Math.sin(radian));
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
    public ParticleCircle clone() {
        ParticleCircle clone = new ParticleCircle(particle, locations.get(0).clone(), xRadius, zRadius, getPitch(), getYaw(), getRoll(), particleFrequency);
        clone.currentCount = currentCount;
        clone.overallCount = overallCount;
        clone.delay = delay;

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.originalCentroid.zero().add(originalCentroid);
        clone.lastRotatedAround.zero().add(lastRotatedAround);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setLimit(limit);
        clone.setLimitInverse(limitInverse);

        for (int i = 0; i < origins.size(); i++) {
            clone.origins.get(i).zero().add(origins.get(i));
        }

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    @Override
    public void scale(double x, double y, double z) {
        setXRadius(getXRadius() * x);
        setZRadius(getZRadius() * z);
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.add(center);
        origins.add(center.clone());
        setWorld(center.getWorld());
        center.setChanged(true);

        if (locations.size() > 1) {
            locations.remove(0);
            origins.remove(0);
        }
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    /**
     * @param limit percentage of the shape that should generate, such that 0 would be the entire shape and 50 would be half the shape.
     */
    public ParticleCircle setLimit(double limit) {
        Validate.isTrue(limit >= 0 && limit <= 100, "Limit is meant to be a percentage, and cannot be below 0 or above 100");

        this.limit = limit;

        return this;
    }

    /**
     * @param limitInverse determines if the limit cuts off at the left or right. default false (left)
     */
    public ParticleCircle setLimitInverse(boolean limitInverse) {
        this.limitInverse = limitInverse;

        return this;
    }

    public Location getCenter() {
        return locations.get(0);
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getZRadius() {
        return zRadius;
    }

    /**
     * @return percentage of the circle that should generate, such that 0 would be the entire sphere and 50 would be a half sphere.
     */
    public double getLimit() {
        return limit;
    }

    /**
     * @return determines if the limit cuts off at the left or right. default false (left)
     */
    public boolean isLimitInverse() {
        return limitInverse;
    }
}