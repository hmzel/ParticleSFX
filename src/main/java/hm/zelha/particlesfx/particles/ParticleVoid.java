package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleVoid extends Particle {
    public ParticleVoid(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.VOID_FOG, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleVoid(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVoid(int count) {
        this(0, 0, 0, count);
    }

    public ParticleVoid() {
        this(0, 0, 0, 1);
    }
}
