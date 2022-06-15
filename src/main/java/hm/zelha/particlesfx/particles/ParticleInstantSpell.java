package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleInstantSpell extends Particle {
    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.INSTANT_SPELL, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInstantSpell(int count) {
        this(0, 0, 0, count);
    }

    public ParticleInstantSpell() {
        this(0, 0, 0, 1);
    }
}
