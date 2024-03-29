package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFlash extends Particle {
    public ParticleFlash(double offsetX, double offsetY, double offsetZ, int count) {
        super("flash", offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlash(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlash(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFlash() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFlash inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFlash clone() {
        return new ParticleFlash().inherit(this);
    }
}