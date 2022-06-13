package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

/**
 * since Effect.MOBSPAWNER_FLAMES is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticleMobspawnerFlames extends Particle {
    public ParticleMobspawnerFlames(int count) {
        super(Effect.MOBSPAWNER_FLAMES, 0, 0, 0, 0, count, 0);
    }

    public ParticleMobspawnerFlames() {
        this(1);
    }
}
