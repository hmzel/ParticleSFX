package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R1.MinecraftKey;
import net.minecraft.server.v1_13_R1.ParticleType;

public class ParticleMagic extends Particle {
    public ParticleMagic(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) REGISTRY.get(new MinecraftKey("instant_effect")), offsetX, offsetY, offsetZ, 1, count, 0);
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
