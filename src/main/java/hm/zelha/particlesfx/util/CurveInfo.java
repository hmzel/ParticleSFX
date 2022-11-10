package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;

public class CurveInfo {

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

    public CurveInfo(double height, double length, double roll) {
        this(height, length, roll, length / 2);
    }

    public CurveInfo(double height, double length) {
        this(height, length, 0, length / 2);
    }

    public CurveInfo clone() {
        return new CurveInfo(height, length, roll, apexPosition);
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
        Validate.isTrue(length > 0, "Length must be greater than 0!");

        this.length = length;
    }

    public void setRoll(double roll) {
        while (roll >= 360) {
            roll -= 360;
        }

        this.roll = roll;
    }

    public void setApexPosition(double apexPosition) {
        Validate.isTrue(apexPosition < length && apexPosition >= 0, "Apex must be within the line!");

        this.apexPosition = apexPosition;
    }
}
