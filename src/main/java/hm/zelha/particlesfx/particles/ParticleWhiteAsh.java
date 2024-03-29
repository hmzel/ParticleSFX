package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleWhiteAsh extends Particle {
    public ParticleWhiteAsh(double offsetX, double offsetY, double offsetZ, int count) {
        super("white_ash", offsetX, offsetY, offsetZ, count);
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