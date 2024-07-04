package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.Particles;

public class ParticleAshWhite extends Particle {
    public ParticleAshWhite(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.WHITE_ASH, offsetX, offsetY, offsetZ, count);
    }

    public ParticleAshWhite(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleAshWhite(int count) {
        this(0, 0, 0, count);
    }

    public ParticleAshWhite() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleAshWhite inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleAshWhite clone() {
        return new ParticleAshWhite().inherit(this);
    }
}