package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGustEmitterLarge extends Particle {
    public ParticleGustEmitterLarge(double offsetX, double offsetY, double offsetZ, int count) {
        super("gust_emitter_large", offsetX, offsetY, offsetZ, count);
    }

    public ParticleGustEmitterLarge(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGustEmitterLarge(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGustEmitterLarge() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGustEmitterLarge inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGustEmitterLarge clone() {
        return new ParticleGustEmitterLarge().inherit(this);
    }
}
