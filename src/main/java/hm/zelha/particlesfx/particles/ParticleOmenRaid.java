package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;

public class ParticleOmenRaid extends Particle {
    public ParticleOmenRaid(double offsetX, double offsetY, double offsetZ, int count) {
        super("raid_omen", offsetX, offsetY, offsetZ, count);
    }

    public ParticleOmenRaid(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleOmenRaid(int count) {
        this(0, 0, 0, count);
    }

    public ParticleOmenRaid() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleOmenRaid inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleOmenRaid clone() {
        return new ParticleOmenRaid().inherit(this);
    }
}
