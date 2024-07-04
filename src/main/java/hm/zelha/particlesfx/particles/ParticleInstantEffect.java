package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleInstantEffect extends Particle {
    public ParticleInstantEffect(double offsetX, double offsetY, double offsetZ, int count) {
        super("instant_effect", offsetX, offsetY, offsetZ, count);
    }

    public ParticleInstantEffect(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInstantEffect(int count) {
        this(0, 0, 0, count);
    }

    public ParticleInstantEffect() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleInstantEffect inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleInstantEffect clone() {
        return new ParticleInstantEffect().inherit(this);
    }
}