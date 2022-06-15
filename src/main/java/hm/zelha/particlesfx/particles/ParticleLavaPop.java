package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleLavaPop extends Particle {
    public ParticleLavaPop(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.LAVA_POP, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLavaPop(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLavaPop(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLavaPop() {
        this(0, 0, 0, 1);
    }
}
