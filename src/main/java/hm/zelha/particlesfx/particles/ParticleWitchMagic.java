package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

public class ParticleWitchMagic extends Particle {
    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.WITCH_MAGIC, offsetX, offsetY, offsetZ, speed, count, 0);
    }

    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ, double speed) {
        this(offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ, int count) {
        this(offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleWitchMagic(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleWitchMagic(double speed, int count) {
        this(0, 0, 0, speed, count);
    }

    public ParticleWitchMagic(double speed) {
        this(0, 0, 0, speed, 1);
    }

    public ParticleWitchMagic(int count) {
        this(0, 0, 0, 0, count);
    }

    public ParticleWitchMagic() {
        this(0, 0, 0, 0, 1);
    }
}
