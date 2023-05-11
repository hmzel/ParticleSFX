package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleFallingWater extends Particle {
    public ParticleFallingWater(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("falling_water")), offsetX, offsetY, offsetZ, 0, count, 0);
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
