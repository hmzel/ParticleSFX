package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSnowDust extends TravellingParticle {
    public ParticleSnowDust(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.SNOW_SHOVEL, 0.085, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.SNOW_SHOVEL, 0.085, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSnowDust(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSnowDust(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSnowDust() {
        this((Location) null, 0, 0, 0, 1);
    }
}
