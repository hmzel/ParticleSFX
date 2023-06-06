package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleFallingNectar extends Particle {
    public ParticleFallingNectar(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.FALLING_NECTAR, offsetX, offsetY, offsetZ, 0, count, 0);
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