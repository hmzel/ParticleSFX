package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class ParticleWitch extends Particle {
    public ParticleWitch(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL_WITCH, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWitch(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWitch(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWitch() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWitch inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWitch clone() {
        return new ParticleWitch().inherit(this);
    }
}