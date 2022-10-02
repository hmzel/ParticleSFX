package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleSplash extends Particle {
    public ParticleSplash(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.WATER_SPLASH, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleSplash(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSplash(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSplash() {
        this(0, 0, 0, 1);
    }
}
