package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticlePoof extends TravellingParticle {
    public ParticlePoof(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("poof", false, 0.085, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePoof(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("poof", false, 0.085, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePoof(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePoof(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePoof(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePoof(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePoof(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticlePoof(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticlePoof(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticlePoof() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticlePoof inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticlePoof clone() {
        return new ParticlePoof().inherit(this);
    }
}