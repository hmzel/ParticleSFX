package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleCrimsonSpore extends Particle {
    public ParticleCrimsonSpore(double offsetX, double offsetY, double offsetZ, int count) {
        super("crimson_spore", offsetX, offsetY, offsetZ, count);
    }

    public ParticleCrimsonSpore(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCrimsonSpore(int count) {
        this(0, 0, 0, count);
    }

    public ParticleCrimsonSpore() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleCrimsonSpore inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCrimsonSpore clone() {
        return new ParticleCrimsonSpore().inherit(this);
    }
}