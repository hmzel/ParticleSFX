package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_11_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleEndRod extends TravellingParticle {
    public ParticleEndRod(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.END_ROD, false, 0.093, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEndRod(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.END_ROD, false, 0.093, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEndRod(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEndRod(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEndRod(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEndRod(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEndRod(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleEndRod(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleEndRod(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleEndRod() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleEndRod inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEndRod clone() {
        return new ParticleEndRod().inherit(this);
    }
}