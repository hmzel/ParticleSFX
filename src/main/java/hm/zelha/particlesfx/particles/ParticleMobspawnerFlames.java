package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.List;

/**
 * since Effect.MOBSPAWNER_FLAMES is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticleMobspawnerFlames extends Particle {
    public ParticleMobspawnerFlames(int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);
    }

    public ParticleMobspawnerFlames() {
        this(1);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i != count; i++) {
            for (int i2 = 0; i2 < players.size(); i2++) {
                EntityPlayer p = players.get(i2).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.sqrt(Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2));

                    if (distance > radius) continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldEvent(
                                Effect.MOBSPAWNER_FLAMES.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                                Effect.MOBSPAWNER_FLAMES.getId(), false
                        )
                );
            }
        }
    }
}
