package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.particles.ShriekParticleOption;

public class ParticleShriek extends Particle {
    public ParticleShriek(double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleShriek(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleShriek(int count) {
        this(0, 0, 0, count);
    }

    public ParticleShriek() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleShriek inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleShriek clone() {
        return new ParticleShriek().inherit(this);
    }

    /**
     * @param delay amount of ticks before this particle displays
     */
    public void setDelay(int delay) {
        particle = new ShriekParticleOption(delay);
    }

    /**
     * @return amount of ticks before this particle displays
     */
    public int getDelay() {
        return ((ShriekParticleOption) particle).c();
    }
}
