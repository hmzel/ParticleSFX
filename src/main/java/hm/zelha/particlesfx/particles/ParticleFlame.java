package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleFlame extends TravellingParticle {
    public ParticleFlame(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.FLAME, false, 0.07, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlame(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.FLAME, false, 0.07, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlame(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlame(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlame(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFlame(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFlame(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleFlame(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleFlame(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleFlame() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleFlame inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }
}
