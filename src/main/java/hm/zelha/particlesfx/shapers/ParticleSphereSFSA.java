package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

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
public class ParticleSphereSFSA extends ParticleShaper {

    //TODO: maybe make this extend ParticleCircle at some point? that makes sense but doesnt at the same time so idrk. decide later

    protected double xRadius;
    protected double yRadius;
    protected double zRadius;
    protected double limit = 0;
    protected boolean limitInverse = false;

    /**@see ParticleSphereSFSA*/
    public ParticleSphereSFSA(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setxRadius(xRadius);
        setyRadius(yRadius);
        setzRadius(zRadius);
        rot.set(pitch, yaw, roll);
        start();
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
            //phi multiplier
            double phiX;

            if (limitInverse) {
                phiX = i - (i * (limit / 100));
            } else {
                phiX = particleFrequency - (i - (i * (limit / 100)));
            }

            double phi = Math.acos(1 - 2D * phiX / particleFrequency);
            double theta = Math.PI * (1 + Math.sqrt(5)) * i;
            double x = Math.cos(theta) * Math.sin(phi) * xRadius;
            double y = Math.cos(phi) * zRadius;
            double z = Math.sin(theta) * Math.sin(phi) * yRadius;

            vectorHelper.setX(x);
            vectorHelper.setY(y);
            vectorHelper.setZ(z);
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

    public void setxRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setyRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    public void setzRadius(double zRadius) {
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

    public double getyRadius() {
        return yRadius;
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