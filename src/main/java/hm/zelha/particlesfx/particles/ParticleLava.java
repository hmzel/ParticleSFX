package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleLava extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleLava(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_LAVA, offsetX, offsetY, offsetZ, count);
    }

    public ParticleLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLava inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleLava clone() {
        return new ParticleLava().inherit(this);
    }

    @Override
    public ParticleLava setLiquidState(LiquidParticleState state) {
        switch (state) {
            case DRIPPING:
                particle = Particles.DRIPPING_LAVA;
                break;
            case FALLING:
                particle = Particles.FALLING_LAVA;
                break;
            case LANDING:
                particle = Particles.LANDING_LAVA;
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}