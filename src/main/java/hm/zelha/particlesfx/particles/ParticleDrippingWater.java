package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R2.EnumParticle;

public class ParticleDrippingWater extends Particle {
    public ParticleDrippingWater(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.DRIP_WATER, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDrippingWater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingWater(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingWater() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingWater inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingWater clone() {
        return new ParticleDrippingWater().inherit(this);
    }
}
