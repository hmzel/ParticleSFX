package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ParticleLine extends ParticleShaper {

    private final List<Location> locations = new ArrayList<>();

    public ParticleLine(Particle particle, double frequency, int particlesPerDisplay, Location... locations) {
        super(particle, 0, 0, 0, frequency, particlesPerDisplay);

        Validate.isTrue(locations.length >= 2, "Line must have 2 or more locations!");

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

    public ParticleLine(Particle particle, int particlesPerDisplay, Location... locations) {
        this(particle, 50, particlesPerDisplay, locations);
    }

    public ParticleLine(Particle particle, double frequency, Location... locations) {
        this(particle, frequency, 0, locations);
    }

    public ParticleLine(Particle particle, Location... locations) {
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
                continue;
            }

            locationHelper.zero().add(start);
            LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

            if (trackCount) {
                current = overallCount - estimatedOverallCount;

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

        for (int i = 0; i < locations.size(); i++) centroid.add(rot.getOrigins().get(i));

        centroid.multiply(1D / locations.size());
        rot.add(pitch, yaw, roll);
        rot.apply(centroid, locations);
    }

    @Override
    public void move(double x, double y, double z) {
        rot.moveOrigins(x, y, z);
        rot2.moveOrigins(x, y, z);

        for (int i = 0; i < locations.size(); i++) locations.get(i).add(x, y, z);
    }

    @Override
    public void face(Location toFace) {
        Location centroid = locationHelper.zero();

        for (int i = 0; i < locations.size(); i++) centroid.add(rot.getOrigins().get(i));

        centroid.multiply(1D / locations.size());

        double xDiff = toFace.getX() - centroid.getX();
        double yDiff = toFace.getY() - centroid.getY();
        double zDiff = toFace.getZ() - centroid.getZ();
        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY));

        if (zDiff < 0.0D) yaw += Math.abs(180.0D - yaw) * 2.0D;

        rot.set(pitch, yaw - 90, rot.getRoll());
        rot.apply(centroid, locations);
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

    public double getTotalDistance() {
        double dist = 0;

        //adding the distance between every circle to dist
        for (int i = 0; i < locations.size() - 1; i++) dist += locations.get(i).distance(locations.get(i + 1));

        return  dist;
    }
}
