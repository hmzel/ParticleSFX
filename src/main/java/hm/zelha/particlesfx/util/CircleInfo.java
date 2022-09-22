package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

public class CircleInfo {

    private final Rotation rot;
    private LocationS center = null;
    private double xRadius;
    private double zRadius;

    public CircleInfo(LocationS center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        setCenter(center);

        this.rot = new Rotation(pitch, yaw, roll);
        this.xRadius = xRadius;
        this.zRadius = zRadius;
    }

    public CircleInfo(LocationS center, double xRadius, double zRadius) {
        this(center, xRadius, zRadius, 0, 0, 0);
    }

    public CircleInfo clone() {
        return new CircleInfo(center.clone(), xRadius, zRadius, rot.getPitch(), rot.getYaw(), rot.getRoll());
    }

    public CircleInfo inherit(CircleInfo other) {
        this.xRadius = other.getXRadius();
        this.zRadius = other.getZRadius();

        center.setWorld(other.getCenter().getWorld());
        center.zero().add(other.getCenter());
        rot.set(other.getPitch(), other.getYaw(), other.getRoll());

        return this;
    }

    public void setCenter(LocationS center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        if (this.center != null) center.setChanged(true);

        this.center = center;
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

    public Rotation getRotation() {
        return rot;
    }

    public LocationS getCenter() {
        return center;
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getZRadius() {
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
}
