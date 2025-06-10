package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCurved extends ParticleLine {

    protected final List<CurveInfo> curves = new ArrayList<>();
    protected final Vector vectorHelper2 = new Vector();
    protected boolean rotateCurves = true;

    public ParticleLineCurved(Particle particle, int particleFrequency, LocationSafe... locations) {
        super(particle, particleFrequency, locations);
    }

    public ParticleLineCurved(Particle particle, LocationSafe... locations) {
        this(particle, locations.length * 50, locations);
    }

    @Override
    public void display() {
        CurveInfo curve = null;
        double control = getTotalDistance() / particleFrequency;
        double curveCurrent = control * overallCount;
        double curveApex = 0, curveEnd = 0;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int current = overallCount;
        int curveIndex = -1;

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
            locationHelper.add(vectorHelper.getX() * current, vectorHelper.getY() * current, vectorHelper.getZ() * current);

            for (int k = current; k < particleAmount; k++) {
                Particle particle = getCurrentParticle();

                if (overallCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY_FULL, particle, locationHelper, vectorHelper);
                if (currentCount == 0) applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_DISPLAY, particle, locationHelper, vectorHelper);

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                vectorHelper2.zero();

                while (curveCurrent >= curveEnd) {
                    if (curveIndex + 1 >= curves.size()) {
                        curve = null;

                        break;
                    }

                    curveIndex++;
                    curve = curves.get(curveIndex);
                    curveCurrent -= curveEnd;
                    curveApex = curve.getApexPosition();
                    curveEnd = curve.getLength();
                }

                if (curve != null && curve.getHeight() != 0) {
                    if (curveCurrent <= curveApex) {
                        vectorHelper2.setY(curve.getHeight() * Math.sin(Math.PI / 2 * curveCurrent / curveApex));
                    } else {
                        vectorHelper2.setY(curve.getHeight() * Math.sin((Math.PI / 2) - (Math.PI / 2 * (curveCurrent - curveApex) / (curveEnd - curveApex))));
                    }

                    rotHelper.set(curve.getPitch(), curve.getYaw(), curve.getRoll());
                    rotHelper.apply(vectorHelper2);

                    if (rotateCurves) {
                        rot.apply(vectorHelper2);

                        for (ParticleShapeCompound compound : compounds) {
                            rotHelper.inherit(compound).apply(vectorHelper2);
                        }
                    }

                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper2);
                    locationHelper.add(vectorHelper2);
                }

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

                overallCount++;
                currentCount++;
                curveCurrent += control;

                locationHelper.add(vectorHelper);
                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_PARTICLE, particle, locationHelper, vectorHelper);
                locationHelper.subtract(vectorHelper2);

                if (trackCount && currentCount >= particlesPerDisplay) {
                    currentCount = 0;
                    hasRan = true;

                    break main;
                }
            }

            current = 0;
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
    public ParticleLineCurved clone() {
        ParticleLineCurved clone = (ParticleLineCurved) super.clone();

        clone.setRotateCurves(rotateCurves);

        for (CurveInfo curve : curves) {
            clone.addCurve(curve.clone());
        }

        return clone;
    }

    @Override
    protected ParticleShaper cloneConstructor() {
        LocationSafe[] locations = new LocationSafe[this.locations.size()];

        for (int i = 0; i < getLocationAmount(); i++) {
            locations[i] = this.locations.get(i).clone();
        }

        return new ParticleLineCurved(particle, particleFrequency, locations);
    }

    @Override
    public void scale(double x, double y, double z) {
        double currentDist = getTotalDistance();

        super.scale(x, y, z);

        double newDist = getTotalDistance();

        for (CurveInfo curve : curves) {
            curve.setLength(curve.getLength() * newDist / currentDist);
            curve.setHeight(curve.getHeight() * newDist / currentDist);
            curve.setApexPosition(curve.getApexPosition() * newDist / currentDist);
        }
    }

    public void addCurve(int index, CurveInfo curve) {
        curves.add(index, curve);
    }

    public void addCurve(CurveInfo curve) {
        addCurve(curves.size(), curve);
    }

    public void removeCurve(int index) {
        curves.remove(index);
    }

    /**
     * @param rotateCurves whether curves are rotated with the shape, defaults to true
     */
    public ParticleLineCurved setRotateCurves(boolean rotateCurves) {
        this.rotateCurves = rotateCurves;

        return this;
    }

    /**
     * @return whether curves are rotated with the shape, defaults to true
     */
    public boolean isRotatingCurves() {
        return rotateCurves;
    }

    public CurveInfo getCurve(int index) {
        return curves.get(index);
    }

    public int getCurveAmount() {
        return curves.size();
    }
}