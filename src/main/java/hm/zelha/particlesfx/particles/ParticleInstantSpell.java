package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_10_R1.EnumParticle;

public class ParticleInstantSpell extends Particle {
    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL_INSTANT, offsetX, offsetY, offsetZ, count);
    }

    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInstantSpell(int count) {
        this(0, 0, 0, count);
    }

    public ParticleInstantSpell() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleInstantSpell inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleInstantSpell clone() {
        return new ParticleInstantSpell().inherit(this);
    }
}