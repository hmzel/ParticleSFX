package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleInk extends TravellingParticle {
    public ParticleInk(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("squid_ink", false, 0.099, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleInk(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("squid_ink", false, 0.099, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleInk(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInk(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInk(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleInk(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleInk(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleInk(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleInk(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleInk() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleInk inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleInk clone() {
        return new ParticleInk().inherit(this);
    }
}