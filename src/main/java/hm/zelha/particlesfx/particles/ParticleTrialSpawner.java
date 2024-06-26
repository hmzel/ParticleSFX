package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTrialSpawner extends TravellingParticle {
    public ParticleTrialSpawner(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("trial_spawner_detection", false, 0.075, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialSpawner(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("trial_spawner_detection", false, 0.075, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialSpawner(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialSpawner(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialSpawner(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialSpawner(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialSpawner(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleTrialSpawner(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleTrialSpawner(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleTrialSpawner() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleTrialSpawner inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleTrialSpawner clone() {
        return new ParticleTrialSpawner().inherit(this);
    }
}
