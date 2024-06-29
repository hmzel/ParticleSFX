package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleOminous extends TravellingParticle {
    public ParticleOminous(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("ominous_spawning", true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleOminous(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("ominous_spawning", true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleOminous(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleOminous(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleOminous(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleOminous(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleOminous(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleOminous(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleOminous(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleOminous() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleOminous inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleOminous clone() {
        return new ParticleOminous().inherit(this);
    }
}
