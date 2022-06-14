package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.VelocityParticle;
import org.bukkit.Effect;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleCrit extends Particle implements VelocityParticle {

    private Vector velocity = null;

    public ParticleCrit(Vector velocity, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.CRIT, offsetX, offsetY, offsetZ, speed, count, 0);

        if (velocity != null) this.velocity = velocity.multiply(0.75);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        this(null, offsetX, offsetY, offsetZ, speed, count);
    }

    public ParticleCrit(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1, 1);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ, double speed) {
        this(null, offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleCrit(double offsetX, double offsetY, double offsetZ) {
        this(null, offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleCrit(Vector velocity, double speed, int count) {
        this(velocity, 0, 0, 0, speed, count);
    }

    public ParticleCrit(double speed) {
        this(null, 0, 0, 0, speed, 1);
    }

    public ParticleCrit(int count) {
        this(null, 0, 0, 0, 0, count);
    }

    public ParticleCrit(Vector velocity) {
        this(velocity, 0, 0, 0, 1, 1);
    }

    public ParticleCrit() {
        this(null, 0, 0, 0, 0, 1);
    }

    @Override
    public void setVelocity(Vector vector) {
        this.velocity = vector.multiply(0.75);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z).multiply(0.75);
    }

    @Override@Nullable
    public Vector getVelocity() {
        return velocity;
    }
}
