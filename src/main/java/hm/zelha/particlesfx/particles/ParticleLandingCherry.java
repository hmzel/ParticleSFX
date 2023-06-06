package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleLandingCherry extends Particle {
    public ParticleLandingCherry(double offsetX, double offsetY, double offsetZ, int count) {
        super("landing_cherry_leaves", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLandingCherry(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingCherry(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingCherry() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingCherry inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingCherry clone() {
        return new ParticleLandingCherry().inherit(this);
    }
}