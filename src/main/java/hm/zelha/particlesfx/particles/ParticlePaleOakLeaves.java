package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticlePaleOakLeaves extends Particle {
    public ParticlePaleOakLeaves(double offsetX, double offsetY, double offsetZ, int count) {
        super("pale_oak_leaves", offsetX, offsetY, offsetZ, count);
    }

    public ParticlePaleOakLeaves(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePaleOakLeaves(int count) {
        this(0, 0, 0, count);
    }

    public ParticlePaleOakLeaves() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticlePaleOakLeaves inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticlePaleOakLeaves clone() {
        return new ParticlePaleOakLeaves().inherit(this);
    }
}
