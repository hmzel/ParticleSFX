package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R3.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleCrit extends TravellingParticle {
    public ParticleCrit(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CRIT, false, 0.82, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCrit(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CRIT, false, 0.82, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCrit(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCrit(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCrit(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleCrit(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleCrit(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleCrit() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleCrit inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCrit clone() {
        return new ParticleCrit().inherit(this);
    }
}
