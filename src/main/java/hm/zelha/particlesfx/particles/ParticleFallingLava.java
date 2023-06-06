package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingLava extends Particle {
    public ParticleFallingLava(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_lava", offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleFallingLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingLava clone() {
        return new ParticleFallingLava().inherit(this);
    }
}