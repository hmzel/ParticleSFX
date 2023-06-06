package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleFallingTear extends Particle {
    public ParticleFallingTear(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.FALLING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFallingTear(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingTear(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingTear() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingTear inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingTear clone() {
        return new ParticleFallingTear().inherit(this);
    }
}