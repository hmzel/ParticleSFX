package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSculkChargePop extends TravellingParticle {
    public ParticleSculkChargePop(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_charge_pop", false, 0.13, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkChargePop(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_charge_pop", false, 0.13, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkChargePop(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkChargePop(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkChargePop(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkChargePop(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkChargePop(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSculkChargePop(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSculkChargePop(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSculkChargePop() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSculkChargePop inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSculkChargePop clone() {
        return new ParticleSculkChargePop().inherit(this);
    }
}
