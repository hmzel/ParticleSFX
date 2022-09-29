package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationS;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class ParticleCircle extends ParticleShaper {

    private double xRadius;
    private double zRadius;
    private boolean halfCircle = false;

    public ParticleCircle(Particle particle, LocationS center, double xRadius, double zRadius, double pitch, double yaw, double roll, double particleFrequency) {
        super(particle, particleFrequency);

        this.xRadius = xRadius;
        this.zRadius = zRadius;

        setCenter(center);
        rot.set(pitch, yaw, roll);
        start();
    }

    public ParticleCircle(Particle particle, LocationS center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 50);
    }

    public ParticleCircle(Particle particle, LocationS center, double xRadius, double zRadius, double particleFrequency) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, particleFrequency);
    }

    public ParticleCircle(Particle particle, LocationS center, double xRadius, double zRadius) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 50);
    }

    @Override
    public void display() {
        Location center = locations.get(0);
        double trueFrequency = ((halfCircle) ? Math.PI : (Math.PI * 2)) / particleFrequency;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (double radian = trueFrequency * overallCount; radian < Math.PI * ((halfCircle) ? 1 : 2); radian += trueFrequency) {
            rot.apply(vectorHelper.setX(xRadius * Math.cos(radian)).setY(0).setZ(zRadius * Math.sin(radian)));

            if (mechanic != null) mechanic.apply(particle, center, vectorHelper);

            locationHelper.zero().add(center);
            getCurrentParticle().display(locationHelper.add(vectorHelper));

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

        if (!trackCount) overallCount = 0;
        if (!hasRan && trackCount) overallCount = 0;
    }

    public void setCenter(LocationS center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        if (rot2.getPitch() + rot2.getYaw() + rot2.getRoll() != 0) {
            center.setChanged(true);
        }

        locations.clear();
        aroundOrigins.clear();
        locations.add(center);
        aroundOrigins.add(center.cloneToLocation());
        setWorld(center.getWorld());
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public void setHalfCircle(boolean halfCircle) {
        this.halfCircle = halfCircle;
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

    public boolean isHalfCircle() {
        return halfCircle;
    }
}
