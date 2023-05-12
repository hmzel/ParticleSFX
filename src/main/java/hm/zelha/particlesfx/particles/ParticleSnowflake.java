package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSnowflake extends TravellingParticle {
    public ParticleSnowflake(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("snowflake", false, 0.12, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowflake(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("snowflake", false, 0.12, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowflake(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowflake(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowflake(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowflake(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowflake(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSnowflake(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSnowflake(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSnowflake() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSnowflake inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSnowflake clone() {
        return new ParticleSnowflake().inherit(this);
    }
}
