package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.Particles;

public class ParticleFallingLava extends Particle {
    public ParticleFallingLava(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.FALLING_LAVA, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleFallingLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFallingLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleFallingLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleFallingLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFallingLava clone() {
        return new ParticleFallingLava().inherit(this);
    }
}
