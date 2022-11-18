package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.util.UUID;

public class ParticleLine extends ParticleShaper {

    //TODO: improve display()

    public ParticleLine(Particle particle, double frequency, LocationSafe... locations) {
        super(particle, frequency);

        Validate.isTrue(locations.length >= 2, "Line must have 2 or more locations!");

        for (int i = 0; i < locations.length; i++) {
            addLocation(locations[i]);
        }

        setWorld(super.locations.get(0).getWorld());
        start();
    }

    public ParticleLine(Particle particle, LocationSafe... locations) {
        this(particle, 50, locations);
    }

    @Override
    public void display() {
        double control = getTotalDistance() / particleFrequency;
        int current = 0;
        int estimatedOverallCount = 0;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        main:
        for (int i = 0; i < locations.size() - 1; i++) {
            Location start = locations.get(i);
            Location end = locations.get(i + 1);
            double distance = start.distance(end);

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
                Particle particle = getCurrentParticle();

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

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

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public ParticleLine clone() {
        LocationSafe[] locations = new LocationSafe[this.locations.size()];

        for (int i = 0; i < getLocationAmount(); i++) {
            locations[i] = this.locations.get(i).clone();
        }

        ParticleLine clone = new ParticleLine(particle, particleFrequency, locations);

        for (Pair<Particle, Integer> pair : secondaryParticles) {
            clone.addParticle(pair.getKey(), pair.getValue());
        }

        for (Pair<ShapeDisplayMechanic, ShapeDisplayMechanic.Phase> pair : mechanics) {
            clone.addMechanic(pair.getValue(), pair.getKey());
        }

        for (UUID id : players) {
            clone.addPlayer(id);
        }

        clone.setParticlesPerDisplay(particlesPerDisplay);

        return clone;
    }

    public void addLocation(LocationSafe location) {
        Validate.notNull(location, "Location can't be null!");
        Validate.notNull(location.getWorld(), "Locations cannot have null worlds!");

        if (locations.size() != 0) {
            Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");
        }

        locations.add(location);
        origins.add(location.cloneToLocation());
        location.setChanged(true);
    }

    public void removeLocation(int index) {
        Validate.isTrue(locations.size() - 1 >= 2, "List must contain 2 or more locations!");

        locations.remove(index);
        origins.remove(index);
        locations.get(0).setChanged(true);
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }
}
