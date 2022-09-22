package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RotationHandlerOld {

    private final Location lastRotatedAround = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Vector vectorHelper = new Vector(0, 0, 0);
    private final double[] oldPitchCosAndSin = {0, 0};
    private final double[] oldYawCosAndSin = {0, 0};
    private final double[] oldRollCosAndSin = {0, 0};
    private final List<Location> origins = new ArrayList<>();
    private double pitch;
    private double yaw;
    private double roll;
    private double oldPitch = 0;
    private double oldYaw = 0;
    private double oldRoll = 0;

    public RotationHandlerOld(double pitch, double yaw, double roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public RotationHandlerOld() {
        this(0, 0, 0);
    }

    public void apply(Location around, List<Location> locations) {
        Validate.isTrue(locations.size() == origins.size(), "Given locations must mimic stored location origins!");

        for (int i = 0; i < locations.size(); i++) {
            Location l = origins.get(i);
            Location l2 = locations.get(i);
            Vector v = apply(LVMath.subtractToVector(vectorHelper, l, around));

            LVMath.additionToLocation(l2, around, v);
        }
    }

    public Vector apply(Vector v) {
        applyPitch(v);
        applyYaw(v);
        applyRoll(v);

        return v;
    }

    public void set(double pitch, double yaw, double roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void add(double pitch, double yaw, double roll) {
        this.pitch += pitch;
        this.yaw += yaw;
        this.roll += roll;
    }

    public void reset() {
        pitch = 0;
        yaw = 0;
        roll = 0;

        origins.clear();
    }

    public void addOrigins(Location... locations) {
        for (int i = 0; i < locations.length; i++) origins.add(locations[i].clone());
    }

    public void removeOrigin(int index) {
        origins.remove(index);
    }

    public void moveOrigins(double x, double y, double z) {
        for (Location l : origins) l.add(x, y, z);
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
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setRoll(double roll) {
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

    public List<Location> getOrigins() {
        return origins;
    }

    public Vector getVectorHelper() {
        return vectorHelper;
    }

    public Location getLastRotatedAround() {
        return lastRotatedAround;
    }
}
