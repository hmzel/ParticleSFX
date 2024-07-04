package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleAngry extends Particle {
    public ParticleAngry(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.VILLAGER_ANGRY, offsetX, offsetY, offsetZ, count);
    }

    public ParticleAngry(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleAngry(int count) {
        this(0, 0, 0, count);
    }

    public ParticleAngry() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleAngry inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleAngry clone() {
        return new ParticleAngry().inherit(this);
    }
}