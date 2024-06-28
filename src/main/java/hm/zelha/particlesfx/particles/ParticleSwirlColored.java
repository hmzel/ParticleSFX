package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;

import javax.annotation.Nullable;

public class ParticleSwirlColored extends ColorableParticle {
    public ParticleSwirlColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        super("entity_effect", color, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSwirlColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSwirlColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirlColored(double offsetX, double offsetY, double offsetZ) {
        this(null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirlColored(@Nullable Color color) {
        this(color, 0, 0, 0, 1);
    }

    public ParticleSwirlColored(int count) {
        this(null, 0, 0, 0, count);
    }

    public ParticleSwirlColored() {
        this(null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSwirlColored inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSwirlColored clone() {
        return new ParticleSwirlColored().inherit(this);
    }
}