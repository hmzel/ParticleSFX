package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

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
        super(Effect.ENDER_SIGNAL, 0, 0, 0, 0, count, 64);
    }

    public ParticleEnderSignal() {
        super(Effect.ENDER_SIGNAL, 0, 0, 0, 0, 1, 64);
    }
}
