package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingSpore extends Particle {
    public ParticleFallingSpore(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_spore_blossom", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleFallingSpore(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingSpore(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingSpore() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingSpore inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingSpore clone() {
        return new ParticleFallingSpore().inherit(this);
    }
}