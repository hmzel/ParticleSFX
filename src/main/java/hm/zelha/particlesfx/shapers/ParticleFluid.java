package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.shapers.parents.Shape;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleSFX;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import net.minecraft.server.v1_8_R3.Entity;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.Material.AIR;

public class ParticleFluid extends ParticleShaper {

    protected final ThreadLocalRandom rng = ThreadLocalRandom.current();
    protected final Location locationHelper2;
    protected final Vector vectorHelper2 = new Vector();
    protected LocationSafe spawnLocation;
    protected double gravity;
    protected double repulsion;
    protected double repulsionDistance;
    protected double attraction;

    public ParticleFluid(Particle particle, LocationSafe spawnLocation, double gravity, double repulsion, int particleFrequency) {
        super(particle, particleFrequency);

        locationHelper2 = spawnLocation.clone();

        setSpawnLocation(spawnLocation);
        setWorld(spawnLocation.getWorld());
        setParticleFrequency(particleFrequency);
        setGravity(gravity);
        setRepulsion(repulsion);
        setRepulsionDistance(repulsion);
        start();
    }

    @Override
    public ParticleShaper start() {
        //this breaks if its ever ran asynchronously due to minecraft just Absolutely Hating reading any block data not on the main thread
        async = false;

        return super.start();
    }

