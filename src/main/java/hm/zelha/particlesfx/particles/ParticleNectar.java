package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.LiquidParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LiquidParticleState;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

import java.util.Locale;

public class ParticleNectar extends Particle implements LiquidParticle {

    private LiquidParticleState state = LiquidParticleState.FALLING;

    public ParticleNectar(double offsetX, double offsetY, double offsetZ, int count) {
        super("falling_nectar", offsetX, offsetY, offsetZ, count);
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
        if (state == LiquidParticleState.DRIPPING) throw new IllegalArgumentException("The \"DRIPPING\" state doesn't exist for this particle!");
        if (state == LiquidParticleState.LANDING) throw new IllegalArgumentException("The \"LANDING\" state doesn't exist for this particle!");

        particle = (ParticleType) IRegistry.Z.a(new MinecraftKey(state.name().toLowerCase(Locale.ROOT) + "_nectar"));
        this.state = state;

        return this;
    }

    @Override
    public LiquidParticleState getLiquidState() {
        return state;
    }
}