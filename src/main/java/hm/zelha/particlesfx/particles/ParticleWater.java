package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_9_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * NOTE: velocity on this particle can't be controlled! <br>
 * both velocity and location to go variables will be completely inaccurate. not my fault <br><br>
 * no matter how low you set it, it'll always go at least 14-21 blocks in the direction its set. blame mojang
 */
public class ParticleWater extends TravellingParticle {
    /**@see ParticleWater*/
    public ParticleWater(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.WATER_WAKE, false, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleWater*/
    public ParticleWater(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.WATER_WAKE, false, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleWater*/
    public ParticleWater(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleWater*/
    public ParticleWater(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleWater*/
    public ParticleWater(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleWater*/
    public ParticleWater(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleWater*/
    public ParticleWater(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    /**@see ParticleWater*/
    public ParticleWater(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    /**@see ParticleWater*/
    public ParticleWater(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    /**@see ParticleWater*/
    public ParticleWater() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleWater inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleWater clone() {
        return new ParticleWater().inherit(this);
    }
}
