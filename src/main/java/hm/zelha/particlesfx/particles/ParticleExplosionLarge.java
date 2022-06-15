package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import org.bukkit.Effect;

public class ParticleExplosionLarge extends Particle implements SizeableParticle {

    private double size;

    public ParticleExplosionLarge(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.EXPLOSION_LARGE, offsetX, offsetY, offsetZ, 0, count, 0);

        this.size = size;
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
    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public double getSize() {
        return size;
    }
}
