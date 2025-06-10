package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.server.v1_15_R1.Particles;

public class ParticleHoney extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_HONEY, offsetX, offsetY, offsetZ, count);
    }

    public ParticleHoney(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleHoney(int count) {
        this(0, 0, 0, count);
    }

    public ParticleHoney() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleHoney inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleHoney clone() {
        return new ParticleHoney().inherit(this);
    }

    @Override
    public ParticleHoney setLiquidState(LiquidParticleState state) {
        switch (state) {
            case DRIPPING:
                particle = Particles.DRIPPING_HONEY;
                break;
            case FALLING:
                particle = Particles.FALLING_HONEY;
                break;
            case LANDING:
                particle = Particles.LANDING_HONEY;
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}