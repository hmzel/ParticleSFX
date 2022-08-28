package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

public class CurveInfo {

    private final RotationHandler rot = new RotationHandler(0, 0, 0);
    private double height;
    private double length;
    private double roll;
    private double apexPosition;

    public CurveInfo(double height, double length, double roll, double apexPosition) {

        Validate.isTrue(length > 0, "Length must be greater than 0!");
        Validate.isTrue(apexPosition < length && apexPosition >= 0, "Apex must be within the line!");

        this.height = height;
        this.length = length;
        this.roll = roll;
        this.apexPosition = apexPosition;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getRoll() {
        return roll;
    }

    public double getApexPosition() {
        return apexPosition;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public void setApexPosition(double apexPosition) {
        this.apexPosition = apexPosition;
    }
}
