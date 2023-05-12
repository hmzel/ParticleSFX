package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleLandingTear extends Particle {
    public ParticleLandingTear(double offsetX, double offsetY, double offsetZ, int count) {
        super("landing_obsidian_tear", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLandingTear(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingTear(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingTear() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingTear inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingTear clone() {
        return new ParticleLandingTear().inherit(this);
    }
}