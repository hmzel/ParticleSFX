package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.server.v1_10_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public abstract class TravellingParticle extends Particle {

    //some travelling particles have to be handled differently due to internal minecraft jank
    protected final boolean inverse;
    protected final double control;
    protected Location toGo;
    protected Vector velocity;

    protected TravellingParticle(EnumParticle particle, boolean inverse, double control, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.inverse = inverse;
        this.control = control;

        if (velocity != null) setVelocity(velocity);
        if (toGo != null) setLocationToGo(toGo);
    }

    @Override
    public TravellingParticle inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof TravellingParticle) {
            TravellingParticle inheritance = (TravellingParticle) particle;
            toGo = inheritance.toGo;
            velocity = inheritance.velocity;
        }

        return this;
    }

    @Override
    public abstract TravellingParticle clone();

    /**
     * Displays this particle with the given location and location to go without changing this object's toGo variable
     *
     * @param location location to display at
     * @param toGo location for this particle to go to after being displayed
     */
    public void display(Location location, Location toGo) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        display(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    /**
     * Displays this particle to the given players with the given location and location to go without changing this object's toGo variable
     *
     * @param location location to display at
     * @param toGo location for this particle to go after being displayed
     * @param players players to display this particle to
     */
    public void displayForPlayers(Location location, Location toGo, Player... players) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location, players);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    /**
     * Displays this particle with the given location and velocity without changing this object's velocity variable
     *
     * @param location location to display at
     * @param velocity velocity of particle
     */
    public void display(Location location, Vector velocity) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        display(location);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    /**
     * Displays this particle to the given players with the given location and velocity without changing this object's velocity variable
     *
     * @param location location to display at
     * @param velocity velocity of particle
     * @param players players to display this particle to
     */
    public void displayForPlayers(Location location, Vector velocity, Player... players) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayers(location, players);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    @Override
    protected Vector getXYZ(Location location) {
        Vector xyz = super.getXYZ(location);

        if (inverse) {
            if (velocity != null) {
                xyz.add(velocity);
            }

            if (toGo != null) {
                LVMath.toVector(xyz, toGo);
            }
        }

        if (velocity != null || toGo != null) {
            xyz.add(generateFakeOffset());
        }

        return xyz;
    }

    @Override
    protected Vector getOffsets(Location location) {
        Vector offsets = super.getOffsets(location);

        if (inverse && velocity != null) {
            offsets.zero().subtract(velocity);
        } else if (inverse && toGo != null) {
            LVMath.subtractToVector(offsets, location, toGo).add(fakeOffsetHelper);
        } else if (velocity != null) {
            offsets.zero().add(velocity).multiply(control);
        } else if (toGo != null) {
            LVMath.subtractToVector(offsets, toGo, location).subtract(fakeOffsetHelper).multiply(control);
        }

        return offsets;
    }

    @Override
    protected float getPacketSpeed() {
        if (velocity != null || toGo != null) return 1;

        return super.getPacketSpeed();
    }

    @Override
    protected int getPacketCount() {
        if (velocity != null || toGo != null) return 0;

        return super.getPacketCount();
    }

    /**
     * @param location where the particle should go after being displayed
     */
    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
        this.velocity = null;
    }

    /**
     * @param velocity how much you want the particle to travel
     */
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
        this.toGo = null;
    }

    /**
     * @param x how much you want the particle to travel in X
     * @param y how much you want the particle to travel in Y
     * @param z how much you want the particle to travel in Z
     */
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z);
        this.toGo = null;
    }

    /**
     * @return location this particle is using, null if using velocity
     */
    @Nullable
    public Location getLocationToGo() {
        return toGo;
    }

    /**
     * @return velocity this particle is using, null if using location to go
     */
    @Nullable
    public Vector getVelocity() {
        return velocity;
    }
}