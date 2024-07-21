package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.server.v1_11_R1.EnumParticle;

import javax.annotation.Nullable;

public class ParticleSpellColored extends ColorableParticle {
    public ParticleSpellColored(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL_MOB, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpellColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpellColored(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpellColored(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleSpellColored(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleSpellColored(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleSpellColored(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleSpellColored() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    public ParticleSpellColored inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSpellColored clone() {
        return new ParticleSpellColored().inherit(this);
    }
}