package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGust extends Particle {
    public ParticleGust(double offsetX, double offsetY, double offsetZ, int count) {
        super("gust", offsetX, offsetY, offsetZ, count);
    }

    public ParticleGust(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGust(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGust() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGust inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGust clone() {
        return new ParticleGust().inherit(this);
    }
}
