package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleLandingHoney extends Particle {
    public ParticleLandingHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super("landing_honey", offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleLandingHoney(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingHoney(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingHoney() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingHoney inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingHoney clone() {
        return new ParticleLandingHoney().inherit(this);
    }
}