package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class CircleInfo {

    private LocationSafe center = null;
    private boolean modified = false;
    private double pitch;
    private double yaw;
    private double roll;
    private double xRadius;
    private double zRadius;

    public CircleInfo(LocationSafe center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        setCenter(center);
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
        setXRadius(xRadius);
        setZRadius(zRadius);
    }

    public CircleInfo(LocationSafe center, double radius, double pitch, double yaw, double roll) {
        this(center, radius, radius, pitch, yaw, roll);
    }

    public CircleInfo(LocationSafe center, double xRadius, double zRadius) {
        this(center, xRadius, zRadius, 0, 0, 0);
    }

    public CircleInfo(LocationSafe center, double radius) {
        this(center, radius, radius, 0, 0, 0);
    }

    /**
     * @param other CircleInfo for this object to copy data from
     * @return this object
     */
    public CircleInfo inherit(CircleInfo other) {
        setPitch(other.pitch);
        setYaw(other.yaw);
        setRoll(other.roll);
        setXRadius(other.xRadius);
        setZRadius(other.zRadius);

        center.setWorld(other.getCenter().getWorld());
        center.zero().add(other.getCenter());

        return this;
    }

    public boolean isModified() {
        return modified;
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
        modified = true;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
        modified = true;
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

    public void setModified(boolean modified) {
        this.modified = modified;
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
