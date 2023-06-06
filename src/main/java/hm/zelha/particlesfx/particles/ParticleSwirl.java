package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleSwirl extends Particle {
    public ParticleSwirl(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.EFFECT, offsetX, offsetY, offsetZ, 1, count);
    }

    public ParticleSwirl(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirl(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSwirl() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleSwirl inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSwirl clone() {
        return new ParticleSwirl().inherit(this);
    }
}