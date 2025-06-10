package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleObsidianTear extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleObsidianTear(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_OBSIDIAN_TEAR, offsetX, offsetY, offsetZ, count);
    }

    public ParticleObsidianTear(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleObsidianTear(int count) {
        this(0, 0, 0, count);
    }

    public ParticleObsidianTear() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleObsidianTear inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleObsidianTear clone() {
        return new ParticleObsidianTear().inherit(this);
    }

    @Override
    public ParticleObsidianTear setLiquidState(LiquidParticleState state) {
        switch (state) {
            case DRIPPING:
                particle = Particles.DRIPPING_OBSIDIAN_TEAR;
                break;
            case FALLING:
                particle = Particles.FALLING_OBSIDIAN_TEAR;
                break;
            case LANDING:
                particle = Particles.LANDING_OBSIDIAN_TEAR;
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}