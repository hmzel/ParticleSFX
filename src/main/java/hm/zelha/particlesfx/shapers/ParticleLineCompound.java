package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
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

        locationHelper = new Location(locations[0].getWorld(), 0, 0, 0);
        World world = locations[0].getWorld();

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

            setXYZ(locationHelper, start.getX(), start.getY(), start.getZ());
            vectorHelper.setX(end.getX() - start.getX()).setY(end.getY() - start.getY()).setZ(end.getZ() - start.getZ()).normalize().multiply(control);

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
            Vector v = rot2.apply(vectorHelper.setX(l.getX() - around.getX()).setY(l.getY() - around.getY()).setZ(l.getZ() - around.getZ()));

            setXYZ(l2, around.getX() + v.getX(), around.getY() + v.getY(), around.getZ() + v.getZ());
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
            Vector v = rot.apply(vectorHelper.setX(l.getX() - centroid.getX()).setY(l.getY() - centroid.getY()).setZ(l.getZ() - centroid.getZ()));

            setXYZ(l2, centroid.getX() + v.getX(), centroid.getY() + v.getY(), centroid.getZ() + v.getZ());
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

    public Location getLocation(int index) {
        return locations.get(index);
    }
}
