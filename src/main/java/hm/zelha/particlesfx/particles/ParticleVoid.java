package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleVoid extends Particle {
    public ParticleVoid(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SUSPENDED_DEPTH, offsetX, offsetY, offsetZ, 0, count, 0);
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

    @Override
    public ParticleVoid inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }
}
