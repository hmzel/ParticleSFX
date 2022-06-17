package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.Main;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class ParticleLine {

    private final RotationHandler rot = new RotationHandler();
    private BukkitTask animator = null;
    private Particle particle;
    private Location originalStart;
    private Location originalEnd;
    private Location start;
    private Location end;
    private double pitch = 0;
    private double yaw = 0;
    private double roll = 0;
    private double frequency;

    public ParticleLine(Particle particle, Location start, Location end, double frequency) {
        Validate.notNull(particle, "Particle cannot be null!");
        Validate.notNull(start, "Location cannot be null!");
        Validate.notNull(end, "Location cannot be null!");
        Validate.notNull(start.getWorld(), "Location's world cannot be null!");
        Validate.notNull(end.getWorld(), "Location's world cannot be null!");
        Validate.isTrue(frequency > 2.0D, "Frequency cannot be 2 or less! if you only want one particle, use Particle.display().");

        this.particle = particle;
        this.originalStart = start;
        this.originalEnd = end;
        this.start = start;
        this.end = end;
        this.frequency = frequency;

        start();
    }

    public ParticleLine(Particle particle, Location start, Location end) {
        this(particle, start, end, 50);
    }

    public void start() {
        if (animator != null) return;

        animator = new BukkitRunnable() {
            @Override
            public void run() {
                Location particleSpawn = start.clone();
                double distance = start.distance(end);
                Vector addition = end.clone().subtract(start).toVector().normalize().multiply(distance / frequency);

                for (double length = 0; length < distance; length += distance / frequency, particleSpawn.add(addition)) {
                    particle.display(particleSpawn);
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 1);
    }

    public void stop() {
        if (animator == null) return;

        animator.cancel();
        animator = null;
    }

    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot.add(pitch, yaw, roll);

        for (Location l : new Location[] {originalStart, originalEnd}) {
            Vector v = l.clone().subtract(around).toVector();

            rot.apply(v);

            if (l.equals(originalStart)) {
                start = around.clone().add(v);
            } else {
                end = around.clone().add(v);
            }
        }
    }

    public void adjustPitch(double pitch) {
        rotateAroundLocation(originalStart.clone().add(originalEnd).multiply(0.5), pitch, 0, 0);
    }

    public void adjustYaw(double yaw) {
        rotateAroundLocation(originalStart.clone().add(originalEnd).multiply(0.5), 0, yaw, 0);
    }

    public void adjustRoll(double roll) {
        rotateAroundLocation(originalStart.clone().add(originalEnd).multiply(0.5), 0, 0, roll);
    }

    public void setParticle(Particle particle) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;
    }

    public void setStart(Location start) {
        Validate.isTrue(start.getWorld() == end.getWorld(), "Locations cannot be in different worlds!");
        Validate.notNull(start.getWorld(), "Location's world cannot be null!");

        this.start = start;
        this.originalStart = start;
        this.originalEnd = end;
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
    }

    public void setEnd(Location end) {
        Validate.isTrue(start.getWorld() == end.getWorld(), "Locations cannot be in different worlds!");
        Validate.notNull(end.getWorld(), "Location's world cannot be null!");

        this.end = end;
        this.originalStart = start;
        this.originalEnd = end;
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
    }

    public void setFrequency(double frequency) {
        Validate.isTrue(frequency > 2.0D, "Frequency cannot be 2 or less! if you only want one particle, use Particle.display().");

        this.frequency = frequency;
    }

    public Particle getParticle() {
        return particle;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public double getFrequency() {
        return frequency;
    }
}