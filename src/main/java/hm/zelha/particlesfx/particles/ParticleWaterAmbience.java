package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

/**
 * NOTE: only visible underwater
 */
public class ParticleWaterAmbience extends Particle {
    public ParticleWaterAmbience(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SUSPENDED, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleWaterAmbience(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaterAmbience(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWaterAmbience() {
        this(0, 0, 0, 1);
    }
}
