package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleFallingObsidian extends Particle {
    public ParticleFallingObsidian(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.FALLING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleFallingObsidian(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingObsidian(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingObsidian() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingObsidian inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingObsidian clone() {
        return new ParticleFallingObsidian().inherit(this);
    }
}