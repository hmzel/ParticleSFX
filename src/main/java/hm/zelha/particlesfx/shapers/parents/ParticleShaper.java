package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.Main;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class ParticleShaper {

    protected final RotationHandler rot;
    protected final RotationHandler rot2;
    protected BukkitTask animator = null;
    protected Particle particle;
    protected double frequency;

    protected ParticleShaper(Particle particle, double pitch, double yaw, double roll, double frequency) {
        Validate.notNull(particle, "Particle cannot be null!");
        Validate.isTrue(frequency >= 2.0D, "Frequency cannot be less than 2! if you only want one particle, use Particle.display().");

        this.particle = particle;
        this.rot = new RotationHandler(pitch, yaw, roll);
        this.rot2 = new RotationHandler();
        this.frequency = frequency;

        start();
    }

    public void start() {
        if (animator != null) return;

        animator = new BukkitRunnable() {
            @Override
            public void run() {
                display();
            }
        }.runTaskTimer(Main.getPlugin(), 0, 1);
    }

    public void stop() {
        if (animator == null) return;

        animator.cancel();
        animator = null;
    }

    public abstract void display();

    public abstract void rotateAroundLocation(Location around, double pitch, double yaw, double roll);

    public abstract void rotate(double pitch, double yaw, double roll);

    public abstract void move(double x, double y, double z);

    public boolean isRunning() {
        return animator != null;
    }

    public void setParticle(Particle particle) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;
    }

    /** @param frequency amount of times to display the particle per full animation */
    public void setFrequency(double frequency) {
        Validate.isTrue(frequency > 2.0D, "Frequency cannot be 2 or less! if you only want one particle, use Particle.display()");

        this.frequency = frequency;
    }

    public Particle getParticle() {
        return particle;
    }

    public double getFrequency() {
        return frequency;
    }
}
