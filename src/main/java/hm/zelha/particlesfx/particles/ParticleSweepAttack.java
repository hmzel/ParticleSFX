package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import net.minecraft.server.v1_9_R1.EnumParticle;

public class ParticleSweepAttack extends SizeableParticle {
    public ParticleSweepAttack(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SWEEP_ATTACK, size, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleSweepAttack(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSweepAttack(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSweepAttack(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSweepAttack(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleSweepAttack(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleSweepAttack(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleSweepAttack() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    public ParticleSweepAttack inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof SizeableParticle) {
            setSize(((SizeableParticle) particle).getSize());
        }

        return this;
    }

    @Override
    public ParticleSweepAttack clone() {
        return new ParticleSweepAttack().inherit(this);
    }
}