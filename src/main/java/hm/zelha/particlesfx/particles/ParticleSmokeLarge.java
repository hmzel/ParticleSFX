package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSmokeLarge extends TravellingParticle {
    public ParticleSmokeLarge(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.LARGE_SMOKE, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeLarge(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.LARGE_SMOKE, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeLarge(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeLarge(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeLarge(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSmokeLarge(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSmokeLarge(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSmokeLarge() {
        this((Location) null, 0, 0, 0, 1);
    }
}
