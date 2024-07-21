package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_11_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleExplosionNormal extends TravellingParticle {
    public ParticleExplosionNormal(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_NORMAL, false, 0.085, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionNormal(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_NORMAL, false, 0.085, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionNormal(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionNormal(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionNormal(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionNormal(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionNormal(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleExplosionNormal(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleExplosionNormal(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleExplosionNormal() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleExplosionNormal inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleExplosionNormal clone() {
        return new ParticleExplosionNormal().inherit(this);
    }
}