package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

/**
 * this particle almost instantly disappears when displayed out of water, which can be displeasing to the eyes
 */
public class ParticleCurrentDown extends Particle {
    /** @see ParticleCurrentDown */
    public ParticleCurrentDown(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CURRENT_DOWN, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    /** @see ParticleCurrentDown */
    public ParticleCurrentDown(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    /** @see ParticleCurrentDown */
    public ParticleCurrentDown(int count) {
        this(0, 0, 0, count);
    }

    /** @see ParticleCurrentDown */
    public ParticleCurrentDown() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleCurrentDown inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCurrentDown clone() {
        return new ParticleCurrentDown().inherit(this);
    }
}
