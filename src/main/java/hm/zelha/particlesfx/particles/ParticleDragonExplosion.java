package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldEvent;
import org.bukkit.Effect;
import org.bukkit.Location;

/**
 * while this effect is really cool, it summons a ton of particles per effect.
 * <br><br>
 * since Effect.DRAGON_BREATH is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their packet uses BlockPosition
 */
public class ParticleDragonExplosion extends Particle {
    /**@see ParticleDragonExplosion*/
    public ParticleDragonExplosion(int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);
    }

    /**@see ParticleDragonExplosion*/
    public ParticleDragonExplosion() {
        this(1);
    }

    @Override
    public ParticleDragonExplosion inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDragonExplosion clone() {
        return new ParticleDragonExplosion().inherit(this);
    }

    @Override
    protected Packet getStrangePacket(Location location) {
        return new PacketPlayOutWorldEvent(
                Effect.DRAGON_BREATH.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                Effect.DRAGON_BREATH.getId(), false
        );
    }
}
