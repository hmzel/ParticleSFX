package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class ParticleExplosionLarge extends SizeableParticle {
    public ParticleExplosionLarge(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_LARGE, size, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleExplosionLarge(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionLarge(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionLarge(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionLarge(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleExplosionLarge(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleExplosionLarge(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleExplosionLarge() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionLarge inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleExplosionLarge clone() {
        return new ParticleExplosionLarge().inherit(this);
    }
}
