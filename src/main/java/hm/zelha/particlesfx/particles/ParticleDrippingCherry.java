package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleDrippingCherry extends Particle {
    public ParticleDrippingCherry(double offsetX, double offsetY, double offsetZ, int count) {
        super("dripping_cherry_leaves", offsetX, offsetY, offsetZ, count);
    }

    public ParticleDrippingCherry(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingCherry(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingCherry() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingCherry inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingCherry clone() {
        return new ParticleDrippingCherry().inherit(this);
    }
}