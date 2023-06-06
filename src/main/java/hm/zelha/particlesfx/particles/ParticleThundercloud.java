package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R2.Particles;

public class ParticleThundercloud extends Particle {
    public ParticleThundercloud(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ANGRY_VILLAGER, offsetX, offsetY, offsetZ, count);
    }

    public ParticleThundercloud(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleThundercloud(int count) {
        this(0, 0, 0, count);
    }

    public ParticleThundercloud() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleThundercloud inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleThundercloud clone() {
        return new ParticleThundercloud().inherit(this);
    }
}