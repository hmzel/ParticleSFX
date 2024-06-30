package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGustEmitterSmall extends Particle {
    public ParticleGustEmitterSmall(double offsetX, double offsetY, double offsetZ, int count) {
        super("gust_emitter_small", offsetX, offsetY, offsetZ, count);
    }

    public ParticleGustEmitterSmall(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGustEmitterSmall(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGustEmitterSmall() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGustEmitterSmall inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGustEmitterSmall clone() {
        return new ParticleGustEmitterSmall().inherit(this);
    }
}
