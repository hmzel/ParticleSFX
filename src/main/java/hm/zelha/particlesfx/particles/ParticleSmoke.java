package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSmoke extends TravellingParticle {
    public ParticleSmoke(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SMOKE, false, 0.075, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmoke(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SMOKE, false, 0.075, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmoke(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmoke(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmoke(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmoke(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmoke(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSmoke(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSmoke(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSmoke() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSmoke inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSmoke clone() {
        return new ParticleSmoke().inherit(this);
    }
}