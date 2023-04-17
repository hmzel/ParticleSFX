package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_10_R1.EnumParticle;

public class ParticleExplosionHuge extends Particle {
    public ParticleExplosionHuge(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_HUGE, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleExplosionHuge(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionHuge(int count) {
        this(0, 0, 0, count);
    }

    public ParticleExplosionHuge() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionHuge inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleExplosionHuge clone() {
        return new ParticleExplosionHuge().inherit(this);
    }
}
