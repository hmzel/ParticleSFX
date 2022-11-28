package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticlePolygon extends ParticleShaper {

    private final List<Corner> corners = new ArrayList<>();
    private final Rotation rotHelper = new Rotation();

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        PolygonLayer[] polygonLayers = new PolygonLayer[layers];

        if (layers == 1) {
            polygonLayers[0] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, 0);
        } else if (layers == 2) {
            polygonLayers[0] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, yRadius);
            polygonLayers[1] = new PolygonLayer(cornersPerLayer, xRadius, zRadius, -yRadius);
        } else {
            int arrayPos = 0;

            for (double i = 0; true; i += Math.PI / (layers - 1)) {
                //the long decimal number is used to cut PI to the 29th decimal place to prevent some double weirdness that i dont even understand
                if (i > Math.PI - 3.5897932384626433832795028841972e-9) {
                    i = Math.PI;
                }

                double x = xRadius * Math.sin(i);
                double z = zRadius * Math.sin(i);

                if (x < 0.05 && z < 0.05) {
                    polygonLayers[arrayPos] = new PolygonLayer(1, x, z, yRadius * Math.cos(i));
                } else {
                    polygonLayers[arrayPos] = new PolygonLayer(cornersPerLayer, x, z, yRadius * Math.cos(i));
                }

                if (i == Math.PI) break;

                arrayPos++;
            }
        }

        initLayers(center, polygonLayers);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int corners, int layers, double xRadius, double yRadius, double zRadius) {
        this(particle, center, corners, layers, xRadius, yRadius, zRadius, 150);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int corners, int layers, double radius, int particleFrequency) {
        this(particle, center, corners, layers, radius, radius, radius, particleFrequency);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int corners, int layers, double radius) {
        this(particle, center, corners, layers, radius, radius, radius, 150);
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
        int current = 0;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        if (trackCount) {
            current = overallCount;
        }

        main:
        for (Corner corner : corners) {
            for (int i = 0; i < corner.getConnectionAmount(); i++) {
                Location start = corner.getLocation();
                Location end = corner.getConnection(i).getLocation();
                double distance = start.distance(end);

                if (trackCount && current >= distance / control) {
                    current -= distance / control;
                    continue;
                }

                locationHelper.zero().add(start);
                LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

                if (trackCount) {
                    locationHelper.add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);
                }

                for (double length = control * current; length <= distance; length += control) {
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
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public ParticlePolygon clone() {
        ParticlePolygon clone = new ParticlePolygon(particle, particleFrequency);
        int index = 0;

        for (Corner corner : corners) {
            clone.addCorner(new Corner(corner.getLocation().clone()));
        }

        for (Corner corner : corners) {
            for (int i = 0; i < corner.getConnectionAmount(); i++) {
                if (!corners.contains(corner.getConnection(i))) continue;

                clone.getCorner(index).connect(clone.getCorner(corners.lastIndexOf(corner.getConnection(i))));
            }

            index++;
        }

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

    private void initLayers(LocationSafe center, PolygonLayer... layers) {
        Validate.notNull(center, "Center cant be null!");
        Validate.notNull(center.getWorld(), "World cant be null!");
        Validate.isTrue(layers.length != 0, "Polygon must have 1 or more layer!");

        setWorld(center.getWorld());

        List<Corner> lastCorners = null;
        List<Corner> currentCorners = new ArrayList<>();
        double currentConnection;
        //last sides + 1 / current sides + 1

        for (PolygonLayer layer : layers) {
            currentConnection = 0;

            //the long decimal number is used to cut PI * 2 to the 29th decimal place to prevent some double weirdness that i dont even understand
            for (double radian = 0; radian < (Math.PI * 2) - 1.4769252867665590057683943387986e-15; radian += Math.PI * 2 / layer.getCorners()) {
                vectorHelper.setX(layer.getXRadius() * Math.cos(radian));
                vectorHelper.setY(layer.getYPosition());
                vectorHelper.setZ(layer.getZRadius() * Math.sin(radian));
                rotHelper.set(layer.getPitch(), layer.getYaw(), layer.getRoll());
                rotHelper.apply(vectorHelper);
                locationHelper.zero().add(center);
                locationHelper.add(vectorHelper);

                Corner corner = new Corner(new LocationSafe(locationHelper.getWorld(), locationHelper.getX(), locationHelper.getY(), locationHelper.getZ()));

                addCorner(corner);

                if (!currentCorners.isEmpty()) {
                    corner.connect(currentCorners.get(currentCorners.size() - 1));
                }

                currentCorners.add(corner);

                if (lastCorners != null) {
                    double connectionInc = (double) lastCorners.size() / layer.getCorners();

                    if (connectionInc < 2) {
                        corner.connect(lastCorners.get((int) currentConnection));
                    } else {
                        for (int i = 0; i < (int) connectionInc; i++) {
                            corner.connect(lastCorners.get(((int) currentConnection) + i));
                        }
                    }

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
        locations.add(corner.getLocation());
        origins.add(corner.getLocation().cloneToLocation());
        corner.getLocation().setChanged(true);

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
        removeCorner(index, false);
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
