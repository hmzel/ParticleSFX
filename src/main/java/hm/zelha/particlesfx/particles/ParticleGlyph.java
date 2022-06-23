package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.InverseTravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleGlyph extends InverseTravellingParticle {
    public ParticleGlyph(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.FLYING_GLYPH, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.FLYING_GLYPH, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleGlyph(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleGlyph(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleGlyph() {
        this((Location) null, 0, 0, 0, 1);
    }
}
