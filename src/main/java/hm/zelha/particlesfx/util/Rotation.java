package hm.zelha.particlesfx.util;

import org.bukkit.util.Vector;

public class Rotation {

    private final double[] oldPitchCosAndSin = {0, 0};
    private final double[] oldYawCosAndSin = {0, 0};
    private final double[] oldRollCosAndSin = {0, 0};
    private double pitch;
    private double yaw;
    private double roll;
    private double oldPitch = 0;
    private double oldYaw = 0;
    private double oldRoll = 0;

    public Rotation(double pitch, double yaw, double roll) {
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    public Rotation() {
        this(0, 0, 0);
    }

    public Vector apply(Vector v) {
        applyPitch(v);
        applyYaw(v);
        applyRoll(v);

        return v;
    }

    public void set(double pitch, double yaw, double roll) {
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    public void add(double pitch, double yaw, double roll) {
        setPitch(this.pitch + pitch);
        setYaw(this.yaw + yaw);
        setRoll(this.roll + roll);
    }

    public void applyPitch(Vector v) {
        if (pitch == 0) return;

        double y, z, cos, sin, angle;

        if (pitch != oldPitch) {
            angle = Math.toRadians(pitch);
            cos = Math.cos(angle);
            sin = Math.sin(angle);
            oldPitchCosAndSin[0] = cos;
            oldPitchCosAndSin[1] = sin;
            oldPitch = pitch;
        } else {
            cos = oldPitchCosAndSin[0];
            sin = oldPitchCosAndSin[1];
        }

        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;

        v.setY(y).setZ(z);
    }

    public void applyYaw(Vector v) {
        if (yaw == 0) return;

        double x, z, cos, sin, angle;

        if (yaw != oldYaw) {
            angle = Math.toRadians(-yaw);
            cos = Math.cos(angle);
            sin = Math.sin(angle);
            oldYawCosAndSin[0] = cos;
            oldYawCosAndSin[1] = sin;
            oldYaw = yaw;
        } else {
            cos = oldYawCosAndSin[0];
            sin = oldYawCosAndSin[1];
        }

        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;

        v.setX(x).setZ(z);
    }

    public void applyRoll(Vector v) {
        if (roll == 0) return;

        double x, y, cos, sin, angle;

        if (roll != oldRoll) {
            angle = Math.toRadians(roll);
            cos = Math.cos(angle);
            sin = Math.sin(angle);
            oldRollCosAndSin[0] = cos;
            oldRollCosAndSin[1] = sin;
            oldRoll = roll;
        } else {
            cos = oldRollCosAndSin[0];
            sin = oldRollCosAndSin[1];
        }

        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;

        v.setX(x).setY(y);
    }

    public void setPitch(double pitch) {
        if (!Double.isFinite(pitch)) {
            pitch = 0;
        }

        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        if (!Double.isFinite(yaw)) {
            yaw = 0;
        }

        this.yaw = yaw;
    }

    public void setRoll(double roll) {
        if (!Double.isFinite(roll)) {
            roll = 0;
        }

        this.roll = roll;
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
