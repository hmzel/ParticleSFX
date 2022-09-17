package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Collections;

public class ParticleCircle extends ParticleShaper {

    //TODO: make it an option to fill the circle

    private Location center;
    private double xRadius;
    private double zRadius;
    private double trueFrequency;
    private boolean halfCircle = false;

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, double pitch, double yaw, double roll, double frequency, int particlesPerDisplay) {
        super(particle, pitch, yaw, roll, frequency, particlesPerDisplay);

        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");
        locationHelper.setWorld(center.getWorld());
        rot2.addOrigins(center);

        this.center = center;
        this.xRadius = xRadius;
        this.zRadius = zRadius;
        this.trueFrequency = ((halfCircle) ? Math.PI : (Math.PI * 2)) / frequency;
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, double pitch, double yaw, double roll, double frequency) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, frequency, 0);
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, double pitch, double yaw, double roll, int particlesPerDisplay) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 50, particlesPerDisplay);
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        this(particle, center, xRadius, zRadius, pitch, yaw, roll, 50, 0);
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, double frequency) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, frequency, 0);
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius, int particlesPerDisplay) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 50, particlesPerDisplay);
    }

    public ParticleCircle(Particle particle, Location center, double xRadius, double zRadius) {
        this(particle, center, xRadius, zRadius, 0, 0, 0, 50, 0);
    }

    @Override
    public void display() {
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

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);
        rot2.apply(around, Collections.singletonList(center));
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        rot.add(pitch, yaw, roll);
    }

    @Override
    public void move(double x, double y, double z) {
        rot2.getOrigins().get(0).add(x, y, z);
        center.add(new Vector(x, y, z));
    }

    @Override
    public void face(Location toFace) {
        double xDiff = toFace.getX() - center.getX();
        double yDiff = toFace.getY() - center.getY();
        double zDiff = toFace.getZ() - center.getZ();
        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY));

        if (zDiff < 0.0D) yaw += Math.abs(180.0D - yaw) * 2.0D;

        setPitch(pitch);
        setYaw(yaw - 90);
    }

    public void setCenter(Location center) {
        this.center = center;

        locationHelper.setWorld(center.getWorld());
        rot2.reset();
        rot2.addOrigins(center);
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public void setPitch(double pitch) {
        rot.setPitch(pitch);
    }

    public void setYaw(double yaw) {
        rot.setYaw(yaw);
    }

    public void setRoll(double roll) {
        rot.setRoll(roll);
    }

    @Override
    public void setParticleFrequency(double particleFrequency) {
        super.setParticleFrequency(particleFrequency);

        this.trueFrequency = (Math.PI * 2) / particleFrequency;
    }

    public void setHalfCircle(boolean halfCircle) {
        this.halfCircle = halfCircle;
    }

    public Location getCenter() {
        return center;
    }

    public double getxRadius() {
        return xRadius;
    }

    public double getzRadius() {
        return zRadius;
    }

    public double getPitch() {
        return rot.getPitch();
    }

    public double getYaw() {
        return rot.getYaw();
    }

    public double getRoll() {
        return rot.getRoll();
    }

    public boolean isHalfCircle() {
        return halfCircle;
    }
}
