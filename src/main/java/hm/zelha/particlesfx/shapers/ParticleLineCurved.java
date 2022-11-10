package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.CurveInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.Rotation;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCurved extends ParticleLine {

    //TODO: make curve rotation stuff less jank

    private final List<CurveInfo> curves = new ArrayList<>();
    private final Rotation rot3 = new Rotation();
    private final Vector vectorHelper2 = new Vector(0, 0, 0);
    private final Vector vectorHelper3 = new Vector(0, 0, 0);
    private List<Double[]> linePitchAndYaw;

    public ParticleLineCurved(Particle particle, double frequency, LocationSafe... locations) {
        super(particle, frequency, locations);
        recalculateAllLinesPitchAndYaw();
    }

    public ParticleLineCurved(Particle particle, LocationSafe... locations) {
        this(particle, 100, locations);
    }

    //TODO: make this thread-safe (and improve?)
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
            double control = (distance / particleFrequency) * locations.size();

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
            LVMath.subtractToVector(vectorHelper3, end, start).normalize().multiply(control);

            if (trackCount) {
                current = overallCount - estimatedOverallCount;
                curveCurrent += control * current;

                locationHelper.add(vectorHelper3.getX() * current, vectorHelper3.getY() * current, vectorHelper3.getZ() * current);

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
                if (mechanic != null) {
                    mechanic.apply(particle, locationHelper, vectorHelper3);
                }

                vectorHelper2.zero();
                locationHelper.add(vectorHelper3);

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

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        super.rotate(pitch, yaw, roll);
        recalculateAllLinesPitchAndYaw();
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        super.rotateAroundLocation(around, pitch, yaw, roll);
        recalculateAllLinesPitchAndYaw();
    }

    @Override
    public void face(Location toFace) {
        super.face(toFace);
        recalculateAllLinesPitchAndYaw();
    }

    @Override
    public void faceAroundLocation(Location toFace, Location around) {
        super.faceAroundLocation(toFace, around);
        recalculateAllLinesPitchAndYaw();
    }

    //TODO: improve this
    private void recalculateAllLinesPitchAndYaw() {
        for (int i = 0; i < locations.size() - 2; i++) {
            linePitchAndYaw.set(i, calculateLinePitchAndYaw(locations.get(i), locations.get(i + 1)));
        }
    }

    private Double[] calculateLinePitchAndYaw(Location start, Location end) {
        LVMath.subtractToVector(vectorHelper, end, start);

        //i genuinely have no bloody clue why this works. if anyone sees this and understands what the heck this is please tell me im very curious
        //(code stolen from Location.setDirection(Vector) and condensed according to my code conventions)
        double pitch = Math.toDegrees(Math.atan(-vectorHelper.getY() / Math.sqrt(NumberConversions.square(vectorHelper.getX()) + NumberConversions.square(vectorHelper.getZ()))));
        double yaw = Math.toDegrees((Math.atan2(-vectorHelper.getX(), vectorHelper.getZ()) + (Math.PI * 2)) % (Math.PI * 2));

        return new Double[] {Math.abs(pitch), yaw};
    }

    @Override
    public void addLocation(LocationSafe location) {
        super.addLocation(location);

        //this method is called in ParticleLine's constructor, and at that point linePitchAndYaw hasnt been initialized yet. have to do it here
        if (linePitchAndYaw == null) {
            linePitchAndYaw = new ArrayList<>();
        }

        if (locations.size() >= 2) {
            linePitchAndYaw.add(calculateLinePitchAndYaw(locations.get(locations.size() - 2), locations.get(locations.size() - 1)));
        }

        int i = locations.size() - 1;

        location.setMechanic((l) -> {
            if (i != 0) {
                linePitchAndYaw.set(i - 1, calculateLinePitchAndYaw(locations.get(i - 1), l));
            }

            if (locations.size() > i + 1) {
                linePitchAndYaw.set(i, calculateLinePitchAndYaw(l, locations.get(i + 1)));
            }
        });
    }

    public void addCurve(CurveInfo curve) {
        curves.add(curve);
    }

    public CurveInfo getCurve(int index) {
        return curves.get(index);
    }

    @Override
    public void removeLocation(int index) {
        super.removeLocation(index);
        linePitchAndYaw.remove(index);

        if (locations.size() != index) {
            linePitchAndYaw.set(index - 1, calculateLinePitchAndYaw(locations.get(index - 1), locations.get(index)));
        }
    }

    public void removeCurve(int index) {
        curves.remove(index);
    }
}