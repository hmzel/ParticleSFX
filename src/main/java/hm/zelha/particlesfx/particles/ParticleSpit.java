package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R3.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSpit extends TravellingParticle {
    public ParticleSpit(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SPIT, false, 0.14, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpit(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SPIT, false, 0.14, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpit(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpit(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpit(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSpit(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSpit(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSpit(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSpit(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSpit() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSpit inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSpit clone() {
        return new ParticleSpit().inherit(this);
    }
}