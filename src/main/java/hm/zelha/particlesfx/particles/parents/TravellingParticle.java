package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.server.v1_16_R1.ParticleParam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public abstract class TravellingParticle extends Particle {

    //some travelling particles have to be handled differently due to minecraft jank
    protected final double control;
    protected boolean inverse;
    protected Location toGo;
    protected Vector velocity;

    protected TravellingParticle(ParticleParam particle, boolean inverse, double control, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
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
            toGo = ((TravellingParticle) particle).toGo;
            velocity = ((TravellingParticle) particle).velocity;
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
        display(location, toGo, null);
    }

    /**
     * Displays this particle to the given players with the given location and location to go without changing this object's toGo variable
     *
     * @param location location to display at
     * @param toGo location for this particle to go after being displayed
     * @param players players to display this particle to
     */
    public void displayForPlayers(Location location, Location toGo, Player... players) {
        display(location, toGo, null, players);
    }

    /**
     * Displays this particle with the given location and velocity without changing this object's velocity variable
     *
     * @param location location to display at
     * @param velocity velocity of particle
     */
    public void display(Location location, Vector velocity) {
        display(location, null, velocity);
    }

    /**
     * Displays this particle to the given players with the given location and velocity without changing this object's velocity variable
     *
     * @param location location to display at
     * @param velocity velocity of particle
     * @param players players to display this particle to
     */
    public void displayForPlayers(Location location, Vector velocity, Player... players) {
        display(location, null, velocity, players);
    }

    @Override
    protected Vector getXYZ(Location location) {
        Vector xyz = super.getXYZ(location);

        if (velocity != null || toGo != null) {
            xyz.add(generateFakeOffset());
        }

        if (inverse) {
            if (velocity != null) {
                xyz.add(velocity);
            }

            if (toGo != null) {
                LVMath.toVector(xyz, toGo);
            }
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

    private void display(Location location, Location toGo, Vector velocity, Player... players) {
        Vector old = this.velocity;
        Location oldToGo = this.toGo;
        this.velocity = velocity;
        this.toGo = toGo;

        if (players.length != 0) {
            displayForPlayers(location, players);
        } else {
            display(location);
        }

        this.velocity = old;
        this.toGo = oldToGo;
    }

    /**
     * @param location where the particle should go after being displayed
     */
    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
        this.velocity = null;
    }

    /**
     * @param x where you want the particle to go in X
     * @param y where you want the particle to go in Y
     * @param z where you want the particle to go in Z
     */
    public void setLocationToGo(double x, double y, double z) {
        setLocationToGo(new Location(null, x, y, z));
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
        setVelocity(new Vector(x, y, z));
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