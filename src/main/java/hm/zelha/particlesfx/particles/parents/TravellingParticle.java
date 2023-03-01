package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.List;

public class TravellingParticle extends Particle {

    //some travelling particles have to be handled differently due to internal minecraft jank
    protected final boolean inverse;
    protected final double control;
    protected Location toGo;
    protected Vector velocity;

    protected TravellingParticle(EnumParticle particle, boolean inverse, double control, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.inverse = inverse;
        this.control = control;

        setVelocity(velocity);
        setLocationToGo(toGo);
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
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < ((toGo == null && velocity == null) ? 1 : count); i++) {
            int count = 0;
            float speed = 1;
            double trueOffsetX = offsetX;
            double trueOffsetY = offsetY;
            double trueOffsetZ = offsetZ;
            Vector addition = null;

            if (toGo != null || velocity != null) {
                addition = generateFakeOffset();

                location.add(addition);
            }

            Location trueLocation = location;

            if (inverse && velocity != null) {
                trueOffsetX = -velocity.getX();
                trueOffsetY = -velocity.getY();
                trueOffsetZ = -velocity.getZ();

                location.add(velocity);
                addition.add(velocity);
            } else if (inverse && toGo != null) {
                trueOffsetX = location.getX() - toGo.getX();
                trueOffsetY = location.getY() - toGo.getY();
                trueOffsetZ = location.getZ() - toGo.getZ();
                trueLocation = toGo;
            } else if (velocity != null) {
                trueOffsetX = velocity.getX() * control;
                trueOffsetY = velocity.getY() * control;
                trueOffsetZ = velocity.getZ() * control;
            } else if (toGo != null) {
                trueOffsetX = (toGo.getX() - location.getX()) * control;
                trueOffsetY = (toGo.getY() - location.getY()) * control;
                trueOffsetZ = (toGo.getZ() - location.getZ()) * control;
            } else {
                speed = 0;
                count = this.count;
            }

            for (int i2 = 0; i2 < players.size(); i2++) {
                EntityPlayer p = players.get(i2).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2);

                    if (distance > Math.pow(radius, 2)) continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldParticles(
                                particle, true, (float) trueLocation.getX(), (float) trueLocation.getY(), (float) trueLocation.getZ(),
                                (float) trueOffsetX, (float) trueOffsetY, (float) trueOffsetZ, speed, count
                        )
                );
            }

            if (addition != null) {
                location.subtract(addition);
            }
        }
    }

    public void display(Location location, Location toGo) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        display(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    public void displayForPlayers(Location location, Location toGo, Player... players) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location, players);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    public void display(Location location, Vector velocity) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        display(location);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    public void displayForPlayers(Location location, Vector velocity, Player... players) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayers(location, players);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
    }

    /**
     * @param velocity how much you want the particle to travel
     */
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * @param x how much you want the particle to travel in X
     * @param y how much you want the particle to travel in Y
     * @param z how much you want the particle to travel in Z
     */
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z);
    }

    /**
     * nullable to save resources
     *
     * @return location this particle is using
     */
    @Nullable
    public Location getLocationToGo() {
        return toGo;
    }

    /**
     * nullable to save resources
     *
     * @return velocity this particle is using
     */
    @Nullable
    public Vector getVelocity() {
        return velocity;
    }
}
