package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCompound extends ParticleShaper {

    private final List<Location> locations = new ArrayList<>();

    public ParticleLineCompound(Particle particle, double frequency, int particlesPerDisplay, Location... locations) {
        super(particle, 0, 0, 0, frequency, particlesPerDisplay);

        Validate.isTrue(locations.length >= 2, "Compound line must have 2 or more locations!");

        World world = locations[0].getWorld();
        locationHelper.setWorld(world);

        for (Location l : locations) {
            Validate.isTrue(l.getWorld() != null, "Locations cannot have null worlds!");
            Validate.isTrue(l.getWorld().equals(world), "Locations cannot have different worlds!");

            this.locations.add(l);
        }

        rot.addOrigins(locations);
        rot2.addOrigins(locations);
    }

    public ParticleLineCompound(Particle particle, int particlesPerDisplay, Location... locations) {
        this(particle, 50, particlesPerDisplay, locations);
    }

    public ParticleLineCompound(Particle particle, double frequency, Location... locations) {
        this(particle, frequency, 0, locations);
    }

    public ParticleLineCompound(Particle particle, Location... locations) {
        this(particle, 50, 0, locations);
    }

    @Override
    public void display() {
        int current = 0;
        int estimatedOverallCount = 0;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        main:
        for (int i = 0; i < locations.size() - 1; i++) {
            Location start = locations.get(i);
            Location end = locations.get(i + 1);
            double distance = start.distance(end);
            double control = (distance / frequency) * locations.size();

            if (trackCount && overallCount >= estimatedOverallCount + (distance / control)) {
                estimatedOverallCount += distance / control;
                current = overallCount - estimatedOverallCount;
                continue;
            }

            locationHelper.zero().add(start);
            LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

            if (trackCount) {
                locationHelper.add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);
            }

            for (double length = control * current; length <= distance; length += control) {
                if (mechanic != null) mechanic.apply(particle, locationHelper, vectorHelper);

                locationHelper.add(vectorHelper);
                getCurrentParticle().display(locationHelper);

                overallCount++;

                if (trackCount) {
                    currentCount++;
                    hasRan = true;

                    if (currentCount >= particlesPerDisplay) {
                        currentCount = 0;
                        break main;
                    }
                }
            }
        }

        if (!trackCount) overallCount = 0;
        if (!hasRan && trackCount) overallCount = 0;
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);
        rot2.apply(around, locations);
        rot2.apply(around, rot.getOrigins());
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location centroid = locationHelper.zero();
        int amount = locations.size();

        for (Location l : rot.getOrigins()) centroid.add(l);

        centroid.setX(centroid.getX() / amount);
        centroid.setY(centroid.getY() / amount);
        centroid.setZ(centroid.getZ() / amount);
        rot.add(pitch, yaw, roll);
        rot.apply(centroid, locations);
    }

    @Override
    public void move(double x, double y, double z) {
        rot.moveOrigins(x, y, z);
        rot2.moveOrigins(x, y, z);

        for (Location l : locations) l.add(x, y, z);
    }

    public void moveOne(int index, double x, double y, double z) {
        rot.getOrigins().get(index).add(x, y, z);
        rot2.getOrigins().get(index).add(x, y, z);
        locations.get(index).add(x, y, z);
    }

    public void addLocation(Location location) {
        Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");

        rot.addOrigins(location);
        rot2.addOrigins(location);
        locations.add(location);
    }

    public void removeLocation(int index) {
        rot.removeOrigin(index);
        rot2.removeOrigin(index);
        locations.remove(index);
    }
}
