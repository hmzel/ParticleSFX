package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleNote extends Particle {
    public ParticleNote(double offsetX, double offsetY, double offsetZ, int count) {
        super("note", offsetX, offsetY, offsetZ, count);
    }

    public ParticleNote(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNote(int count) {
        this(0, 0, 0, count);
    }

    public ParticleNote() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleNote inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleNote clone() {
        return new ParticleNote().inherit(this);
    }
}