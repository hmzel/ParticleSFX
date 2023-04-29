package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R1.MinecraftKey;
import net.minecraft.server.v1_13_R1.ParticleType;

public class ParticleDripWater extends Particle {
    public ParticleDripWater(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) REGISTRY.get(new MinecraftKey("dripping_water")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDripWater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDripWater(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDripWater() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDripWater inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDripWater clone() {
        return new ParticleDripWater().inherit(this);
    }
}
