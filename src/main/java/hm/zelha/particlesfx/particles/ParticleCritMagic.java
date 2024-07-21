package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_11_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleCritMagic extends TravellingParticle {
    public ParticleCritMagic(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CRIT_MAGIC, false, 0.83, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCritMagic(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.CRIT_MAGIC, false, 0.83, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCritMagic(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCritMagic(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCritMagic(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleCritMagic(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleCritMagic(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleCritMagic(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleCritMagic(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleCritMagic() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleCritMagic inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleCritMagic clone() {
        return new ParticleCritMagic().inherit(this);
    }
}