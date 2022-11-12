package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.shapers.parents.Shape;
import hm.zelha.particlesfx.util.LocationSafe;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

public class ParticleCircle extends ParticleShaper {

    private double xRadius;
    private double zRadius;
    private double limit = 0;
    private boolean limitInverse = false;

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll, double particleFrequency) {
        super(particle, particleFrequency);

        this.xRadius = xRadius;
        this.zRadius = zRadius;

        setCenter(center);
        rot.set(pitch, yaw, roll);
        start();
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 50);
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius, double particleFrequency) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    public ParticleCircle(Particle particle, LocationSafe center, double xRadius, double zRadius) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 50);
    }

    @Override
    public void display() {
        Location center = getCenter();
        double limitation = Math.PI * 2 * limit / 100;
        double loopEnd = Math.PI * 2;
        double loopStart = limitation;
        boolean hasRan = false;
        double increase = ((Math.PI * 2) - limitation) / particleFrequency;
        boolean trackCount = particlesPerDisplay > 0;

        if (limitInverse) {
            loopEnd -= limitation;
            loopStart = 0;
        }

        for (double radian = loopStart + (increase * overallCount); radian < loopEnd; radian += increase) {
            Particle particle = getCurrentParticle();

            vectorHelper.setX(xRadius * Math.cos(radian));
            vectorHelper.setY(0);
            vectorHelper.setZ(zRadius * Math.sin(radian));

            if (mechanic != null) {
                mechanic.apply(particle, center, vectorHelper);
            }

            rot.apply(vectorHelper);
            locationHelper.zero().add(center);
            particle.display(locationHelper.add(vectorHelper));

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
    public Shape clone() {
        ParticleCircle clone = new ParticleCircle(particle, locations.get(0).clone(), xRadius, zRadius, getPitch(), getYaw(), getRoll(), particleFrequency);

        for (Pair<Particle, Integer> pair : secondaryParticles) {
            clone.addParticle(pair.getKey(), pair.getValue());
        }

        clone.setMechanic(mechanic);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setLimit(limit);
        clone.setLimitInverse(limitInverse);

        return clone;
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.add(center);
        setWorld(center.getWorld());
        originalCentroid.zero().add(center);
        center.setChanged(true);

        if (locations.size() > 1) {
            locations.remove(0);
        }
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    /**
     * @param limit percentage of the sphere that should generate, such that 0 would be the entire sphere and 50 would be a half sphere.
     */
    public void setLimit(double limit) {
        Validate.isTrue(limit >= 0 && limit <= 100, "Limit is meant to be a percentage, and cannot be below 0 or above 100");

        this.limit = limit;
    }

    /**
     * @param limitInverse determines if the limit cuts off the top or the bottom. default false (top)
     */
    public void setLimitInverse(boolean limitInverse) {
        this.limitInverse = limitInverse;
    }

    public Location getCenter() {
        return locations.get(0);
    }

    public double getxRadius() {
        return xRadius;
    }

    public double getzRadius() {
        return zRadius;
    }

    /**
     * @return percentage of the sphere that should generate, such that 0 would be the entire sphere and 50 would be a half sphere.
     */
    public double getLimit() {
        return limit;
    }

    /**
     * @return if the limit cuts off the top or the bottom. default false (top)
     */
    public boolean isLimitInverse() {
        return limitInverse;
    }
}
