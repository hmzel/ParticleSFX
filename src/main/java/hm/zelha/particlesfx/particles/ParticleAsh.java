package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleAsh extends Particle {
    public ParticleAsh(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ASH, offsetX, offsetY, offsetZ, count);
    }

    public ParticleAsh(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleAsh(int count) {
        this(0, 0, 0, count);
    }

    public ParticleAsh() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleAsh inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleAsh clone() {
        return new ParticleAsh().inherit(this);
    }
}