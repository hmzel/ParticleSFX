package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleEffect extends Particle {
    public ParticleEffect(double offsetX, double offsetY, double offsetZ, int count) {
        super("effect", offsetX, offsetY, offsetZ, count);
    }

    public ParticleEffect(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEffect(int count) {
        this(0, 0, 0, count);
    }

    public ParticleEffect() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleEffect inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEffect clone() {
        return new ParticleEffect().inherit(this);
    }
}