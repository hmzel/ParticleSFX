package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class CircleInfo {

    private final RotationHandler rot;
    private Location center;
    private double xRadius;
    private double zRadius;

    public CircleInfo(Location center, double xRadius, double zRadius, double pitch, double yaw, double roll) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        this.rot = new RotationHandler(pitch, yaw, roll);
        this.center = center;
        this.xRadius = xRadius;
        this.zRadius = zRadius;

        rot.addOrigins(center);
    }

    public CircleInfo(Location center, double xRadius, double zRadius) {
        this(center, xRadius, zRadius, 0, 0, 0);
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public RotationHandler getRotationHandler() {
        return rot;
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
}
