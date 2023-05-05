package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R1.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSoulFlame extends TravellingParticle {
    public ParticleSoulFlame(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL_FIRE_FLAME, false, 0.07, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulFlame(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SOUL_FIRE_FLAME, false, 0.07, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulFlame(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulFlame(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulFlame(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulFlame(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulFlame(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSoulFlame(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSoulFlame(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSoulFlame() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSoulFlame inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSoulFlame clone() {
        return new ParticleSoulFlame().inherit(this);
    }
}
