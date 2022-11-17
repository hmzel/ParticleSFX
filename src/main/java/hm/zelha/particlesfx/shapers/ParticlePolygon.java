package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.Corner;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.PolygonLayer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ParticlePolygon extends ParticleShaper {

    private final List<Corner> corners = new ArrayList<>();

    public ParticlePolygon(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius, double particleFrequency) {
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

    public ParticlePolygon(Particle particle, LocationSafe center, int corners, int layers, double radius, double particleFrequency) {
        this(particle, center, corners, layers, radius, radius, radius, particleFrequency);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, int corners, int layers, double radius) {
        this(particle, center, corners, layers, radius, radius, radius, 150);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, double particleFrequency, PolygonLayer... layers) {
        super(particle, particleFrequency);
        initLayers(center, layers);
    }

    public ParticlePolygon(Particle particle, LocationSafe center, PolygonLayer... layers) {
        this(particle, center, 150, layers);
    }

    @Override
    public void display() {
        double control = getTotalDistance() / particleFrequency;

        for (Corner corner : corners) {
            for (int i = 0; i < corner.getConnectionAmount(); i++) {
                Location start = corner.getLocation();
                Location end = corner.getConnection(i).getLocation();
                double distance = start.distance(end);

                locationHelper.zero().add(start);
                LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

                for (double length = 0; length <= distance; length += control) {
                    getCurrentParticle().display(locationHelper);
                    locationHelper.add(vectorHelper);
                }
            }
        }
    }

    @Override
    public ParticlePolygon clone() {
        return null;
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
