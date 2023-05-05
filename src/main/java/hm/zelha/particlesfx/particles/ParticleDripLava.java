package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_14_R1.Particles;

public class ParticleDripLava extends Particle {
    public ParticleDripLava(double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRIPPING_LAVA, offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleDripLava(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDripLava(int count) {
        this(0, 0, 0, count);
    }

    public ParticleDripLava() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleDripLava inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDripLava clone() {
        return new ParticleDripLava().inherit(this);
    }
}
