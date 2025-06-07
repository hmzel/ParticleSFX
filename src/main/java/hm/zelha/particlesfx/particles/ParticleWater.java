package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;

import java.util.Locale;

public class ParticleWater extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.DRIPPING;

    public ParticleWater(double offsetX, double offsetY, double offsetZ, int count) {
        super("dripping_water", offsetX, offsetY, offsetZ, count);
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
            ((LiquidParticle) particle).setLiquidState(getLiquidState());
        }

        return this;
    }

    @Override
    public ParticleWater clone() {
        return new ParticleWater().inherit(this);
    }

    @Override
    public ParticleWater setLiquidState(LiquidParticleState state) {
        if (state == LiquidParticleState.LANDING) throw new IllegalArgumentException("The \"LANDING\" state doesn't exist for this particle!");

        particle = (ParticleType) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", state.name().toLowerCase(Locale.ROOT) + "_water"));
        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}