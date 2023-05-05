package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.MinecraftKey;
import net.minecraft.server.v1_14_R1.ParticleType;

public class ParticleSwirl extends Particle {
    public ParticleSwirl(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("effect")), offsetX, offsetY, offsetZ, 1, count, 0);
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
