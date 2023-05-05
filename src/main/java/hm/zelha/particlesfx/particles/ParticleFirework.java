package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.MinecraftKey;
import net.minecraft.server.v1_14_R1.ParticleType;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleFirework extends TravellingParticle {
    public ParticleFirework(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("firework")), false, 0.103, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFirework(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("firework")), false, 0.103, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFirework(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFirework(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFirework(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleFirework(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleFirework(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleFirework(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleFirework(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleFirework() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleFirework inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleFirework clone() {
        return new ParticleFirework().inherit(this);
    }
}
