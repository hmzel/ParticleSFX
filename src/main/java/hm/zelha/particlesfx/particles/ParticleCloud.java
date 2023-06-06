package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_11_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleCloud extends TravellingParticle {
    public ParticleCloud(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CLOUD, false, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCloud(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CLOUD, false, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCloud(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCloud(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCloud(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCloud(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCloud(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleCloud(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleCloud(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleCloud() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleCloud inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCloud clone() {
        return new ParticleCloud().inherit(this);
    }
}