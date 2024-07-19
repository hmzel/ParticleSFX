package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticlePolygonFilled extends ParticlePolygon {

    protected final ThreadLocalRandom rng = ThreadLocalRandom.current();
    protected final Location locationHelper2;
    protected final Vector vectorHelper2 = new Vector();

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

        if (corners.length > 0) {
            locationHelper2 = corners[0].getLocation().clone();
        } else {
            locationHelper2 = new Location(null, 0, 0, 0);
        }
    }

    public ParticlePolygonFilled(Particle particle, Corner... corners) {
        this(particle, 150, corners);
    }

    @Override
    public void display() {
        if (corners.size() == 0) return;

        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            Corner corner = corners.get(rng.nextInt(corners.size()));

            if (corner.getConnectionAmount() == 0) continue;

            int index = rng.nextInt(corner.getConnectionAmount());
            Location start = corner.getConnection(index).getLocation();
            Location end;

            if (index + 1 >= corner.getConnectionAmount()) {
                end = corner.getConnection(0).getLocation();
            } else {
                end = corner.getConnection(index + 1).getLocation();
            }

            double startDist = corner.getLocation().distance(start);
            double endDist = corner.getLocation().distance(end);
            double rngDist = rngDouble(startDist);

            locationHelper.zero().add(corner.getLocation());
            locationHelper2.zero().add(corner.getLocation());
            LVMath.subtractToVector(vectorHelper, start, locationHelper).normalize();
            LVMath.subtractToVector(vectorHelper2, end, locationHelper2).normalize();

            if (startDist < endDist) {
                vectorHelper.multiply(rngDist);
                vectorHelper2.multiply(rng.nextDouble(rngDist, endDist));
            } else if (endDist < startDist) {
                rngDist = rngDouble(endDist);

                vectorHelper.multiply(rng.nextDouble(rngDist, startDist));
                vectorHelper2.multiply(rngDist);
            } else {
                vectorHelper.multiply(rngDist);
                vectorHelper2.multiply(rngDist);
            }

            locationHelper.add(vectorHelper);
            locationHelper2.add(vectorHelper2);
            LVMath.subtractToVector(vectorHelper, locationHelper2, locationHelper).normalize();
            vectorHelper.multiply(rngDouble(locationHelper.distance(locationHelper2)));

            if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper);
            if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper);

            applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
            locationHelper.add(vectorHelper);

            if (!players.isEmpty()) {
                particle.displayForPlayers(locationHelper, players);
            } else {
                particle.display(locationHelper);
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
    public ParticlePolygonFilled clone() {
        ParticlePolygonFilled clone = new ParticlePolygonFilled(particle, particleFrequency);

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
    protected void initLayers(LocationSafe center, PolygonLayer... layers) {
        Validate.notNull(center, "Center cant be null!");
        Validate.notNull(center.getWorld(), "World cant be null!");
        Validate.isTrue(layers.length != 0, "Polygon must have 1 or more layer!");

        setWorld(center.getWorld());

        List<Corner> lastCorners = null;
        List<Corner> currentCorners = new ArrayList<>();

        for (PolygonLayer layer : layers) {
            double currentConnection = 0;

            for (int i = 0; i < layer.getCornerAmount(); i++) {
                double radian = (Math.PI / 4) + Math.PI * 2 / layer.getCornerAmount() * i;

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
                    Corner connection = currentCorners.get(currentCorners.size() - 1);

                    corner.connect(connection);
                    connection.connect(corner);
                }

                currentCorners.add(corner);

                if (lastCorners != null) {
                    double connectionInc = (double) lastCorners.size() / layer.getCornerAmount();
                    int k = 0;

                    do {
                        Corner connection = lastCorners.get(((int) currentConnection) + k);

                        corner.connect(connection);
                        connection.connect(corner);
                    } while ((++k) < (int) connectionInc);

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

    protected double rngDouble(double d) {
        if (d <= 0 || !Double.isFinite(d)) return 0;

        return rng.nextDouble(d);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);

        if (locationHelper2 != null) {
            locationHelper2.setWorld(world);
        }
    }
}