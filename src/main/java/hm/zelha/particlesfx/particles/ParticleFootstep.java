package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleFootstep extends Particle {
    public ParticleFootstep(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.FOOTSTEP, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFootstep(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFootstep(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFootstep() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFootstep inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFootstep clone() {
        return new ParticleFootstep().inherit(this);
    }
}
