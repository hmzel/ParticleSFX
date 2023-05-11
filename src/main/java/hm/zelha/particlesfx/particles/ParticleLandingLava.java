package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleLandingLava extends Particle {
    public ParticleLandingLava(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("landing_lava")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLandingLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingLava clone() {
        return new ParticleLandingLava().inherit(this);
    }
}
