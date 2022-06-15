package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleHeart extends Particle {
    public ParticleHeart(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.HEART, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleHeart(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleHeart(int count) {
        this(0, 0, 0, count);
    }

    public ParticleHeart() {
        this(0, 0, 0, 1);
    }
}
