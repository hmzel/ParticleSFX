package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleScrape extends TravellingParticle {
    public ParticleScrape(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("scrape", false, 5.25, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleScrape(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("scrape", false, 5.25, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleScrape(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleScrape(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleScrape(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleScrape(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleScrape(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleScrape(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleScrape(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleScrape() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleScrape inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleScrape clone() {
        return new ParticleScrape().inherit(this);
    }
}