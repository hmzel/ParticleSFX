package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R1.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleCampfireSignal extends TravellingParticle {
    public ParticleCampfireSignal(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CAMPFIRE_SIGNAL_SMOKE, false, 0.003, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireSignal(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CAMPFIRE_SIGNAL_SMOKE, false, 0.003, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireSignal(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireSignal(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireSignal(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireSignal(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireSignal(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleCampfireSignal(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleCampfireSignal(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleCampfireSignal() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleCampfireSignal inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCampfireSignal clone() {
        return new ParticleCampfireSignal().inherit(this);
    }
}