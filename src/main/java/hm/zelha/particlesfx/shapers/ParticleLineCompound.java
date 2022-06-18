package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCompound extends ParticleShaper {

    private final List<Location> originalLocations = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final Location locationHelper;
    private final Vector vectorHelper = new Vector(0, 0, 0);

    public ParticleLineCompound(Particle particle, double frequency, Location... locations) {
        super(particle, 0, 0, 0, frequency);

        Validate.isTrue(locations.length >= 2, "Compound line must have 2 or more locations!");

        World world = locations[0].getWorld();
        locationHelper = new Location(world, 0, 0, 0);

        for (Location l : locations) {
            Validate.isTrue(l.getWorld() != null, "Locations cannot have null worlds!");
            Validate.isTrue(l.getWorld().equals(world), "Locations cannot have different worlds!");

            originalLocations.add(l);
            this.locations.add(l.clone());
        }
    }

    public ParticleLineCompound(Particle particle, Location... locations) {
        this(particle, 50, locations);
    }

    @Override
    public void display() {
        for (int i = 0; i < locations.size() - 1; i++) {
            Location start = locations.get(i);
            Location end = locations.get(i + 1);
            double distance = start.distance(end);
            double control = distance / frequency;

            locationHelper.zero().add(start);
            LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

            for (double length = 0; length < distance; length += control, locationHelper.add(vectorHelper)) {
                particle.display(locationHelper);
            }
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);

        for (int i = 0; i < locations.size(); i++) {
            Location l = originalLocations.get(i);
            Location l2 = locations.get(i);
            Vector v = rot2.apply(LVMath.subtractToVector(vectorHelper, l, around));

            LVMath.additionToLocation(l2, around, v);
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location centroid = locationHelper.zero();
        int amount = originalLocations.size();

        for (Location l : originalLocations) centroid.add(l);

        centroid.setX(centroid.getX() / amount);
        centroid.setY(centroid.getY() / amount);
        centroid.setZ(centroid.getZ() / amount);
        rot.add(pitch, yaw, roll);

        for (int i = 0; i < amount; i++) {
            Location l = originalLocations.get(i);
            Location l2 = locations.get(i);
            Vector v = rot.apply(LVMath.subtractToVector(vectorHelper, l, centroid));

            LVMath.additionToLocation(l2, centroid, v);
        }
    }

    @Override
    public void move(double x, double y, double z) {
        for (Location l : originalLocations) l.add(x, y, z);
        for (Location l : locations) l.add(x, y, z);
    }

    public void addLocation(Location location) {
        Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");

        originalLocations.add(location);
        locations.add(location.clone());
    }

    public void removeLocation(int index) {
        originalLocations.remove(index);
        locations.remove(index);
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }
}
