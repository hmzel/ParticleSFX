package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;

import java.util.List;

/**
 * while this effect is really cool, it summons around 30 particles per effect, which will look strange if many of them are displayed
 * due to the 4,000 client-side limit
 * <br><br>
 * since Effect.ENDER_SIGNAL is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their packet uses BlockPosition
 */
public class ParticleEnderSignal extends Particle {
    /**@see ParticleEnderSignal*/
    public ParticleEnderSignal(int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);
    }

    /**@see ParticleEnderSignal*/
    public ParticleEnderSignal() {
        this(1);
    }

    @Override
    public ParticleEnderSignal inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEnderSignal clone() {
        return new ParticleEnderSignal().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < count; i++) {
            for (int i2 = 0; i2 < players.size(); i2++) {
                EntityPlayer p = players.get(i2).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2);

                    if (distance > Math.pow(radius, 2)) continue;
                }

                //i wish BlockPositions were mutable
                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldEvent(
                                Effect.ENDER_SIGNAL.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                                Effect.ENDER_SIGNAL.getId(), false
                        )
                );
            }
        }
    }
}
