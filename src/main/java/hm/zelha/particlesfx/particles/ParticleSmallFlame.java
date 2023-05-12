package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSmallFlame extends TravellingParticle {
    public ParticleSmallFlame(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("small_flame", false, 0.07, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmallFlame(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("small_flame", false, 0.07, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmallFlame(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmallFlame(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmallFlame(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmallFlame(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmallFlame(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSmallFlame(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSmallFlame(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSmallFlame() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSmallFlame inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSmallFlame clone() {
        return new ParticleSmallFlame().inherit(this);
    }
}
