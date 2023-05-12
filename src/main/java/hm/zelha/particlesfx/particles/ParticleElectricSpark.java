package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleElectricSpark extends TravellingParticle {
    public ParticleElectricSpark(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("electric_spark", false, 1.4, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleElectricSpark(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("electric_spark", false, 1.4, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleElectricSpark(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleElectricSpark(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleElectricSpark(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleElectricSpark(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleElectricSpark(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleElectricSpark(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleElectricSpark(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleElectricSpark() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleElectricSpark inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleElectricSpark clone() {
        return new ParticleElectricSpark().inherit(this);
    }
}
