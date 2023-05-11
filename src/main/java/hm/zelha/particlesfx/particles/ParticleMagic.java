package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.MinecraftKey;

public class ParticleMagic extends Particle {
    public ParticleMagic(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.ab.get(new MinecraftKey("instant_effect")), offsetX, offsetY, offsetZ, 1, count, 0);
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
