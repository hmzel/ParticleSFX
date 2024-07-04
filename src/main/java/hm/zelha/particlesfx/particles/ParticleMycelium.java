package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleMycelium extends Particle {
    public ParticleMycelium(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.MYCELIUM, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMycelium(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMycelium(int count) {
        this(0, 0, 0, count);
    }

    public ParticleMycelium() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleMycelium inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleMycelium clone() {
        return new ParticleMycelium().inherit(this);
    }
}