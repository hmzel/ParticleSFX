package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldEvent;
import org.bukkit.Effect;
import org.bukkit.Location;

/**
 * since Effect.MOBSPAWNER_FLAMES is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their packet uses BlockPosition
 */
public class ParticleMobspawnerFlames extends Particle {
    /**@see ParticleMobspawnerFlames*/
    public ParticleMobspawnerFlames(int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);
    }

    /**@see ParticleMobspawnerFlames*/
    public ParticleMobspawnerFlames() {
        this(1);
    }

    @Override
    public ParticleMobspawnerFlames inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleMobspawnerFlames clone() {
        return new ParticleMobspawnerFlames().inherit(this);
    }

    @Override
    protected Packet getStrangePacket(Location location) {
        return new PacketPlayOutWorldEvent(
                Effect.MOBSPAWNER_FLAMES.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                Effect.MOBSPAWNER_FLAMES.getId(), false
        );
    }
}
