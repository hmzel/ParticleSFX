package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

/**
 * only used in one of ParticlePolygon's constructors for more customizability
 */
public class PolygonLayer {

    private int corners;
    private double xRadius;
    private double zRadius;
    private double yPosition;
    private double pitch;
    private double yaw;
    private double roll;

    public PolygonLayer(int corners, double xRadius, double zRadius, double yPosition, double pitch, double yaw, double roll) {
        setCorners(corners);
        setXRadius(xRadius);
        setZRadius(zRadius);
        setYPosition(yPosition);
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    public PolygonLayer(int corners, double xRadius, double zRadius, double yPosition, double yaw) {
        this(corners, xRadius, zRadius, yPosition, 0, yaw, 0);
    }

    public PolygonLayer(int corners, double xRadius, double zRadius, double yPosition) {
        this(corners, xRadius, zRadius, yPosition, 0, 0, 0);
    }

    public PolygonLayer(int corners, double radius, double yPosition) {
        this(corners, radius, radius, yPosition, 0, 0, 0);
    }

    public PolygonLayer(int corners, double radius) {
        this(corners, radius, radius, 0, 0 ,0, 0);
    }

    public void setCorners(int corners) {
        Validate.isTrue(corners > 0, "Cant have less than 1 corner!");

        this.corners = corners;
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
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

    public int getCorners() {
        return corners;
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getZRadius() {
        return zRadius;
    }

    public double getYPosition() {
        return yPosition;
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
