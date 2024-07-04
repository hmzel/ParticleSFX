package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSoulSculk extends TravellingParticle {
    public ParticleSoulSculk(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_soul", false, 0.06, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulSculk(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("sculk_soul", false, 0.06, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulSculk(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulSculk(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulSculk(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSoulSculk(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSoulSculk(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSoulSculk(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSoulSculk(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSoulSculk() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSoulSculk inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSoulSculk clone() {
        return new ParticleSoulSculk().inherit(this);
    }
}