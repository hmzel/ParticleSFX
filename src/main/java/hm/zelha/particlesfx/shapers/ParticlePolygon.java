package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ParticlePolygon extends ParticleShaper {

    protected final List<Corner> corners = new ArrayList<>();

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        PolygonLayer[] polygonLayers = new PolygonLayer[layers];

        if (layers == 1) {
            polygonLayers[0] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, 0);
        } else if (layers == 2) {
            polygonLayers[0] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, yRadius);
            polygonLayers[1] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, -yRadius);
        } else {
            for (int i = 0; i < layers; i++) {
                double radian = Math.PI / (layers - 1) * i;
                double x = xRadius * Math.sin(radian);
                double z = zRadius * Math.sin(radian);

                if (x < 0.05 && z < 0.05) {
                    polygonLayers[i] = new PolygonLayer(1, x, z, yRadius * Math.cos(radian));
                } else {
                    polygonLayers[i] = new PolygonLayer(cornersPerLayer, x, z, yRadius * Math.cos(radian));
                }
            }
        }

        initLayers(center, polygonLayers);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius) {
        this(particle, center, cornersPerLayer, layers, xRadius, yRadius, zRadius, 150);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double radius, int particleFrequency) {
        this(particle, center, cornersPerLayer, layers, radius, radius, radius, particleFrequency);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double radius) {
        this(particle, center, cornersPerLayer, layers, radius, radius, radius, 150);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int particleFrequency, PolygonLayer... layers) {
        super(particle, particleFrequency);
        initLayers(center, layers);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, PolygonLayer... layers) {
        this(particle, center, 150, layers);
    }

    public ParticlePolygon(Particle particle, int particleFrequency, Corner... corners) {
        super(particle, particleFrequency);

        for (Corner corner : corners) {
            addCorner(corner);
        }

        start();
    }

    public ParticlePolygon(Particle particle, Corner... corners) {
        this(particle, 150, corners);
    }

    @Override
    public void display() {
        double control = getTotalDistance() / particleFrequency;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;

        main:
        for (Corner corner : corners) {
            for (int i = 0; i < corner.getConnectionAmount(); i++) {
                Location start = corner.getLocation();
                Location end = corner.getConnection(i).getLocation();
                int particleAmount = (int) Math.max(start.distance(end) / control, 1);

                if (current >= particleAmount) {
                    current -= particleAmount;

                    continue;
                }

                locationHelper.zero().add(start);
                LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);
                locationHelper.add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);

                for (int k = current; k < particleAmount; k++) {
                    Particle particle = getCurrentParticle();

                    applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);

                    if (!players.isEmpty()) {
                        particle.displayForPlayers(locationHelper, players);
                    } else {
                        particle.display(locationHelper);
                    }

                    overallCount++;

                    locationHelper.add(vectorHelper);
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
            }
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public ParticlePolygon clone() {
        ParticlePolygon clone = new ParticlePolygon(particle, particleFrequency);

        for (Corner corner : corners) {
            clone.addCorner(new Corner((LocationSafe) corner.getLocation().clone()));
        }

        for (int i = 0; i < corners.size(); i++) {
            Corner corner = corners.get(i);

            for (int k = 0; k < corner.getConnectionAmount(); k++) {
                Corner connection = corner.getConnection(k);

                if (corners.contains(connection)) {
                    clone.getCorner(i).connect(clone.getCorner(corners.lastIndexOf(connection)));
                } else {
                    clone.getCorner(i).connect(connection);
                }
            }
        }

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    @Override
    protected boolean recalculateIfNeeded(@Nullable Location around) {
        for (int i = 0; i < corners.size(); i++) {
            if (corners.get(i).getLocation() != locations.get(i)) {
                locations.set(i, (LocationSafe) corners.get(i).getLocation());
            }
        }

        return super.recalculateIfNeeded(around);
    }

    protected void initLayers(LocationSafe center, PolygonLayer... layers) {
        Validate.notNull(center, "Center cant be null!");
        Validate.notNull(center.getWorld(), "World cant be null!");
        Validate.isTrue(layers.length != 0, "Polygon must have 1 or more layer!");

        setWorld(center.getWorld());

        List<Corner> lastCorners = null;
        List<Corner> currentCorners = new ArrayList<>();

        for (PolygonLayer layer : layers) {
            double currentConnection = 0;

            for (int i = 0; i < layer.getCorners(); i++) {
                double radian = Math.PI * 2 / layer.getCorners() * i;

                vectorHelper.setX(layer.getXRadius() * Math.cos(radian));
                vectorHelper.setY(layer.getYPosition());
                vectorHelper.setZ(layer.getZRadius() * Math.sin(radian));
                rotHelper.set(layer.getPitch(), layer.getYaw(), layer.getRoll());
                rotHelper.apply(vectorHelper);
                locationHelper.zero().add(center);
                locationHelper.add(vectorHelper);

                Corner corner = new Corner(new LocationSafe(locationHelper));

                addCorner(corner);

                if (!currentCorners.isEmpty()) {
                    corner.connect(currentCorners.get(currentCorners.size() - 1));
                }

                currentCorners.add(corner);

                if (lastCorners != null) {
                    double connectionInc = (double) lastCorners.size() / layer.getCorners();
                    int k = 0;

                    do {
                        corner.connect(lastCorners.get(((int) currentConnection) + k));
                    } while ((++k) < (int) connectionInc);
                    //https://stackoverflow.com/questions/33645518/difference-between-and-in-java

                    currentConnection += connectionInc;
                }
            }

            if (currentCorners.size() > 1) {
                currentCorners.get(currentCorners.size() - 1).connect(currentCorners.get(0));
            }

            lastCorners = currentCorners;
            currentCorners = new ArrayList<>();
        }

        start();
    }

    /**
     * @param corner corner to add
     * @param connections index of other corners to connect to the added corner
     */
    public void addCorner(Corner corner, int... connections) {
        Validate.notNull(corner, "Corner cant be null!");

        if (!locations.isEmpty()) {
            Validate.isTrue(corner.getLocation().getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");
        } else {
            setWorld(corner.getLocation().getWorld());
        }

        corners.add(corner);
        locations.add((LocationSafe) corner.getLocation());
        origins.add(corner.getLocation().clone());
        ((LocationSafe) corner.getLocation()).setChanged(true);

        for (int i : connections) {
            corner.connect(corners.get(i));
        }
    }

    /**
     * @param index index of the corner to remove
     * @param removeConnections whether all corners should sever their connection to the removed corner
     */
    public void removeCorner(int index, boolean removeConnections) {
        if (removeConnections) {
            for (Corner corner : corners) {
                corner.disconnect(corners.get(index));
            }
        }

        corners.remove(index);
        locations.remove(index);
        origins.remove(index);

        if (!locations.isEmpty()) {
            locations.get(0).setChanged(true);
        }
    }

    /**
     * @param index index of the corner to remove
     */
    public void removeCorner(int index) {
        removeCorner(index, true);
    }

    public Corner getCorner(int index) {
        return corners.get(index);
    }

    public int getCornerAmount() {
        return corners.size();
    }

    @Override
    public double getTotalDistance() {
        double dist = 0;

        for (Corner corner : corners) {
            for (int i = 0; i < corner.getConnectionAmount(); i++) {
                dist += corner.getLocation().distance(corner.getConnection(i).getLocation());
            }
        }

        return dist;
    }
}
