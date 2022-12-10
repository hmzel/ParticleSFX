package hm.zelha.particlesfx.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/** location and vector math utils for cleaner code, not meant to be used outside ParticleSFX internals */
public final class LVMath {

    private LVMath() {
    }

    public static double getAbsoluteSum(Vector vector) {
        return Math.abs(vector.getX()) + Math.abs(vector.getY()) + Math.abs(vector.getZ());
    }

    public static Vector toVector(Vector vector, Location location) {
        return vector.setX(location.getX()).setY(location.getY()).setZ(location.getZ());
    }

    public static Vector divide(Vector vector, double dividend) {
        if (dividend == 0) return vector.zero();

        return vector.multiply(1 / dividend);
    }

    public static Vector subtractToVector(Vector toSet, Location location, Location subtrahend) {
        toSet.setX(location.getX() - subtrahend.getX());
        toSet.setY(location.getY() - subtrahend.getY());
        toSet.setZ(location.getZ() - subtrahend.getZ());

        return toSet;
    }

    public static Location additionToLocation(Location toSet, Location toAddTo, Vector addend, boolean causedByCompound) {
        if (toSet instanceof LocationSafe) {
            ((LocationSafe) toSet).setUnsafely(toAddTo.getX() + addend.getX(), toAddTo.getY() + addend.getY(), toAddTo.getZ() + addend.getZ(), causedByCompound);
        } else {
            toSet.setX(toAddTo.getX() + addend.getX());
            toSet.setY(toAddTo.getY() + addend.getY());
            toSet.setZ(toAddTo.getZ() + addend.getZ());
        }

        return toSet;
    }

    public static Location additionToLocation(Location toSet, Location toAddTo, Vector addend) {
        return additionToLocation(toSet, toAddTo, addend, false);
    }
}
