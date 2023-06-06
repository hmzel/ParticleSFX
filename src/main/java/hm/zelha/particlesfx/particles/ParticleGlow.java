package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleGlow extends Particle {
    public ParticleGlow(double offsetX, double offsetY, double offsetZ, int count) {
        super("glow", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleGlow(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlow(int count) {
        this(0, 0, 0, count);
    }

    public ParticleGlow() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleGlow inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGlow clone() {
        return new ParticleGlow().inherit(this);
    }
}