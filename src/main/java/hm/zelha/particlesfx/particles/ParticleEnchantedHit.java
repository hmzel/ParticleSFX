package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleEnchantedHit extends TravellingParticle {
    public ParticleEnchantedHit(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ENCHANTED_HIT, false, 0.83, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchantedHit(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.ENCHANTED_HIT, false, 0.83, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchantedHit(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchantedHit(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchantedHit(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEnchantedHit(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEnchantedHit(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleEnchantedHit(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleEnchantedHit(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleEnchantedHit() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleEnchantedHit inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleEnchantedHit clone() {
        return new ParticleEnchantedHit().inherit(this);
    }
}