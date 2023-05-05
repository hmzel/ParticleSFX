package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_14_R1.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleBubblePop extends TravellingParticle {
    public ParticleBubblePop(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.BUBBLE_POP, false, 0.26, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubblePop(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.BUBBLE_POP, false, 0.26, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubblePop(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubblePop(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubblePop(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubblePop(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubblePop(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleBubblePop(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleBubblePop(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleBubblePop() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleBubblePop inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleBubblePop clone() {
        return new ParticleBubblePop().inherit(this);
    }
}
