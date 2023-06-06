package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleWaxOn extends TravellingParticle {
    public ParticleWaxOn(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("wax_on", false, 5.25, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOn(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("wax_on", false, 5.25, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOn(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOn(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOn(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOn(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOn(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleWaxOn(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleWaxOn(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleWaxOn() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleWaxOn inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWaxOn clone() {
        return new ParticleWaxOn().inherit(this);
    }
}