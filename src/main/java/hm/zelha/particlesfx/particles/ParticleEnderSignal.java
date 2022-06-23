package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * while this effect is really cool, it summons around 30 particles per effect, which will look strange if many of them are displayed
 * due to the 4,000 client-side limit
 * <p></p>
 * since Effect.ENDER_SIGNAL is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticleEnderSignal extends Particle {
    public ParticleEnderSignal(int count) {
        super(Effect.ENDER_SIGNAL, 0, 0, 0, 0, count, 0);
    }

    public ParticleEnderSignal() {
        this(1);
    }

    @Override
    protected void display(Location location, Player... players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i != count; i++) {
            for (Player player : players) {
                EntityPlayer p = ((CraftPlayer) player).getHandle();

                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0 && (Math.abs(location.getX() - p.locX) + Math.abs(location.getY() - p.locY) + Math.abs(location.getZ() - p.locZ)) > radius) {
                    continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldEvent(
                                particle.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                                particle.getId(), false
                        )
                );
            }
        }
    }
}
