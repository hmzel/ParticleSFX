package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleLandingHoney extends Particle {
    public ParticleLandingHoney(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.LANDING_HONEY, offsetX, offsetY, offsetZ, count);
    }

    public ParticleLandingHoney(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleLandingHoney(int count) {
        this(0, 0, 0, count);
    }

    public ParticleLandingHoney() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleLandingHoney inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleLandingHoney clone() {
        return new ParticleLandingHoney().inherit(this);
    }
}