package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleSpell extends Particle {
    public ParticleSpell(double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.INSTANT_SPELL, offsetX, offsetY, offsetZ, 1, count, 0);
    }

    public ParticleSpell(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpell(int count) {
        this(0, 0, 0, count);
    }

    public ParticleSpell() {
        this(0, 0, 0, 1);
    }
}
