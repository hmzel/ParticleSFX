package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleSuspendedDepth extends Particle {
    public ParticleSuspendedDepth(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SUSPENDED_DEPTH, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSuspendedDepth(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSuspendedDepth(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSuspendedDepth() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSuspendedDepth inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSuspendedDepth clone() {
        return new ParticleSuspendedDepth().inherit(this);
    }
}