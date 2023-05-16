package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingCherry extends Particle {
    public ParticleFallingCherry(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_cherry_leaves", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleFallingCherry(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingCherry(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingCherry() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingCherry inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingCherry clone() {
        return new ParticleFallingCherry().inherit(this);
    }
}
