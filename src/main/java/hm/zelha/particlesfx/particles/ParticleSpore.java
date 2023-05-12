package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleSpore extends Particle {
    public ParticleSpore(double offsetX, double offsetY, double offsetZ, int count) {
        super("spore_blossom_air", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleSpore(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpore(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSpore() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSpore inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSpore clone() {
        return new ParticleSpore().inherit(this);
    }
}
