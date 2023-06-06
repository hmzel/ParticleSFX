package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleDrippingLava extends Particle {
    public ParticleDrippingLava(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_LAVA, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDrippingLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingLava clone() {
        return new ParticleDrippingLava().inherit(this);
    }
}