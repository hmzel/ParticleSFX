package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleLandingObsidian extends Particle {
    public ParticleLandingObsidian(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.LANDING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleLandingObsidian(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingObsidian(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingObsidian() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingObsidian inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingObsidian clone() {
        return new ParticleLandingObsidian().inherit(this);
    }
}
