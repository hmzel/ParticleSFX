package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.Shape;
import org.apache.commons.lang3.Validate;
import org.bukkit.util.Vector;

public class Rotation {

    private final Rotation axisRotation;
    private final Axis[] axes = {Axis.PITCH, Axis.YAW, Axis.ROLL};
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

        axisRotation = new Rotation(true);
    }

    public Rotation() {
        this(0, 0, 0);
    }

    private Rotation(boolean isAxis) {
        axisRotation = null;
    }

    public Rotation clone() {
        return new Rotation().inherit(this);
    }

    /**
     * @param rotation object for this rotation class to copy data from
     * @return this object
     */
    public Rotation inherit(Rotation rotation) {
        if (axisRotation != null) {
            axisRotation.inherit(rotation.axisRotation);
        }

        axes[0] = rotation.axes[0];
        axes[1] = rotation.axes[1];
        axes[2] = rotation.axes[2];
        oldPitchCosAndSin[0] = rotation.oldPitchCosAndSin[0];
        oldPitchCosAndSin[1] = rotation.oldPitchCosAndSin[1];
        oldYawCosAndSin[0] = rotation.oldYawCosAndSin[0];
        oldYawCosAndSin[1] = rotation.oldYawCosAndSin[1];
        oldRollCosAndSin[0] = rotation.oldRollCosAndSin[0];
        oldRollCosAndSin[1] = rotation.oldRollCosAndSin[1];
        pitch = rotation.pitch;
        yaw = rotation.yaw;
        roll = rotation.roll;
        oldPitch = rotation.oldPitch;
        oldYaw = rotation.oldYaw;
        oldRoll = rotation.oldRoll;

        return this;
    }

    /**
     * @param shape object for this rotation class to copy data from
     * @return this object
     */
    public Rotation inherit(Shape shape) {
        Axis[] axes = shape.getRotationOrder();

        setRotationOrder(axes[0], axes[1], axes[2]);
        set(shape.getPitch(), shape.getYaw(), shape.getRoll());
        setAxis(shape.getAxisPitch(), shape.getAxisYaw(), shape.getAxisRoll());

        return this;
    }

    /**
     * @param v vector for this rotation to be applied to
     * @return the given vector
     */
    public Vector apply(Vector v) {
        if (axisRotation != null) {
            axisRotation.apply(v);
        }

        applyForAxis(axes[0], v);
        applyForAxis(axes[1], v);
        applyForAxis(axes[2], v);

        return v;
    }

    /**
     * @param v vector for this rotation's pitch to be applied to
     */
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


    /**
     * @param v vector for this rotation's yaw to be applied to
     */
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

    /**
     * @param v vector for this rotation's roll to be applied to
     */
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

    private void applyForAxis(Axis axis, Vector v) {
        if (axis == Axis.PITCH) {
            applyPitch(v);
        }

        if (axis == Axis.YAW) {
            applyYaw(v);
        }

        if (axis == Axis.ROLL) {
            applyRoll(v);
        }
    }

    public void add(double pitch, double yaw, double roll) {
        setPitch(this.pitch + pitch);
        setYaw(this.yaw + yaw);
        setRoll(this.roll + roll);
    }

    /**
     * adds the given pitch, yaw, and roll to this rotation's axis, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     *
     * @param pitch how much to add to pitch
     * @param yaw how much to add to yaw
     * @param roll how much to add to roll
     */
    public void addAxis(double pitch, double yaw, double roll) {
        axisRotation.add(pitch, yaw, roll);
    }

    /**
     * Changes the order that rotations are applied. Default is pitch, yaw, then roll.
     *
     * @param first first axis to be applied
     * @param second second axis to be applied
     * @param third third axis to be applied
     */
    public void setRotationOrder(Axis first, Axis second, Axis third) {
        Validate.isTrue(first != second && first != third && second != third, "Can't have the same axis twice!");

        axes[0] = first;
        axes[1] = second;
        axes[2] = third;

        if (axisRotation != null) {
            axisRotation.setRotationOrder(first, second, third);
        }
    }

    public void set(double pitch, double yaw, double roll) {
        setPitch(pitch);
        setYaw(yaw);
        setRoll(roll);
    }

    public void setPitch(double pitch) {
        if (!Double.isFinite(pitch)) pitch = 0;

        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        if (!Double.isFinite(yaw)) yaw = 0;

        this.yaw = yaw;
    }

    public void setRoll(double roll) {
        if (!Double.isFinite(roll)) roll = 0;

        this.roll = roll;
    }

    /**
     * sets this rotation's axis to the given pitch, yaw, and roll, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     *
     * @param pitch pitch to set
     * @param yaw yaw to set
     * @param roll roll to set
     */
    public void setAxis(double pitch, double yaw, double roll) {
        axisRotation.set(pitch, yaw, roll);
    }

    /**
     * sets this rotation's axis to the given pitch, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     *
     * @param pitch pitch to set
     */
    public void setAxisPitch(double pitch) {
        axisRotation.setPitch(pitch);
    }

    /**
     * sets this rotation's axis to the given yaw, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     *
     * @param yaw yaw to set
     */
    public void setAxisYaw(double yaw) {
        axisRotation.setYaw(yaw);
    }

    /**
     * sets this rotation's axis to the given roll, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     *
     * @param roll roll to set
     */
    public void setAxisRoll(double roll) {
        axisRotation.setRoll(roll);
    }

    /**
     * @return an array of axes where [0] is the first rotation applied, [1] is the second, and [2] is the third.
     */
    public Axis[] getRotationOrder() {
        return new Axis[] {axes[0], axes[1], axes[2]};
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

    /**
     * @return the pitch of this rotation's axis, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     */
    public double getAxisPitch() {
        return axisRotation.getPitch();
    }

    /**
     * @return the yaw of this rotation's axis, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     */
    public double getAxisYaw() {
        return axisRotation.getYaw();
    }

    /**
     * @return the roll of this rotation's axis, which will be applied before this rotation in {@link Rotation#apply(Vector)}
     */
    public double getAxisRoll() {
        return axisRotation.getRoll();
    }

    public enum Axis {
        PITCH,
        YAW,
        ROLL
    }
}