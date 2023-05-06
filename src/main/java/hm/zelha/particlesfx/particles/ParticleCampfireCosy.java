package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleCampfireCosy extends TravellingParticle {
    public ParticleCampfireCosy(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CAMPFIRE_COSY_SMOKE, false, 0.009, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireCosy(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.CAMPFIRE_COSY_SMOKE, false, 0.009, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireCosy(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireCosy(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireCosy(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCampfireCosy(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCampfireCosy(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleCampfireCosy(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleCampfireCosy(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleCampfireCosy() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleCampfireCosy inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCampfireCosy clone() {
        return new ParticleCampfireCosy().inherit(this);
    }
}
