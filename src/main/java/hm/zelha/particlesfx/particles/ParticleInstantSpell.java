package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleInstantSpell extends Particle {
    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.INSTANT_SPELL, offsetX, offsetY, offsetZ, speed, count, 0);
    }

    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ, double speed) {
        this(offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ, int count) {
        this(offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleInstantSpell(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleInstantSpell(double speed, int count) {
        this(0, 0, 0, speed, count);
    }

    public ParticleInstantSpell(double speed) {
        this(0, 0, 0, speed, 1);
    }

    public ParticleInstantSpell(int count) {
        this(0, 0, 0, 0, count);
    }

    public ParticleInstantSpell() {
        this(0, 0, 0, 0, 1);
    }
}
