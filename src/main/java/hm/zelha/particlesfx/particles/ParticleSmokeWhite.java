package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSmokeWhite extends TravellingParticle {
    public ParticleSmokeWhite(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("white_smoke", false, 0.075, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeWhite(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("white_smoke", false, 0.075, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeWhite(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeWhite(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeWhite(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSmokeWhite(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeWhite(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSmokeWhite(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSmokeWhite(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSmokeWhite() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSmokeWhite inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSmokeWhite clone() {
        return new ParticleSmokeWhite().inherit(this);
    }
}
