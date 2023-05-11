package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleWhiteAsh extends Particle {
    public ParticleWhiteAsh(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("white_ash")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleWhiteAsh(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWhiteAsh(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWhiteAsh() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWhiteAsh inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWhiteAsh clone() {
        return new ParticleWhiteAsh().inherit(this);
    }
}
