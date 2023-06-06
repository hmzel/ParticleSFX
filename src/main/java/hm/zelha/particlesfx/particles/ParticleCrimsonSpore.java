package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleCrimsonSpore extends Particle {
    public ParticleCrimsonSpore(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CRIMSON_SPORE, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleCrimsonSpore(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCrimsonSpore(int count) {
        this(0, 0, 0, count);
    }

    public ParticleCrimsonSpore() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleCrimsonSpore inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCrimsonSpore clone() {
        return new ParticleCrimsonSpore().inherit(this);
    }
}