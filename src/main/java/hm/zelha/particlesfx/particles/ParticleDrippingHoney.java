package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_15_R1.Particles;

public class ParticleDrippingHoney extends Particle {
    public ParticleDrippingHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_HONEY, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDrippingHoney(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDrippingHoney(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDrippingHoney() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDrippingHoney inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDrippingHoney clone() {
        return new ParticleDrippingHoney().inherit(this);
    }
}
