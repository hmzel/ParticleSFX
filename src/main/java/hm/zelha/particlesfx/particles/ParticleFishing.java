package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleFishing extends TravellingParticle {
    public ParticleFishing(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("fishing", false, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFishing(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("fishing", false, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFishing(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFishing(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFishing(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFishing(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFishing(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleFishing(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleFishing(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleFishing() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleFishing inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFishing clone() {
        return new ParticleFishing().inherit(this);
    }
}