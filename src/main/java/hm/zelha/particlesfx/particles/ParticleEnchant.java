package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R1.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleEnchant extends TravellingParticle {
    public ParticleEnchant(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ENCHANT, true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchant(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ENCHANT, true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchant(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchant(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchant(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchant(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchant(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleEnchant(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleEnchant(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleEnchant() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleEnchant inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEnchant clone() {
        return new ParticleEnchant().inherit(this);
    }

    @Override
    protected Vector getXYZ(Location location) {
        Vector vec = super.getXYZ(location);

        return vec.setY(vec.getY() + 1.2);
    }

    @Override
    protected Vector getOffsets(Location location) {
        Vector vec = super.getOffsets(location);

        return vec.setY(vec.getY() - 1.2);
    }
}