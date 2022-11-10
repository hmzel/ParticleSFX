package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class CircleInfo {

    private LocationSafe center = null;
    private double pitch;
    private double yaw;
    private double roll;
    private double xRadius;
    private double zRadius;

    public CircleInfo(LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        setCenter(center);

        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.xRadius = xRadius;
        this.zRadius = zRadius;
    }

    public CircleInfo(LocationSafe center, double xRadius, double zRadius) {
        this(center, xRadius, zRadius, 0, 0, 0);
    }

    public CircleInfo clone() {
        return new CircleInfo(center.clone(), xRadius, zRadius, pitch, yaw, roll);
    }

    public CircleInfo inherit(CircleInfo other) {
        this.xRadius = other.getXRadius();
        this.zRadius = other.getZRadius();
        this.pitch = other.pitch;
        this.yaw = other.yaw;
        this.roll = other.roll;

        center.setWorld(other.getCenter().getWorld());
        center.zero().add(other.getCenter());

        return this;
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        if (this.center != null) {
            center.setChanged(true);
        }

        this.center = center;
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public Location getCenter() {
        return center;
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getZRadius() {
        return zRadius;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public double getRoll() {
        return roll;
    }
}
