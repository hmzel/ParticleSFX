package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R2.EnumParticle;

public class ParticleHeart extends Particle {
    public ParticleHeart(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.HEART, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleHeart(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleHeart(int count) {
        this(0, 0, 0, count);
    }

    public ParticleHeart() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleHeart inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleHeart clone() {
        return new ParticleHeart().inherit(this);
    }
}
