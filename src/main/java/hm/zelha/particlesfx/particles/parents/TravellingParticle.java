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

    protected final double control;
    protected Location toGo;
    protected Vector velocity;

    protected TravellingParticle(EnumParticle particle, double control, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.control = control;
        this.velocity = velocity;
        this.toGo = toGo;
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        int count2 = count;

        if (toGo == null && velocity == null) {
            count2 = 1;
        }

        for (int i = 0; i != count2; i++) {
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

            if (velocity != null) {
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
                    double distance = Math.sqrt(Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2));

                    if (distance > radius) continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldParticles(
                                particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
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
     * since particle velocity is very volatile, the given velocity is automatically multiplied by a decimal in all default implementations of
     * TravellingParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     * <p></p>
     * said decimal makes sure that every VelocityParticle implementation follows the same convention,
     * such that a Vector with x,y,z at 1 would make the particle move 1 block in all 3 axis on average, if speed is 1.
     *
     * @param velocity velocity to set
     */
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by a decimal in all default implementations of
     * TravellingParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     * <p></p>
     * said decimal makes sure that every VelocityParticle implementation follows the same convention,
     * such that a Vector with x,y,z at 1 would make the particle move 1 block in all 3 axis on average, if speed is 1.
     *
     * @param x x velocity
     * @param y y velocity
     * @param z z velocity
     */
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z);
    }

    /** nullable to save resources */
    @Nullable
    public Location getLocationToGo() {
        return toGo;
    }

    /** nullable to save resources */
    @Nullable
    public Vector getVelocity() {
        return velocity;
    }
}
