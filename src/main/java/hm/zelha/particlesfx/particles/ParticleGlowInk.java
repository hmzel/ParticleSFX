package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleGlowInk extends TravellingParticle {
    public ParticleGlowInk(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("glow_squid_ink", false, 0.099, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlowInk(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("glow_squid_ink", false, 0.099, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlowInk(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlowInk(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlowInk(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlowInk(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlowInk(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleGlowInk(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleGlowInk(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleGlowInk() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleGlowInk inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleGlowInk clone() {
        return new ParticleGlowInk().inherit(this);
    }
}
