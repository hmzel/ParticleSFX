package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleFallingWater extends Particle {
    public ParticleFallingWater(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_water", offsetX, offsetY, offsetZ, count);
    }

    public ParticleFallingWater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingWater(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingWater() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingWater inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingWater clone() {
        return new ParticleFallingWater().inherit(this);
    }
}