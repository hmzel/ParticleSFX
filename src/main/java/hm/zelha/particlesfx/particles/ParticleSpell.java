package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R1.EnumParticle;

public class ParticleSpell extends Particle {
    public ParticleSpell(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpell(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpell(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSpell() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSpell inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSpell clone() {
        return new ParticleSpell().inherit(this);
    }
}