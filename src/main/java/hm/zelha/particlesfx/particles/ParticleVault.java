package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleVault extends TravellingParticle {
    public ParticleVault(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("vault_connection", true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVault(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("vault_connection", true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVault(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVault(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVault(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVault(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVault(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleVault(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleVault(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleVault() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleVault inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleVault clone() {
        return new ParticleVault().inherit(this);
    }

    @Override
    protected Vector getXYZ(Location location) {
        Vector vec = super.getXYZ(location);

        return vec.setY(vec.getY() + 1.2);
    }

    @Override
    protected Vector getOffsets(Location location) {
        Vector vec = super.getOffsets(location);

        return vec.setY(vec.getY() - 1.2).multiply(1.5);
    }
}
