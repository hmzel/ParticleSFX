package hm.zelha.particlesfx.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

/** S for SAFE! <p></p>
 *
 * This class is meant to allow me to keep locations mutable without shapes going completely bonkers if they're modified <p></p>
 *
 * This class also extends {@link Location} so you can use it in the same way as that class
 */
public class LocationS extends Location {

    private static final Rotation rotHelper = new Rotation(0, 0, 0);
    private Consumer<Location> mechanic = null;
    private boolean changed = false;

    public LocationS(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    /**
     * only meant to be used in {@link RotationHandler}, use at own risk
     */
    public void setUnsafely(double x, double y, double z) {
        super.setX(x);
        super.setY(y);
        super.setZ(z);
    }

    /**
     * WARNING: DO NOT USE OUTSIDE PARTICLESFX INTERNALS. <p></p>
     *
     * DO NOT COME TO ME ABOUT EVERYTHING SCREWING UP IF YOU DO
     */
    public void setMechanic(Consumer<Location> mechanic) {
        this.mechanic = mechanic;
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        changed = true;
        if (mechanic != null) mechanic.accept(this);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        changed = true;
        if (mechanic != null) mechanic.accept(this);
    }

    @Override
    public void setZ(double z) {
        super.setZ(z);
        changed = true;
        if (mechanic != null) mechanic.accept(this);
    }

    @Override
    public Location add(Vector vec) {
        super.add(vec);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location add(Location vec) {
        super.add(vec);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location add(double x, double y, double z) {
        super.add(x, y, z);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location subtract(Vector vec) {
        super.subtract(vec);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location subtract(Location vec) {
        super.subtract(vec);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location subtract(double x, double y, double z) {
        super.subtract(x, y, z);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location multiply(double m) {
        super.multiply(m);
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public Location zero() {
        super.zero();
        changed = true;
        if (mechanic != null) mechanic.accept(this);

        return this;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
    }

    @Override
    public LocationS clone() {
        LocationS l = new LocationS(getWorld(), getX(), getY(), getZ());

        l.setMechanic(mechanic);

        return l;
    }

    public Location cloneToLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public static Rotation getRotHelper() {
        return rotHelper;
    }
}
