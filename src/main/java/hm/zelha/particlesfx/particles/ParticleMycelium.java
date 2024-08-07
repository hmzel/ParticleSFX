package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleMycelium extends Particle {
    public ParticleMycelium(double offsetX, double offsetY, double offsetZ, int count) {
        super("mycelium", offsetX, offsetY, offsetZ, count);
    }

    public ParticleMycelium(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMycelium(int count) {
        this(0, 0, 0, count);
    }

    public ParticleMycelium() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleMycelium inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleMycelium clone() {
        return new ParticleMycelium().inherit(this);
    }
}