package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleCherryLeaves extends Particle {
    public ParticleCherryLeaves(double offsetX, double offsetY, double offsetZ, int count) {
        super("cherry_leaves", offsetX, offsetY, offsetZ, count);
    }

    public ParticleCherryLeaves(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCherryLeaves(int count) {
        this(0, 0, 0, count);
    }

    public ParticleCherryLeaves() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleCherryLeaves inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCherryLeaves clone() {
        return new ParticleCherryLeaves().inherit(this);
    }
}