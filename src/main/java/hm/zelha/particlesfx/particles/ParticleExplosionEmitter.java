package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleExplosionEmitter extends Particle {
    public ParticleExplosionEmitter(double offsetX, double offsetY, double offsetZ, int count) {
        super("explosion_emitter", offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleExplosionEmitter(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionEmitter(int count) {
        this(0, 0, 0, count);
    }

    public ParticleExplosionEmitter() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionEmitter inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleExplosionEmitter clone() {
        return new ParticleExplosionEmitter().inherit(this);
    }
}