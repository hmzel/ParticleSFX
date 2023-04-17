package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_10_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSnowDust extends TravellingParticle {
    public ParticleSnowDust(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SNOW_SHOVEL, false, 0.085, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.SNOW_SHOVEL, false, 0.085, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSnowDust(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSnowDust(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSnowDust(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSnowDust(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSnowDust() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSnowDust inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSnowDust clone() {
        return new ParticleSnowDust().inherit(this);
    }
}
