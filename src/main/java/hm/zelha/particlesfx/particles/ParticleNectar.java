package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleNectar extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.FALLING;

    public ParticleNectar(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.FALLING_NECTAR, offsetX, offsetY, offsetZ, count);
    }

    public ParticleNectar(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNectar(int count) {
        this(0, 0, 0, count);
    }

    public ParticleNectar() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleNectar inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleNectar clone() {
        return new ParticleNectar().inherit(this);
    }

    @Override
    public ParticleNectar setLiquidState(LiquidParticleState state) {
        switch (state) {
            case DRIPPING:
            case LANDING:
                throw new IllegalArgumentException("The \"" + state.name() + "\" state doesn't exist for this particle!");
            case FALLING:
                particle = Particles.FALLING_NECTAR;
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}