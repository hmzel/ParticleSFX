package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleDrippingTear extends Particle {
    public ParticleDrippingTear(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDrippingTear(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingTear(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingTear() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingTear inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingTear clone() {
        return new ParticleDrippingTear().inherit(this);
    }
}