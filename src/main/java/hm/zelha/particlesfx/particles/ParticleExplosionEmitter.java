package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleType;

public class ParticleExplosionEmitter extends Particle {
    public ParticleExplosionEmitter(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("explosion_emitter")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleExplosionEmitter(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionEmitter(int count) {
        this(0, 0, 0, count);
    }

    public ParticleExplosionEmitter() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionEmitter inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleExplosionEmitter clone() {
        return new ParticleExplosionEmitter().inherit(this);
    }
}
