package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleNautilus extends TravellingParticle {
    public ParticleNautilus(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.NAUTILUS, true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleNautilus(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.NAUTILUS, true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleNautilus(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNautilus(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNautilus(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleNautilus(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNautilus(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleNautilus(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleNautilus(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleNautilus() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleNautilus inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleNautilus clone() {
        return new ParticleNautilus().inherit(this);
    }
}
