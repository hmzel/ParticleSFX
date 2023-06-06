package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleSplash extends Particle {
    public ParticleSplash(double offsetX, double offsetY, double offsetZ, int count) {
        super("splash", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleSplash(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSplash(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSplash() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSplash inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSplash clone() {
        return new ParticleSplash().inherit(this);
    }
}