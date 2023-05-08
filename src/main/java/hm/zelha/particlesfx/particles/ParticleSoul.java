package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R3.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSoul extends TravellingParticle {
    public ParticleSoul(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL, false, 0.06, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoul(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL, false, 0.06, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoul(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoul(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoul(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoul(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoul(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSoul(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSoul(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSoul() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSoul inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSoul clone() {
        return new ParticleSoul().inherit(this);
    }
}
