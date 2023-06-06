package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_9_R2.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticlePortal extends TravellingParticle {
    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.PORTAL, true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePortal(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.PORTAL, true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticlePortal(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticlePortal(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticlePortal() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticlePortal inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticlePortal clone() {
        return new ParticlePortal().inherit(this);
    }
}