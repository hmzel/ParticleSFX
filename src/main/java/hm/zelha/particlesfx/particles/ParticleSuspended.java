package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_10_R1.EnumParticle;

/**
 * NOTE: only visible underwater
 */
public class ParticleSuspended extends Particle {
    /**@see ParticleSuspended */
    public ParticleSuspended(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SUSPENDED, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleSuspended */
    public ParticleSuspended(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleSuspended */
    public ParticleSuspended(int count) {
        this(0, 0, 0, count);
    }

    /**@see ParticleSuspended */
    public ParticleSuspended() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSuspended inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSuspended clone() {
        return new ParticleSuspended().inherit(this);
    }
}