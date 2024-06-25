package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleDustPlume extends TravellingParticle {
    public ParticleDustPlume(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("dust_plume", false, 0.235, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustPlume(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("dust_plume", false, 0.235, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustPlume(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustPlume(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustPlume(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustPlume(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustPlume(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleDustPlume(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleDustPlume(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleDustPlume() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleDustPlume inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDustPlume clone() {
        return new ParticleDustPlume().inherit(this);
    }
}
