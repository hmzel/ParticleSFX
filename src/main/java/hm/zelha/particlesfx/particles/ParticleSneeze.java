package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSneeze extends TravellingParticle {
    public ParticleSneeze(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("sneeze", false, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSneeze(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("sneeze", false, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSneeze(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSneeze(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSneeze(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSneeze(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSneeze(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSneeze(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSneeze(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSneeze() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSneeze inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSneeze clone() {
        return new ParticleSneeze().inherit(this);
    }
}