package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleWhiteAsh extends Particle {
    public ParticleWhiteAsh(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.WHITE_ASH, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleWhiteAsh(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWhiteAsh(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWhiteAsh() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWhiteAsh inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWhiteAsh clone() {
        return new ParticleWhiteAsh().inherit(this);
    }
}
