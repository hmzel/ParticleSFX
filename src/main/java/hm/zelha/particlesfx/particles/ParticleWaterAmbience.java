package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleType;

/**
 * NOTE: only visible underwater
 */
public class ParticleWaterAmbience extends Particle {
    /**@see ParticleWaterAmbience*/
    public ParticleWaterAmbience(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("underwater")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    /**@see ParticleWaterAmbience*/
    public ParticleWaterAmbience(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleWaterAmbience*/
    public ParticleWaterAmbience(int count) {
        this(0, 0, 0, count);
    }

    /**@see ParticleWaterAmbience*/
    public ParticleWaterAmbience() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleWaterAmbience inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWaterAmbience clone() {
        return new ParticleWaterAmbience().inherit(this);
    }
}
