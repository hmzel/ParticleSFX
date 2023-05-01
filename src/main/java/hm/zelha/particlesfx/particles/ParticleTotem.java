package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleType;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTotem extends TravellingParticle {
    public ParticleTotem(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("totem_of_undying")), false, 0.44, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTotem(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("totem_of_undying")), false, 0.44, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTotem(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTotem(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTotem(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTotem(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTotem(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleTotem(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleTotem(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleTotem() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleTotem inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleTotem clone() {
        return new ParticleTotem().inherit(this);
    }
}
