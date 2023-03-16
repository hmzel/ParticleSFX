package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

public class CurveInfo {

    private double height;
    private double length;
    private double apexPosition;
    private double pitch;
    private double yaw;
    private double roll;

    public CurveInfo(double height, double length, double apexPosition, double pitch, double yaw, double roll) {
        setHeight(height);
        setLength(length);
        setApexPosition(apexPosition);
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    public CurveInfo(double height, double length, double apexPosition) {
        this(height, length, apexPosition, 0, 0, 0);
    }

    public CurveInfo(double height, double length) {
        this(height, length, length / 2, 0, 0, 0);
    }

    public CurveInfo clone() {
        return new CurveInfo(height, length, apexPosition, pitch, yaw, roll);
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setLength(double length) {
        Validate.isTrue(length > 0, "Length must be greater than 0!");

        this.length = length;
    }

    /**
     * @param apexPosition where the highest point of the curve should be, must be less than length and greater than 0
     */
    public void setApexPosition(double apexPosition) {
        Validate.isTrue(apexPosition < length && apexPosition >= 0, "Apex must be within the line!");

        this.apexPosition = apexPosition;
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

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    /**
     * @return where the highest point of the curve should be
     */
    public double getApexPosition() {
        return apexPosition;
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
