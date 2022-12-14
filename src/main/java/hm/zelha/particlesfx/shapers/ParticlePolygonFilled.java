package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Corner;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.PolygonLayer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticlePolygonFilled extends ParticlePolygon {

    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private final Location locationHelper2;
    private final Vector vectorHelper2 = new Vector(0, 0, 0);

    public ParticlePolygonFilled(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        super(particle, center, cornersPerLayer, layers, xRadius, yRadius, zRadius, particleFrequency);

        locationHelper2 = center.clone();
    }

    public ParticlePolygonFilled(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double xRadius, double yRadius, double zRadius) {
        this(particle, center, cornersPerLayer, layers, xRadius, yRadius, zRadius, 150);
    }

    public ParticlePolygonFilled(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double radius, int particleFrequency) {
        this(particle, center, cornersPerLayer, layers, radius, radius, radius, particleFrequency);
    }

    public ParticlePolygonFilled(Particle particle, LocationSafe center, int cornersPerLayer, int layers, double radius) {
        this(particle, center, cornersPerLayer, layers, radius, radius, radius, 150);
    }

    public ParticlePolygonFilled(Particle particle, LocationSafe center, int particleFrequency, PolygonLayer... layers) {
        super(particle, center, particleFrequency, layers);

        locationHelper2 = center.clone();
    }

    public ParticlePolygonFilled(Particle particle, LocationSafe center, PolygonLayer... layers) {
        this(particle, center, 150, layers);
    }

    public ParticlePolygonFilled(Particle particle, int particleFrequency, Corner... corners) {
        super(particle, particleFrequency, corners);

        locationHelper2 = corners[0].getLocation().clone();
    }

    public ParticlePolygonFilled(Particle particle, Corner... corners) {
        this(particle, 150, corners);
    }

    @Override
    public void display() {
        for (int i = 0; i < particleFrequency; i++) {
            Corner corner = corners.get(rng.nextInt(corners.size()));

            if (corner.getConnectionAmount() == 0) {
                i--;
                continue;
            }

            int index = rng.nextInt(corner.getConnectionAmount());
            Corner startCon = corner.getConnection(index);
            Corner endCon;

            if (index + 1 >= corner.getConnectionAmount()) {
                endCon = corner.getConnection(0);
            } else {
                endCon = corner.getConnection(index + 1);
            }

            double startDist = corner.getLocation().distance(startCon.getLocation());
            double endDist = corner.getLocation().distance(endCon.getLocation());

            locationHelper.zero().add(corner.getLocation());
            locationHelper2.zero().add(corner.getLocation());
            LVMath.subtractToVector(vectorHelper, startCon.getLocation(), locationHelper).normalize();
            LVMath.subtractToVector(vectorHelper2, endCon.getLocation(), locationHelper2).normalize();

            if (startDist < endDist) {
                double rngDist = rng.nextDouble(startDist);

                vectorHelper.multiply(rngDist);
                vectorHelper2.multiply(rng.nextDouble(rngDist, endDist));
            } else if (endDist < startDist) {
                double rngDist = rng.nextDouble(endDist);

                vectorHelper.multiply(rng.nextDouble(rngDist, startDist));
                vectorHelper2.multiply(rngDist);
            } else {
                double rngDist = rng.nextDouble(startDist);

                vectorHelper.multiply(rngDist);
                vectorHelper2.multiply(rngDist);
            }

            locationHelper.add(vectorHelper);
            locationHelper2.add(vectorHelper2);
            LVMath.subtractToVector(vectorHelper, locationHelper2, locationHelper).normalize();

            double dist = locationHelper.distance(locationHelper2);

            if (dist > 0) {
                vectorHelper.multiply(rng.nextDouble(dist));
            } else {
                vectorHelper.multiply(0);
            }

            locationHelper.add(vectorHelper);
            getCurrentParticle().display(locationHelper);
        }
    }

    @Override
    protected void initLayers(LocationSafe center, PolygonLayer... layers) {
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
                    Corner connection = currentCorners.get(currentCorners.size() - 1);

                    corner.connect(connection);
                    connection.connect(corner);
                }

                currentCorners.add(corner);

                if (lastCorners != null) {
                    double connectionInc = (double) lastCorners.size() / layer.getCorners();

                    if (connectionInc < 2) {
                        Corner connection = lastCorners.get((int) currentConnection);

                        corner.connect(connection);
                        connection.connect(corner);
                    } else {
                        for (int i = 0; i < (int) connectionInc; i++) {
                            Corner connection = lastCorners.get(((int) currentConnection) + i);

                            corner.connect(connection);
                            connection.connect(corner);
                        }
                    }

                    currentConnection += connectionInc;
                }
            }

            if (currentCorners.size() > 1) {
                Corner firstCorner = currentCorners.get(0);
                Corner lastCorner = currentCorners.get(currentCorners.size() - 1);

                lastCorner.connect(firstCorner);
                firstCorner.connect(lastCorner);

                if (firstCorner.getConnectionAmount() > 2) {
                    Corner firstConnection = firstCorner.getConnection(0);
                    Corner secondConnection = firstCorner.getConnection(1);

                    firstCorner.setConnection(0, secondConnection);
                    firstCorner.setConnection(1, firstConnection);
                }
            }

            lastCorners = currentCorners;
            currentCorners = new ArrayList<>();
        }

        start();
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);

        if (locationHelper2 != null) {
            locationHelper2.setWorld(world);
        }
    }
}



















