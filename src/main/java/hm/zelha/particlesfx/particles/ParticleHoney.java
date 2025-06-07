package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;

import java.util.Locale;

public class ParticleHoney extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super("dripping_honey", offsetX, offsetY, offsetZ, count);
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
            ((LiquidParticle) particle).setLiquidState(getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleHoney clone() {
        return new ParticleHoney().inherit(this);
    }

    @Override
    public ParticleHoney setLiquidState(LiquidParticleState state) {
        particle = (ParticleType) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", state.name().toLowerCase(Locale.ROOT) + "_honey"));
        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}