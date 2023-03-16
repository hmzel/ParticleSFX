package hm.zelha.particlesfx.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/** location and vector math utils for cleaner code, not meant to be used outside ParticleSFX internals */
public final class LVMath {

    private LVMath() {
    }

    /**
     * @param vector vector to get absolute sum from
     * @return the sum of the absolute values of the X, Y, and Z of the given vector
     */
    public static double getAbsoluteSum(Vector vector) {
        return Math.abs(vector.getX()) + Math.abs(vector.getY()) + Math.abs(vector.getZ());
    }

    /**
     * @param vector vector to copy the given location's info to
     * @param location the location to copy
     * @return the given vector
     */
    public static Vector toVector(Vector vector, Location location) {
        return vector.setX(location.getX()).setY(location.getY()).setZ(location.getZ());
    }

    /**
     * @param vector vector to divide
     * @param dividend number to divide the given vector's X, Y, and Z coordinates by
     * @return the given vector, with its coordinates divided by the given dividend or zeroed if the dividend is 0.
     */
    public static Vector divide(Vector vector, double dividend) {
        if (dividend == 0) return vector.zero();

        return vector.multiply(1 / dividend);
    }

    /**
     * @param toSet vector to modify
     * @param location location to be subtracted from
     * @param subtrahend location to be subtracted
     * @return the given vector
     */
    public static Vector subtractToVector(Vector toSet, Location location, Location subtrahend) {
        toSet.setX(location.getX() - subtrahend.getX());
        toSet.setY(location.getY() - subtrahend.getY());
        toSet.setZ(location.getZ() - subtrahend.getZ());

        return toSet;
    }

    /**
     * @param toSet location to modify
     * @param toAddTo location to be added to
     * @param addend vector to add
     * @param causedByCompound whether this was caused by a {@link ParticleShapeCompound}
     * @return the location that was modified
     */
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

    /**
     * @param toSet location to modify
     * @param toAddTo location to be added to
     * @param addend vector to add
     * @return the location that was modified
     */
    public static Location additionToLocation(Location toSet, Location toAddTo, Vector addend) {
        return additionToLocation(toSet, toAddTo, addend, false);
    }
}
