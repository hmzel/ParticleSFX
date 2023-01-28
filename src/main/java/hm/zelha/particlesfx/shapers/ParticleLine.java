package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class ParticleLine extends ParticleShaper {
    public ParticleLine(Particle particle, int particleFrequency, LocationSafe... locations) {
        super(particle, particleFrequency);

        Validate.isTrue(locations.length >= 2, "Line must have 2 or more locations!");

        for (LocationSafe location : locations) {
            addLocation(location);
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
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;

        main:
        for (int i = 0; i < locations.size() - 1; i++) {
            Location start = locations.get(i);
            Location end = locations.get(i + 1);
            int particleAmount = (int) Math.max(start.distance(end) / control, 1);

            if (current >= particleAmount) {
                current -= particleAmount;

                continue;
            }

            locationHelper.zero().add(start);
            LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

            if (current != 0) {
                locationHelper.add(vectorHelper.multiply(current));
                vectorHelper.multiply(1D / current);
            }

            for (int k = current; k < particleAmount; k++) {
                Particle particle = getCurrentParticle();

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

                overallCount++;

                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);

                if (trackCount) {
                    currentCount++;
                    hasRan = true;

                    if (currentCount >= particlesPerDisplay) {
                        currentCount = 0;

                        break main;
                    }
                }
            }

            current = 0;
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

        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    public void addLocation(LocationSafe location) {
        Validate.notNull(location, "Location can't be null!");
        Validate.notNull(location.getWorld(), "Locations cannot have null worlds!");

        if (locations.size() != 0) {
            Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");
        }

        locations.add(location);
        origins.add(location.clone());
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
