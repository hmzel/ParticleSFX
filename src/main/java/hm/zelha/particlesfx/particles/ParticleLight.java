package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleLight extends Particle {
    public ParticleLight(double offsetX, double offsetY, double offsetZ, int count) {
        super("light", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLight(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLight(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLight() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLight inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLight clone() {
        return new ParticleLight().inherit(this);
    }
}
