package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleType;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleWater extends TravellingParticle {
    public ParticleWater(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("fishing")), false, 0.05, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWater(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("fishing")), false, 0.05, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWater(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWater(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWater(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleWater(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleWater(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleWater(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleWater(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

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