    @Override
    public void display() {
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < locations.size(); i++) {
            Location l = locations.get(i);
            Particle particle = getCurrentParticle();
            List<Entity> entityList = ((CraftWorld) l.getWorld()).getHandle().entityList;
            int nearby = 0;

            //stuck prevention
            //for some reason some particles still get lodged inside of blocks
            //and i cant figure out why or how to fix it for the life of me
            //i seem to have at least mostly fixed it though
            while (l.getBlock().getType() != AIR) {
                l.setY((int) (l.getY() + 1));
            }

            locationHelper.zero().add(l);
            locationHelper2.zero();

            //attraction
            if (attraction > 0) {
                locationHelper2.zero();

                for (int k = 0; k < locations.size(); k++) {
                    locationHelper2.add(locations.get(k));
                }

                locationHelper2.multiply(1D / locations.size());
                LVMath.subtractToVector(vectorHelper, locationHelper2, locationHelper);
                LVMath.divide(vectorHelper, LVMath.getAbsoluteSum(vectorHelper) / attraction);
                locationHelper.add(vectorHelper);
            }

            //repulsion
            if (repulsion > 0) {
                for (int k = 0; k < locations.size(); k++) {
                    Location other = locations.get(k);

                    if (l == other) continue;
                    if (locationHelper.distanceSquared(other) > Math.pow(repulsionDistance, 2)) continue;

                    LVMath.subtractToVector(vectorHelper, locationHelper, other);

                    if (LVMath.getAbsoluteSum(vectorHelper) == 0) {
                        vectorHelper.setX(rng.nextDouble(repulsion * 2) - repulsion);
                        vectorHelper.setY(rng.nextDouble(repulsion * 2) - repulsion);
                        vectorHelper.setZ(rng.nextDouble(repulsion * 2) - repulsion);
                    }

                    LVMath.divide(vectorHelper, LVMath.getAbsoluteSum(vectorHelper) / repulsion);
                    locationHelper.add(vectorHelper);

                    nearby++;
                }
            }

            //go up if theres more than 2 particles nearby
            if (nearby > 1) {
                locationHelper.setY(locationHelper.getY() + (repulsion * (1 - (1D / nearby))));
            }

            //gravity
            locationHelper.subtract(0, gravity, 0);

            //entity collision
            for (int k = 0; k < entityList.size(); k++) {
                Entity e = entityList.get(k);

                //checking if locationHelper is within range of entity
                if (locationHelper.getX() < e.locX - (e.width / 2) - repulsion) continue;
                if (locationHelper.getY() < e.locY - repulsion) continue;
                if (locationHelper.getZ() < e.locZ - (e.width / 2) - repulsion) continue;
                if (locationHelper.getX() >= e.locX + (e.width / 2) + repulsion) continue;
                if (locationHelper.getY() >= e.locY + e.length + repulsion) continue;
                if (locationHelper.getZ() >= e.locZ + (e.width / 2) + repulsion) continue;

                locationHelper2.zero().add(e.locX, e.locY + (e.length / 2), e.locZ);
                LVMath.subtractToVector(vectorHelper, locationHelper, locationHelper2);
                vectorHelper2.zero().add(vectorHelper);

                double bound = (e.width / 2) + repulsion;

                if (Math.abs(vectorHelper2.getX()) > Math.abs(vectorHelper2.getZ())) {
                    if (vectorHelper2.getX() < 0) bound = -bound;

                    vectorHelper2.setX(bound);
                } else {
                    if (vectorHelper2.getZ() < 0) bound = -bound;

                    vectorHelper2.setZ(bound);
                }

                vectorHelper2.subtract(vectorHelper);
                locationHelper.add(vectorHelper2);
            }

            //block collision
            LVMath.subtractToVector(vectorHelper, locationHelper, l);

            if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper);
            if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper);

            applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, l, vectorHelper);

            double increase = 0.1;
            double absoluteSum = LVMath.getAbsoluteSum(vectorHelper);

            if (absoluteSum < increase) {
                increase = absoluteSum;
            }

            LVMath.divide(vectorHelper, absoluteSum / increase);

            for (double k = 0; k < absoluteSum; k += increase) {
                locationHelper2.zero().add(l);

                if (locationHelper2.add(increase, 0, 0).getBlock().getType() != AIR && vectorHelper.getX() > 0) {
                    vectorHelper.setX(0);
                }

                if (locationHelper2.subtract(increase * 2, 0, 0).getBlock().getType() != AIR && vectorHelper.getX() < 0) {
                    vectorHelper.setX(0);
                }

                if (locationHelper2.add(increase, increase, 0).getBlock().getType() != AIR && vectorHelper.getY() > 0) {
                    vectorHelper.setY(0);
                }

                if (locationHelper2.subtract(0, increase * 2, 0).getBlock().getType() != AIR && vectorHelper.getY() < 0) {
                    vectorHelper.setY(0);
                }

                if (locationHelper2.add(0, increase, increase).getBlock().getType() != AIR && vectorHelper.getZ() > 0) {
                    vectorHelper.setZ(0);
                }

                if (locationHelper2.subtract(0, 0, increase * 2).getBlock().getType() != AIR && vectorHelper.getZ() < 0) {
                    vectorHelper.setZ(0);
                }

                LVMath.divide(vectorHelper, LVMath.getAbsoluteSum(vectorHelper) / increase);
                l.add(vectorHelper);
            }

            if (!players.isEmpty()) {
                particle.displayForPlayers(l, players);
            } else {
                particle.display(l);
            }

            overallCount++;
            currentCount++;

            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_PARTICLE, particle, locationHelper, vectorHelper);

            if (trackCount && currentCount >= particlesPerDisplay) {
                currentCount = 0;
                hasRan = true;

                break;
            }
        }

        if (!trackCount || hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);
        }

        if (!trackCount || !hasRan) {
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_FULL, particle, locationHelper, vectorHelper);

            overallCount = 0;

            if (trackCount) {
                display();

                return;
            }

            currentCount = 0;
        }
    }

    @Override
    public ParticleFluid clone() {
        ParticleFluid clone = (ParticleFluid) super.clone();
        clone.particleFrequency = particleFrequency;

        clone.locations.clear();
        clone.origins.clear();
        clone.setRepulsionDistance(repulsionDistance);
        clone.setAttraction(attraction);

        for (LocationSafe l : locations) {
            clone.locations.add(l.clone());
            clone.origins.add(l.clone());
        }

        return clone;
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        return new ParticleFluid(particle, spawnLocation.clone(), gravity, repulsion, 1);
    }

    @Override
    public void setParticleFrequency(int particleFrequency) {
        Validate.isTrue(particleFrequency > 0, "Cannot have a particle frequency of 0 or less!");

        super.particleFrequency = particleFrequency;

        if (spawnLocation == null) return;

        if (particleFrequency > locations.size()) {
            for (int i = locations.size(); i < particleFrequency; i++) {
                locations.add(spawnLocation.clone());
                origins.add(spawnLocation.clone());
            }
        } else if (particleFrequency < locations.size()) {
            for (int i = particleFrequency; i < locations.size(); i++) {
                int index = rng.nextInt(locations.size());

                locations.remove(index);
                origins.remove(index);
            }
        }

        locations.get(0).setChanged(true);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        spawnLocation.setWorld(world);
        locationHelper2.setWorld(world);
    }

    /**
     * @param gravity how much the particle moves towards the ground per display
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
     * @param repulsion how much particles push each other away
     */
    public void setRepulsion(double repulsion) {
        this.repulsion = Math.abs(repulsion);
    }

    /**
     * @param repulsionDistance how close particles have to be to push each other away
     */
    public ParticleFluid setRepulsionDistance(double repulsionDistance) {
        this.repulsionDistance = Math.abs(repulsionDistance);

        return this;
    }

    /**
     * @param attraction how much particles move toward each other
     */
    public ParticleFluid setAttraction(double attraction) {
        this.attraction = Math.abs(attraction);

        return this;
    }

    /**
     * @param spawnLocation where particles will appear when increasing particle frequency
     */
    public void setSpawnLocation(LocationSafe spawnLocation) {
        Validate.notNull(spawnLocation, "Location cannot be null!");
        Validate.notNull(spawnLocation.getWorld(), "Location's world cannot be null!");

        this.spawnLocation = spawnLocation;
    }

    /**
     * @return how much the particle moves towards the ground per display
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * @return how much particles push each other away
     */
    public double getRepulsion() {
        return repulsion;
    }

    /**
     * @return how close particles have to be to push each other away
     */
    public double getRepulsionDistance() {
        return repulsionDistance;
    }

    /**
     * @return how much particles move toward each other
     */
    public double getAttraction() {
        return attraction;
    }

    /**
     * @return where particles will appear when increasing particle frequency
     */
    public LocationSafe getSpawnLocation() {
        return spawnLocation;
    }
}