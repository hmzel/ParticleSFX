package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleHappy extends Particle {
    public ParticleHappy(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.HAPPY_VILLAGER, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleHappy(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleHappy(int count) {
        this(0, 0, 0, count);
    }

    public ParticleHappy() {
        this(0, 0, 0, 1);
    }
}
