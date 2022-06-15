package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleDripLava extends Particle {
    public ParticleDripLava(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.LAVADRIP, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDripLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDripLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDripLava() {
        this(0, 0, 0, 1);
    }
}
