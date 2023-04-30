package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R1.MinecraftKey;
import net.minecraft.server.v1_13_R1.ParticleType;

public class ParticleCurrentDown extends Particle {
    public ParticleCurrentDown(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) REGISTRY.get(new MinecraftKey("current_down")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleCurrentDown(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCurrentDown(int count) {
        this(0, 0, 0, count);
    }

    public ParticleCurrentDown() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleCurrentDown inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCurrentDown clone() {
        return new ParticleCurrentDown().inherit(this);
    }
}
