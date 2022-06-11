package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleFlame extends Particle {
    public ParticleFlame(float offsetX, float offsetY, float offsetZ, float speed, int count) {
        super(Effect.FLAME, offsetX, offsetY, offsetZ, speed, count, 64);
    }

    public ParticleFlame(float offsetX, float offsetY, float offsetZ) {
        super(Effect.FLAME, offsetX, offsetY, offsetZ, 0, 1, 64);
    }

    public ParticleFlame(float speed, int count) {
        super(Effect.FLAME, 0, 0, 0, speed, count, 64);
    }

    public ParticleFlame(float speed) {
        super(Effect.FLAME, 0, 0, 0, speed, 1, 64);
    }

    public ParticleFlame(int count) {
        super(Effect.FLAME, 0, 0, 0, 0, count, 64);
    }

    public ParticleFlame() {
        super(Effect.FLAME, 0, 0, 0, 0, 0, 0);
    }
}
