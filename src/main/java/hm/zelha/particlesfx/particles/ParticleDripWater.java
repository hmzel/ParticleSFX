package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleDripWater extends Particle {
    public ParticleDripWater(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.WATERDRIP, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDripWater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDripWater(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDripWater() {
        this(0, 0, 0, 1);
    }
}
