package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleWitchMagic extends Particle {
    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ, int count) {
        super("witch", offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWitchMagic(int count) {
        this(0, 0, 0, count);
    }

    public ParticleWitchMagic() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWitchMagic inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWitchMagic clone() {
        return new ParticleWitchMagic().inherit(this);
    }
}
