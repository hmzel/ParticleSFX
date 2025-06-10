package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleSporeBlossom extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleSporeBlossom(double offsetX, double offsetY, double offsetZ, int count) {
        super("spore_blossom_air", offsetX, offsetY, offsetZ, count);
    }

    public ParticleSporeBlossom(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSporeBlossom(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSporeBlossom() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSporeBlossom inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof LiquidParticle) {
            setLiquidState(((LiquidParticle) particle).getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleSporeBlossom clone() {
        return new ParticleSporeBlossom().inherit(this);
    }

    @Override
    public ParticleSporeBlossom setLiquidState(LiquidParticleState state) {
        if (state == LiquidParticleState.LANDING) throw new IllegalArgumentException("The \"LANDING\" state doesn't exist for this particle!");

        if (state == LiquidParticleState.DRIPPING) {
            particle = (ParticleType) IRegistry.aa.a(new MinecraftKey("spore_blossom_air"));
        } else {
            particle = (ParticleType) IRegistry.aa.a(new MinecraftKey("falling_spore_blossom"));
        }

        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}