package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.RotationHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class is meant to allow me to keep locations mutable without shapes going completely bonkers if they're modified <br><br>
 *
 * This class also extends {@link Location} so you can use it in the same way as that class
 */
public class LocationSafe extends Location {

    private final Map<ParticleShapeCompound, Consumer<Location>> recalcMechanics = new HashMap<>();
    private boolean changed = false;

    /**@see LocationSafe*/
    public LocationSafe(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    /**@see LocationSafe*/
    public LocationSafe(Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    /**
     * only meant to be used in {@link RotationHandler} or {@link ParticleShapeCompound}, use at own risk
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @param causedByCompound whether this was caused by a {@link ParticleShapeCompound}
     */
    public void setUnsafely(double x, double y, double z, boolean causedByCompound) {
        super.setX(x);
        super.setY(y);
        super.setZ(z);

        if (causedByCompound) {
            this.changed = true;
        } else {
            for (Consumer<Location> mechanic : recalcMechanics.values()) {
                mechanic.accept(this);
            }
        }
    }

    /**
     * WARNING: DO NOT USE OUTSIDE PARTICLESFX INTERNALS. <br><br>
     *
     * DO NOT COME TO ME ABOUT EVERYTHING SCREWING UP IF YOU DO
     *
     * @param owner the owner of the shape that owns this location
     * @param mechanic mechanic to be ran when this location is modified
     */
    public void addRecalcMechanic(ParticleShapeCompound owner, Consumer<Location> mechanic) {
        recalcMechanics.put(owner, mechanic);
    }

    /**
     * WARNING: DO NOT USE OUTSIDE PARTICLESFX INTERNALS. <br><br>
     *
     * DO NOT COME TO ME ABOUT EVERYTHING SCREWING UP IF YOU DO
     *
     * @param owner the owner of the shape that owns this location
     */
    public void removeRecalcMechanic(ParticleShapeCompound owner) {
        recalcMechanics.remove(owner);
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        setChanged(true);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        setChanged(true);
    }

    @Override
    public void setZ(double z) {
        super.setZ(z);
        setChanged(true);
    }

    @Override
    public LocationSafe add(Vector vec) {
        super.add(vec);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe add(Location vec) {
        super.add(vec);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe add(double x, double y, double z) {
        super.add(x, y, z);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe subtract(Vector vec) {
        super.subtract(vec);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe subtract(Location vec) {
        super.subtract(vec);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe subtract(double x, double y, double z) {
        super.subtract(x, y, z);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe multiply(double m) {
        super.multiply(m);
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe zero() {
        super.zero();
        setChanged(true);

        return this;
    }

    @Override
    public LocationSafe clone() {
        return new LocationSafe(getWorld(), getX(), getY(), getZ());
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;

        if (changed) {
            for (Consumer<Location> mechanic : recalcMechanics.values()) {
                mechanic.accept(this);
            }
        }
    }
}
