package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.VelocityParticle;
import org.bukkit.Effect;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleFireworksSpark extends Particle implements VelocityParticle {

    private Vector velocity = null;

    public ParticleFireworksSpark(Vector velocity, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.FIREWORKS_SPARK, offsetX, offsetY, offsetZ, speed, count, 0);

        if (velocity != null) this.velocity = velocity.multiply(0.05);
    }

    public ParticleFireworksSpark(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        this(null, offsetX, offsetY, offsetZ, speed, count);
    }

    public ParticleFireworksSpark(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1, 1);
    }

    public ParticleFireworksSpark(double offsetX, double offsetY, double offsetZ, double speed) {
        this(null, offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleFireworksSpark(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleFireworksSpark(double offsetX, double offsetY, double offsetZ) {
        this(null, offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleFireworksSpark(Vector velocity, double speed, int count) {
        this(velocity, 0, 0, 0, speed, count);
    }

    public ParticleFireworksSpark(double speed) {
        this(null, 0, 0, 0, speed, 1);
    }

    public ParticleFireworksSpark(int count) {
        this(null, 0, 0, 0, 0, count);
    }

    public ParticleFireworksSpark(Vector velocity) {
        this(velocity, 0, 0, 0, 1, 1);
    }

    public ParticleFireworksSpark() {
        this(null, 0, 0, 0, 0, 1);
    }

    @Override
    public void setVelocity(Vector vector) {
        this.velocity = vector.multiply(0.05);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z).multiply(0.05);
    }

    @Override@Nullable
    public Vector getVelocity() {
        return velocity;
    }
}
