package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleExplosionDust extends TravellingParticle {
    public ParticleExplosionDust(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_NORMAL, false, 0.085, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionDust(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_NORMAL, false, 0.085, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionDust(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionDust(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionDust(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionDust(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionDust(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleExplosionDust(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleExplosionDust(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleExplosionDust() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionDust inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }
}
