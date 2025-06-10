package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleWater extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleWater(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_WATER, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWater(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWater(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWater() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWater inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleWater clone() {
        return new ParticleWater().inherit(this);
    }

    @Override
    public ParticleWater setLiquidState(LiquidParticleState state) {
        switch (state) {
            case DRIPPING:
                particle = Particles.DRIPPING_WATER;
                break;
            case FALLING:
                particle = Particles.FALLING_WATER;
                break;
            case LANDING:
                throw new IllegalArgumentException("The \"LANDING\" state doesn't exist for this particle!");
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}