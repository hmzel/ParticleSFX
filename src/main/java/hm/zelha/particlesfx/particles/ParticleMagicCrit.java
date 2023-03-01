package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleMagicCrit extends TravellingParticle {
    public ParticleMagicCrit(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CRIT_MAGIC, false, 0.75, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMagicCrit(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CRIT_MAGIC, false, 0.75, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMagicCrit(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMagicCrit(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMagicCrit(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMagicCrit(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMagicCrit(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleMagicCrit(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleMagicCrit(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleMagicCrit() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleMagicCrit inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }
}
