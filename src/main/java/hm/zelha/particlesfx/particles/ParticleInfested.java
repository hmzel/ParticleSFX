package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleInfested extends Particle {
    public ParticleInfested(double offsetX, double offsetY, double offsetZ, int count) {
        super("infested", offsetX, offsetY, offsetZ, count);
    }

    public ParticleInfested(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInfested(int count) {
        this(0, 0, 0, count);
    }

    public ParticleInfested() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleInfested inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleInfested clone() {
        return new ParticleInfested().inherit(this);
    }
}
