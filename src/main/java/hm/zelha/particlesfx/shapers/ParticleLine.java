package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleLine extends ParticleShaper {

    private Location originalStart;
    private Location originalEnd;
    private Location start;
    private Location end;

    public ParticleLine(Particle particle, Location start, Location end, double frequency) {
        super(particle, 0, 0, 0, frequency);

        Validate.notNull(start, "Location cannot be null!");
        Validate.notNull(end, "Location cannot be null!");
        Validate.notNull(start.getWorld(), "Location's world cannot be null!");
        Validate.notNull(end.getWorld(), "Location's world cannot be null!");

        this.originalStart = start;
        this.originalEnd = end;
        this.start = start;
        this.end = end;
    }

    public ParticleLine(Particle particle, Location start, Location end) {
        this(particle, start, end, 50);
    }

    @Override
    public void display() {
        Location particleSpawn = start.clone();
        double distance = start.distance(end);
        Vector addition = end.clone().subtract(start).toVector().normalize().multiply(distance / frequency);

        for (double length = 0; length < distance; length += distance / frequency, particleSpawn.add(addition)) {
            particle.display(particleSpawn);
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);

        for (Location l : new Location[] {originalStart, originalEnd}) {
            Vector v = rot2.apply(l.clone().subtract(around).toVector());

            if (l.equals(originalStart)) {
                start = around.clone().add(v);
            } else {
                end = around.clone().add(v);
            }
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location center = originalStart.clone().add(originalEnd).multiply(0.5);

        rot.add(pitch, yaw, roll);

        for (Location l : new Location[] {originalStart, originalEnd}) {
            Vector v = rot.apply(l.clone().subtract(center).toVector());

            if (l.equals(originalStart)) {
                start = center.clone().add(v);
            } else {
                end = center.add(v);
            }
        }
    }

    @Override
    public void move(double x, double y, double z) {
        for (Location l : new Location[] {start, end}) l.add(x, y, z);
    }

    public void setStart(Location start) {
        Validate.isTrue(start.getWorld() == end.getWorld(), "Locations cannot be in different worlds!");
        Validate.notNull(start.getWorld(), "Location's world cannot be null!");

        this.start = start;
        this.originalStart = start;
        this.originalEnd = end;
        rot.reset();
    }

    public void setEnd(Location end) {
        Validate.isTrue(start.getWorld() == end.getWorld(), "Locations cannot be in different worlds!");
        Validate.notNull(end.getWorld(), "Location's world cannot be null!");

        this.end = end;
        this.originalStart = start;
        this.originalEnd = end;
        rot.reset();
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }
}