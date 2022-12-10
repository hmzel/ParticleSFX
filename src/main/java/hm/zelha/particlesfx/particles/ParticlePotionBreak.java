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
import org.bukkit.potion.PotionType;

import java.util.List;

/**
 * NOTE: this effect makes sound!
 * <br><br>
 * since Effect.POTION_BREAK is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticlePotionBreak extends Particle {

    private PotionType type;

    public ParticlePotionBreak(PotionType type, int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);

        this.type = type;
    }

    public ParticlePotionBreak(PotionType type) {
        this(type, 1);
    }

    public ParticlePotionBreak(int count) {
        this(PotionType.WATER, count);
    }

    public ParticlePotionBreak() {
        this(PotionType.WATER, 1);
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
                    double distance = Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2);

                    if (distance > Math.pow(radius, 2)) continue;
                }

                //i wish BlockPositions were mutable
                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldEvent(
                                Effect.POTION_BREAK.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                                type.getDamageValue(), false
                        )
                );
            }
        }
    }

    public void setPotionType(PotionType type) {
        this.type = type;
    }

    public PotionType getPotionType() {
        return type;
    }
}
