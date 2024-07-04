package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class ParticleInstantEffect extends Particle {
    public ParticleInstantEffect(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL_INSTANT, offsetX, offsetY, offsetZ, count);
    }

    public ParticleInstantEffect(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInstantEffect(int count) {
        this(0, 0, 0, count);
    }

    public ParticleInstantEffect() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleInstantEffect inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleInstantEffect clone() {
        return new ParticleInstantEffect().inherit(this);
    }
}