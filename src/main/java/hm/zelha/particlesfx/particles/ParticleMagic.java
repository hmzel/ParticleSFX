package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleMagic extends Particle {
    public ParticleMagic(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.INSTANT_EFFECT, offsetX, offsetY, offsetZ, 1, count);
    }

    public ParticleMagic(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMagic(int count) {
        this(0, 0, 0, count);
    }

    public ParticleMagic() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleMagic inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleMagic clone() {
        return new ParticleMagic().inherit(this);
    }
}