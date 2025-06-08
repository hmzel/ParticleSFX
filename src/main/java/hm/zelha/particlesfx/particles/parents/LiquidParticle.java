package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.LiquidParticleState;

public interface LiquidParticle extends IParticle {
    LiquidParticle inherit(Particle particle);

    LiquidParticle clone();

    /**
     * @param state The type of liquid particle this object represents, keep in mind some LiquidParticles don't support all states.
     * @return this object
     */
    LiquidParticle setLiquidState(LiquidParticleState state);

    Particle setSpeed(double speed);

    Particle setRadius(int radius);

    /**
     * @return The type of liquid particle this object represents, keep in mind some LiquidParticles don't support all states.
     */
    LiquidParticleState getLiquidState();
}
