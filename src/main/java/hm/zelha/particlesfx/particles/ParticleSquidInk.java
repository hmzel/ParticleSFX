package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R3.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSquidInk extends TravellingParticle {
    public ParticleSquidInk(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SQUID_INK, false, 0.099, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSquidInk(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SQUID_INK, false, 0.099, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSquidInk(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSquidInk(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSquidInk(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSquidInk(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSquidInk(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSquidInk(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSquidInk(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSquidInk() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSquidInk inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSquidInk clone() {
        return new ParticleSquidInk().inherit(this);
    }
}