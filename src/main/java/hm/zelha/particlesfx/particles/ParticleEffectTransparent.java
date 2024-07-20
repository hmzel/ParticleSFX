package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.server.v1_16_R1.Particles;

import javax.annotation.Nullable;

public class ParticleEffectTransparent extends ColorableParticle {
    public ParticleEffectTransparent(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.AMBIENT_ENTITY_EFFECT, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEffectTransparent(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEffectTransparent(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEffectTransparent(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleEffectTransparent(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleEffectTransparent(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleEffectTransparent(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleEffectTransparent() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    public ParticleEffectTransparent inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEffectTransparent clone() {
        return new ParticleEffectTransparent().inherit(this);
    }
}