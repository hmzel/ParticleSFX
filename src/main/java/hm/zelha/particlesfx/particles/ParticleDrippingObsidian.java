package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleDrippingObsidian extends Particle {
    public ParticleDrippingObsidian(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDrippingObsidian(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingObsidian(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingObsidian() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingObsidian inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingObsidian clone() {
        return new ParticleDrippingObsidian().inherit(this);
    }
}
