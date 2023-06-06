package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Effect;
import org.bukkit.Location;

/**
 * while this effect is really cool, it summons around 30 particles per effect.
 * <br><br>
 * since Effect.ENDER_SIGNAL is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their packet uses BlockPosition
 */
public class ParticleEnderSignal extends Particle {

    private final BlockPosition.MutableBlockPosition pos = new BlockPosition.MutableBlockPosition(0, 0, 0);

    /**@see ParticleEnderSignal*/
    public ParticleEnderSignal(int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count);
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
    protected Packet getStrangePacket(Location location) {
        return new PacketPlayOutWorldEvent(
                Effect.ENDER_SIGNAL.getId(), pos.d(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                Effect.ENDER_SIGNAL.getId(), false
        );
    }
}