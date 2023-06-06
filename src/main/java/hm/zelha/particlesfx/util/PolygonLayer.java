package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

/**
 * only used in one of ParticlePolygon's constructors for more customizability
 */
public class PolygonLayer {

    private int cornerAmount;
    private double xRadius;
    private double zRadius;
    private double yPosition;
    private double pitch;
    private double yaw;
    private double roll;

    /**@see PolygonLayer*/
    public PolygonLayer(int cornerAmount, double xRadius, double zRadius, double yPosition, double pitch, double yaw, double roll) {
        setCornerAmount(cornerAmount);
        setXRadius(xRadius);
        setZRadius(zRadius);
        setYPosition(yPosition);
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    /**@see PolygonLayer*/
    public PolygonLayer(int cornerAmount, double xRadius, double zRadius, double yPosition, double yaw) {
        this(cornerAmount, xRadius, zRadius, yPosition, 0, yaw, 0);
    }

    /**@see PolygonLayer*/
    public PolygonLayer(int cornerAmount, double xRadius, double zRadius, double yPosition) {
        this(cornerAmount, xRadius, zRadius, yPosition, 0, 0, 0);
    }

    /**@see PolygonLayer*/
    public PolygonLayer(int cornerAmount, double radius, double yPosition) {
        this(cornerAmount, radius, radius, yPosition, 0, 0, 0);
    }

    /**@see PolygonLayer*/
    public PolygonLayer(int cornerAmount, double radius) {
        this(cornerAmount, radius, radius, 0, 0 ,0, 0);
    }

    public void setCornerAmount(int cornerAmount) {
        Validate.isTrue(cornerAmount > 0, "Cant have less than 1 corner!");

        this.cornerAmount = cornerAmount;
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

    public int getCornerAmount() {
        return cornerAmount;
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