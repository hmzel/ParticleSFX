package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleFlameSoul extends TravellingParticle {
    public ParticleFlameSoul(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL_FIRE_FLAME, false, 0.07, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlameSoul(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL_FIRE_FLAME, false, 0.07, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlameSoul(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlameSoul(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlameSoul(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlameSoul(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlameSoul(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleFlameSoul(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleFlameSoul(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleFlameSoul() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleFlameSoul inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFlameSoul clone() {
        return new ParticleFlameSoul().inherit(this);
    }
}