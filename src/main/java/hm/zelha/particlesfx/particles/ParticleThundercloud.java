package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleThundercloud extends Particle {
    public ParticleThundercloud(double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.VILLAGER_ANGRY, offsetX, offsetY, offsetZ, 1, count, 0);
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
}
