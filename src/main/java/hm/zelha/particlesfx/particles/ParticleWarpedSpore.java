package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleWarpedSpore extends Particle {
    public ParticleWarpedSpore(double offsetX, double offsetY, double offsetZ, int count) {
        super("warped_spore", offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleWarpedSpore(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWarpedSpore(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWarpedSpore() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWarpedSpore inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWarpedSpore clone() {
        return new ParticleWarpedSpore().inherit(this);
    }
}