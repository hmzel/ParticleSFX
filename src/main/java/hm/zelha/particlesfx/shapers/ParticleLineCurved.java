package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.CurveInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCurved extends ParticleShaper {

    //TODO:
    // make this class extend ParticleLine instead of ParticleShaper and reduce code accordingly

    private final List<Location> locations = new ArrayList<>();
    private final List<CurveInfo> curves = new ArrayList<>();
    private final List<double[]> linePitchAndYaw = new ArrayList<>();
    private final RotationHandler rot3 = new RotationHandler(0, 0, 0);
    private final Vector vectorHelper2 = new Vector(0, 0, 0);

    public ParticleLineCurved(Particle particle, double frequency, int particlesPerDisplay, Location... locations) {
        super(particle, 0, 0, 0, frequency, particlesPerDisplay);

        Validate.isTrue(locations.length >= 2, "Line must have 2 or more locations!");

        World world = locations[0].getWorld();
        locationHelper.setWorld(world);

        for (int i = 0; i < locations.length; i++) {
            Location l = locations[i];

            Validate.isTrue(l.getWorld() != null, "Locations cannot have null worlds!");
            Validate.isTrue(l.getWorld().equals(world), "Locations cannot have different worlds!");

            this.locations.add(l);

            if (i < locations.length - 1) {
                linePitchAndYaw.add(calculateLinePitch(l, locations[i + 1]));
            }
        }

        rot.addOrigins(locations);
        rot2.addOrigins(locations);
    }

    public ParticleLineCurved(Particle particle, int particlesPerDisplay, Location... locations) {
        this(particle, 50, particlesPerDisplay, locations);
    }

    public ParticleLineCurved(Particle particle, double frequency, Location... locations) {
        this(particle, frequency, 0, locations);
    }

    public ParticleLineCurved(Particle particle, Location... locations) {
        this(particle, 50, 0, locations);
    }

    //TODO: make this thread-safe
    @Override
    public void display() {
        int curveIndex = 0;
        CurveInfo curve = (curves.isEmpty()) ? null : curves.get(0);
        int current = 0;
        int estimatedOverallCount = 0;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        double curveApex = 0, curveEnd = 0, curveCurrent = 0;

        if (curve != null) {
            curveApex = curve.getApexPosition();
            curveEnd = curve.getLength();
        }

        main:
        for (int i = 0; i < locations.size() - 1; i++) {
            Location start = locations.get(i);
            Location end = locations.get(i + 1);
            double distance = start.distance(end);
            double control = (distance / frequency) * locations.size();

            if (trackCount && overallCount >= estimatedOverallCount + (distance / control)) {
                estimatedOverallCount += distance / control;
                curveCurrent += distance;

                while (curveCurrent >= curveEnd) {
                    curveIndex++;
                    curve = (curveIndex >= curves.size()) ? null : curves.get(curveIndex);

                    if (curve == null) break;

                    curveCurrent -= curveEnd;
                    curveApex = curve.getApexPosition();
                    curveEnd = curve.getLength();
                    rot3.set(linePitchAndYaw.get(i)[0], linePitchAndYaw.get(i)[1] + 90, curve.getRoll());
                }

                continue;
            }

            locationHelper.zero().add(start);
            LVMath.subtractToVector(vectorHelper, end, start).normalize().multiply(control);

            if (trackCount) {
                current = overallCount - estimatedOverallCount;

                locationHelper.add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);
                curveCurrent += control * current;

                while (curveCurrent >= curveEnd) {
                    curveIndex++;
                    curve = (curveIndex >= curves.size()) ? null : curves.get(curveIndex);

                    if (curve == null) break;

                    curveCurrent -= curveEnd;
                    curveApex = curve.getApexPosition();
                    curveEnd = curve.getLength();
                    rot3.set(linePitchAndYaw.get(i)[0], linePitchAndYaw.get(i)[1] + 90, curve.getRoll());
                }
            }

            if (curve != null && curve.getHeight() != 0) {
                rot3.set(linePitchAndYaw.get(i)[0], linePitchAndYaw.get(i)[1] + 90, curve.getRoll());
            }

            for (double length = control * current; length <= distance; length += control) {
                if (mechanic != null) mechanic.apply(particle, locationHelper, vectorHelper);

                vectorHelper2.zero();
                locationHelper.add(vectorHelper);

                curveCurrent += control;

                while (curveCurrent >= curveEnd) {
                    curveIndex++;
                    curve = (curveIndex >= curves.size()) ? null : curves.get(curveIndex);

                    if (curve == null) break;

                    curveCurrent -= curveEnd;
                    curveApex = curve.getApexPosition();
                    curveEnd = curve.getLength();
                    rot3.set(linePitchAndYaw.get(i)[0], linePitchAndYaw.get(i)[1] + 90, curve.getRoll());
                }

                if (curve != null && curve.getHeight() != 0) {
                    double height = curve.getHeight();

                    if (curveCurrent > curveApex) {
                        //idk what to call this variable it basically determines the decrement of the curve
                        double v = (((curveEnd - curveApex) - (curveEnd - curveCurrent)) / (curveEnd - curveApex));

                        vectorHelper2.setY(height - (height * v) * Math.sin(Math.PI - ((Math.PI / 2) * v)));
                    } else {
                        //determines increment this time
                        double v = ((curveApex - curveCurrent) / curveApex);

                        vectorHelper2.setY(height - (height * v * Math.sin((Math.PI / 2) * v)));
                    }

                    rot3.applyRoll(vectorHelper2);
                    rot3.applyYaw(vectorHelper2);
                    rot3.applyPitch(vectorHelper2);
                    locationHelper.add(vectorHelper2);
                }

                getCurrentParticle().display(locationHelper);
                locationHelper.subtract(vectorHelper2);

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

        //TODO: improve this when you fix RotationHandler
        for (int i = 0; i < linePitchAndYaw.size(); i++) {
            linePitchAndYaw.set(i, calculateLinePitch(locations.get(i), locations.get(i + 1)));
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location centroid = locationHelper.zero();
        int amount = locations.size();

        for (Location l : rot.getOrigins()) centroid.add(l);

        centroid.setX(centroid.getX() / amount);
        centroid.setY(centroid.getY() / amount);
        centroid.setZ(centroid.getZ() / amount);
        rot.add(pitch, yaw, roll);
        rot.apply(centroid, locations);

        //TODO: improve this when you fix RotationHandler
        for (int i = 0; i < linePitchAndYaw.size(); i++) {
            linePitchAndYaw.set(i, calculateLinePitch(locations.get(i), locations.get(i + 1)));
        }
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

        //TODO: improve this when you fix RotationHandler
        for (int i = 0; i < linePitchAndYaw.size(); i++) {
            linePitchAndYaw.set(i, calculateLinePitch(locations.get(i), locations.get(i + 1)));
        }
    }

    public void moveOne(int index, double x, double y, double z) {
        rot.getOrigins().get(index).add(x, y, z);
        rot2.getOrigins().get(index).add(x, y, z);
        locations.get(index).add(x, y, z);

        if (index - 1 >= 0) linePitchAndYaw.set(index - 1, calculateLinePitch(locations.get(index - 1), locations.get(index)));

        linePitchAndYaw.set(index, calculateLinePitch(locations.get(index), locations.get(index + 1)));
    }

    private double[] calculateLinePitch(Location start, Location end) {
        double xDiff = end.getX() - start.getX();
        double yDiff = end.getY() - start.getY();
        double zDiff = end.getZ() - start.getZ();
        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
        double pitch = Math.abs(Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D);
        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));

        if (zDiff < 0.0D) yaw += Math.abs(180.0D - yaw) * 2.0D;
        if (!Double.isFinite(yaw)) yaw = 0;

        return new double[] {pitch, yaw};
    }

    public void addLocation(Location location) {
        Validate.isTrue(location.getWorld().equals(locations.get(0).getWorld()), "Locations cannot have different worlds!");

        rot.addOrigins(location);
        rot2.addOrigins(location);
        locations.add(location);
        linePitchAndYaw.add(calculateLinePitch(locations.get(locations.size() - 2), locations.get(locations.size() - 1)));
    }

    public void addCurve(CurveInfo curve) {
        Validate.isTrue(curve.getLength() <= getTotalDistance(), "Length must be less than or equal to the total distance of the line!");

        curves.add(curve);
    }

    public CurveInfo getCurve(int index) {
        return curves.get(index);
    }

    public void removeLocation(int index) {
        rot.removeOrigin(index);
        rot2.removeOrigin(index);
        locations.remove(index);
        linePitchAndYaw.remove(index);
        linePitchAndYaw.set(index, calculateLinePitch(locations.get(index), locations.get(index + 1)));
    }

    public void removeCurve(int index) {
        curves.remove(index);
    }

    public double getTotalDistance() {
        double dist = 0;

        //adding the distance between every circle to dist
        for (int i = 0; i < locations.size() - 1; i++) dist += locations.get(i).distance(locations.get(i + 1));

        return  dist;
    }
}