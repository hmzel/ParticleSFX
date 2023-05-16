package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSculkSoul extends TravellingParticle {
    public ParticleSculkSoul(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_soul", false, 0.06, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkSoul(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_soul", false, 0.06, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkSoul(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkSoul(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkSoul(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkSoul(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkSoul(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSculkSoul(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSculkSoul(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSculkSoul() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSculkSoul inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSculkSoul clone() {
        return new ParticleSculkSoul().inherit(this);
    }
}
