package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleThundercloud extends Particle {
    public ParticleThundercloud(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.VILLAGER_THUNDERCLOUD, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleThundercloud(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleThundercloud(int count) {
        this(0, 0, 0, count);
    }

    public ParticleThundercloud() {
        this(0, 0, 0, 1);
    }
}
