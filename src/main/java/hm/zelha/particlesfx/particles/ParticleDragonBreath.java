package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleDragonBreath extends TravellingParticle {
    public ParticleDragonBreath(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRAGON_BREATH, false, 0.025, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDragonBreath(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.DRAGON_BREATH, false, 0.025, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDragonBreath(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDragonBreath(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDragonBreath(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDragonBreath(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDragonBreath(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleDragonBreath(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleDragonBreath(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleDragonBreath() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleDragonBreath inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleDragonBreath clone() {
        return new ParticleDragonBreath().inherit(this);
    }
}
