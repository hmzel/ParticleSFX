package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

/**
 * NOTE: only visible underwater
 */
public class ParticleUnderwater extends Particle {
    /**@see ParticleUnderwater */
    public ParticleUnderwater(double offsetX, double offsetY, double offsetZ, int count) {
        super("underwater", offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleUnderwater */
    public ParticleUnderwater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleUnderwater */
    public ParticleUnderwater(int count) {
        this(0, 0, 0, count);
    }

    /**@see ParticleUnderwater */
    public ParticleUnderwater() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleUnderwater inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleUnderwater clone() {
        return new ParticleUnderwater().inherit(this);
    }
}