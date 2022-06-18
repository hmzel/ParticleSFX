package hm.zelha.particlesfx.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/** location & vector math utils */
public final class LVMath {

    private LVMath() {
    }

    public static Vector toVector(Vector vector, Location location) {
        return vector.setX(location.getX()).setY(location.getY()).setZ(location.getZ());
    }

    public static Vector subtractToVector(Vector vector, Location location, Location subtrahend) {
        vector.setX(location.getX() - subtrahend.getX());
        vector.setY(location.getY() - subtrahend.getY());
        vector.setZ(location.getZ() - subtrahend.getZ());

        return vector;
    }

    public static Location additionToLocation(Location location, Location toAddTo, Vector addend) {
        location.setX(toAddTo.getX() + addend.getX());
        location.setY(toAddTo.getY() + addend.getY());
        location.setZ(toAddTo.getZ() + addend.getZ());

        return location;
    }
}
