package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationS;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class ParticleLine extends ParticleShaper {

    //TODO: improve display()

    public ParticleLine(Particle particle, double frequency, LocationS... locations) {
        super(particle, frequency);

        Validate.isTrue(locations.length >= 2, "Line must have 2 or more locations!");

        for (int i = 0; i < locations.length; i++) addLocation(locations[i]);

        setWorld(super.locations.get(0).getWorld());
        start();
    }

    public ParticleLine(Particle particle, LocationS... locations) {
        this(particle, 50);
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
            double control = (distance / particleFrequency) * locations.size();

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

    public void addLocation(LocationS location) {
        Validate.notNull(location, "Location can't be null!");
        Validate.notNull(location.getWorld(), "Locations cannot have null worlds!");

        if (locations.size() != 0) {
            Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");
        }

        locations.add(location);
        origins.add(location.cloneToLocation());
        aroundOrigins.add(location.cloneToLocation());

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            location.setChanged(true);
        }
    }

    public void removeLocation(int index) {
        Validate.isTrue(locations.size() - 1 >= 2, "List must contain 2 or more locations!");

        locations.remove(index);
        origins.remove(index);
        aroundOrigins.remove(index);

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            locations.get(0).setChanged(true);
        }
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }
}
