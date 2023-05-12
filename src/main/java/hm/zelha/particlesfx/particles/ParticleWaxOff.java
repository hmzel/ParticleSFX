package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleWaxOff extends TravellingParticle {
    public ParticleWaxOff(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("wax_off", false, 5.25, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOff(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("wax_off", false, 5.25, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOff(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOff(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOff(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWaxOff(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWaxOff(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleWaxOff(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleWaxOff(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleWaxOff() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleWaxOff inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWaxOff clone() {
        return new ParticleWaxOff().inherit(this);
    }
}
