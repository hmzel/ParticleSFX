package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTrialOminous extends TravellingParticle {
    public ParticleTrialOminous(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("trial_spawner_detection_ominous", false, 0.075, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialOminous(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("trial_spawner_detection_ominous", false, 0.075, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialOminous(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialOminous(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialOminous(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTrialOminous(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTrialOminous(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleTrialOminous(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleTrialOminous(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleTrialOminous() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleTrialOminous inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleTrialOminous clone() {
        return new ParticleTrialOminous().inherit(this);
    }
}
