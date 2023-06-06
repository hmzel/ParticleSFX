package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import net.minecraft.server.v1_12_R1.EnumParticle;

public class ParticleExplosion extends SizeableParticle {
    public ParticleExplosion(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_LARGE, size, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleExplosion(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosion(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosion(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosion(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleExplosion(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleExplosion(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleExplosion() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    public ParticleExplosion inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof SizeableParticle) {
            setSize(((SizeableParticle) particle).getSize());
        }

        return this;
    }

    @Override
    public ParticleExplosion clone() {
        return new ParticleExplosion().inherit(this);
    }
}