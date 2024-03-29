package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingHoney extends Particle {
    public ParticleFallingHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_honey", offsetX, offsetY, offsetZ, count);
    }

    public ParticleFallingHoney(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingHoney(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingHoney() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingHoney inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingHoney clone() {
        return new ParticleFallingHoney().inherit(this);
    }
}