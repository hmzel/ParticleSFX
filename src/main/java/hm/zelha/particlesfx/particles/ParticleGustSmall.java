package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGustSmall extends Particle {
    public ParticleGustSmall(double offsetX, double offsetY, double offsetZ, int count) {
        super("small_gust", offsetX, offsetY, offsetZ, count);
    }

    public ParticleGustSmall(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGustSmall(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGustSmall() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGustSmall inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGustSmall clone() {
        return new ParticleGustSmall().inherit(this);
    }
}
