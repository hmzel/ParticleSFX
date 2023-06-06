package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleSonicBoom extends Particle {
    public ParticleSonicBoom(double offsetX, double offsetY, double offsetZ, int count) {
        super("sonic_boom", offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleSonicBoom(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSonicBoom(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSonicBoom() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSonicBoom inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSonicBoom clone() {
        return new ParticleSonicBoom().inherit(this);
    }
}