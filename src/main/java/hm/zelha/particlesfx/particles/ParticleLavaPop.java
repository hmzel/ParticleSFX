package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleLavaPop extends Particle {
    public ParticleLavaPop(double offsetX, double offsetY, double offsetZ, int count) {
        super("lava", offsetX, offsetY, offsetZ, count);
    }

    public ParticleLavaPop(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLavaPop(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLavaPop() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLavaPop inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLavaPop clone() {
        return new ParticleLavaPop().inherit(this);
    }
}