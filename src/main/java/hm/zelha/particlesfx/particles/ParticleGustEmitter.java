package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGustEmitter extends Particle {
    public ParticleGustEmitter(double offsetX, double offsetY, double offsetZ, int count) {
        super("gust_emitter", offsetX, offsetY, offsetZ, count);
    }

    public ParticleGustEmitter(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGustEmitter(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGustEmitter() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGustEmitter inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGustEmitter clone() {
        return new ParticleGustEmitter().inherit(this);
    }
}
