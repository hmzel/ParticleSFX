package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleOmenTrial extends Particle {
    public ParticleOmenTrial(double offsetX, double offsetY, double offsetZ, int count) {
        super("trial_omen", offsetX, offsetY, offsetZ, count);
    }

    public ParticleOmenTrial(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleOmenTrial(int count) {
        this(0, 0, 0, count);
    }

    public ParticleOmenTrial() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleOmenTrial inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleOmenTrial clone() {
        return new ParticleOmenTrial().inherit(this);
    }
}
