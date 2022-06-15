package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleExplosionHuge extends Particle {
    public ParticleExplosionHuge(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.EXPLOSION_HUGE, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleExplosionHuge(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionHuge(int count) {
        this(0, 0, 0, count);
    }

    public ParticleExplosionHuge() {
        this(0, 0, 0, 1);
    }
}
