package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingNectar extends Particle {
    public ParticleFallingNectar(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_nectar", offsetX, offsetY, offsetZ, count);
    }

    public ParticleFallingNectar(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingNectar(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingNectar() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingNectar inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingNectar clone() {
        return new ParticleFallingNectar().inherit(this);
    }
}