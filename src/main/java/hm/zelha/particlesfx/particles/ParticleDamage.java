package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_9_R2.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleDamage extends TravellingParticle {
    public ParticleDamage(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.DAMAGE_INDICATOR, false, 0.69, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDamage(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.DAMAGE_INDICATOR, false, 0.69, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDamage(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDamage(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDamage(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDamage(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDamage(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleDamage(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleDamage(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleDamage() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleDamage inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDamage clone() {
        return new ParticleDamage().inherit(this);
    }
}
