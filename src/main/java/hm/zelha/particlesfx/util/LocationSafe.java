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
    private Consumer<Location> mechanic = null;
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
     * @param causedByCompound whether or not this was caused by a {@link ParticleShapeCompound}
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

    /**
     * WARNING: DO NOT USE OUTSIDE PARTICLESFX INTERNALS. <br><br>
     *
     * DO NOT COME TO ME ABOUT EVERYTHING SCREWING UP IF YOU DO
     *
     * @param mechanic mechanic to be ran when this location is modified
     */
    public void setMechanic(Consumer<Location> mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }
    }

    @Override
    public void setZ(double z) {
        super.setZ(z);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }
    }

    @Override
    public Location add(Vector vec) {
        super.add(vec);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location add(Location vec) {
        super.add(vec);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location add(double x, double y, double z) {
        super.add(x, y, z);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location subtract(Vector vec) {
        super.subtract(vec);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location subtract(Location vec) {
        super.subtract(vec);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location subtract(double x, double y, double z) {
        super.subtract(x, y, z);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location multiply(double m) {
        super.multiply(m);
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public Location zero() {
        super.zero();
        changed = true;

        if (mechanic != null) {
            mechanic.accept(this);
        }

        for (Consumer<Location> mechanic : recalcMechanics.values()) {
            mechanic.accept(this);
        }

        return this;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
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
