package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.server.v1_11_R1.EnumParticle;

import javax.annotation.Nullable;

public class ParticleSpellTransparent extends ColorableParticle {
    public ParticleSpellTransparent(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SPELL_MOB_AMBIENT, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpellTransparent(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpellTransparent(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpellTransparent(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleSpellTransparent(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleSpellTransparent(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleSpellTransparent(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleSpellTransparent() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    public ParticleSpellTransparent inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSpellTransparent clone() {
        return new ParticleSpellTransparent().inherit(this);
    }
}