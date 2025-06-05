package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFirefly extends Particle {
    public ParticleFirefly(double offsetX, double offsetY, double offsetZ, int count) {
        super("firefly", offsetX, offsetY, offsetZ, count);
    }

    public ParticleFirefly(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFirefly(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFirefly() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFirefly inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFirefly clone() {
        return new ParticleFirefly().inherit(this);
    }
}
