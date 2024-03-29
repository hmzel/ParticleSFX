package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleLandingLava extends Particle {
    public ParticleLandingLava(double offsetX, double offsetY, double offsetZ, int count) {
        super("landing_lava", offsetX, offsetY, offsetZ, count);
    }

    public ParticleLandingLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingLava clone() {
        return new ParticleLandingLava().inherit(this);
    }
}