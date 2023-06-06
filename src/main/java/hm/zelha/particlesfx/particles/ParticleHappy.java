package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_15_R1.Particles;

public class ParticleHappy extends Particle {
    public ParticleHappy(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.HAPPY_VILLAGER, offsetX, offsetY, offsetZ, 1, count);
    }

    public ParticleHappy(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleHappy(int count) {
        this(0, 0, 0, count);
    }

    public ParticleHappy() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleHappy inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleHappy clone() {
        return new ParticleHappy().inherit(this);
    }
}