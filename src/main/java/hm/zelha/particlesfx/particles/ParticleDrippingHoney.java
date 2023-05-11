package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleDrippingHoney extends Particle {
    public ParticleDrippingHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("dripping_honey")), offsetX, offsetY, offsetZ, 0, count, 0);
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
