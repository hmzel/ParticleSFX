package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSculkCharge extends TravellingParticle {
    public ParticleSculkCharge(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0.08, null, toGo, offsetX, offsetY, offsetZ, count);

        setRoll(0);
    }

    public ParticleSculkCharge(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0.08, velocity, null, offsetX, offsetY, offsetZ, count);

        setRoll(0);
    }

    public ParticleSculkCharge(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkCharge(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkCharge(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSculkCharge(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSculkCharge(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleSculkCharge(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleSculkCharge(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleSculkCharge() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleSculkCharge inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleSculkCharge) {
            this.particle = ((ParticleSculkCharge) particle).particle;
        }

        return this;
    }

    @Override
    public ParticleSculkCharge clone() {
        return new ParticleSculkCharge().inherit(this);
    }

    /**
     * @param degrees how much this particle should be rotated in the Z axis
     */
    public ParticleSculkCharge setRoll(double degrees) {
        particle = new SculkChargeParticleOptions((float) Math.toRadians(degrees));

        return this;
    }

    /**
     * @return how much this particle is rotated in the Z axis
     */
    public double getRoll() {
        return Math.toDegrees(((SculkChargeParticleOptions) particle).c());
    }
}