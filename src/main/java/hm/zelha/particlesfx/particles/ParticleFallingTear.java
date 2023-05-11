package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleFallingTear extends Particle {
    public ParticleFallingTear(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("falling_obsidian_tear")), offsetX, offsetY, offsetZ, 0, count, 0);
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