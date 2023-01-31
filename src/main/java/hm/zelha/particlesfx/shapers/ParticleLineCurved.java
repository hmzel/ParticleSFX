package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.*;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleLineCurved extends ParticleLine {

    protected final List<CurveInfo> curves = new ArrayList<>();
    protected final Rotation rot3 = new Rotation();
    protected final Vector vectorHelper2 = new Vector();
    protected boolean rotateCurves = true;

    public ParticleLineCurved(Particle particle, int particleFrequency, LocationSafe... locations) {
        super(particle, particleFrequency, locations);
    }

    public ParticleLineCurved(Particle particle, LocationSafe... locations) {
        this(particle, 100, locations);
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

            if (current != 0) {
                locationHelper.add(vectorHelper.multiply(current));
                vectorHelper.multiply(1D / current);
            }

            for (int k = current; k < particleAmount; k++) {
                Particle particle = getCurrentParticle();
                curveCurrent += control;

                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);
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
                    double height = curve.getHeight();

                    if (curveCurrent <= curveApex) {
                        vectorHelper2.setY(height * Math.sin(Math.PI / 2 * curveCurrent / curveApex));
                    } else {
                        vectorHelper2.setY(height * Math.sin((Math.PI / 2) - (Math.PI / 2 * (curveCurrent - curveApex) / (curveEnd - curveApex))));
                    }

                    rot3.set(curve.getPitch(), curve.getYaw(), curve.getRoll());
                    rot3.apply(vectorHelper2);
                    applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper2);
                    locationHelper.add(vectorHelper2);
                }

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

                overallCount++;

                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);
                locationHelper.subtract(vectorHelper2);

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
    public void rotate(double pitch, double yaw, double roll) {
        super.rotate(pitch, yaw, roll);

        if (rotateCurves) {
            for (CurveInfo curve : curves) {
                curve.setPitch(curve.getPitch() + pitch);
                curve.setYaw(curve.getYaw() + yaw);
                curve.setRoll(curve.getRoll() + roll);
            }
        }
    }

    @Override
    public ParticleLineCurved clone() {
        LocationSafe[] locations = new LocationSafe[this.locations.size()];

        for (int i = 0; i < getLocationAmount(); i++) {
            locations[i] = this.locations.get(i).clone();
        }

        ParticleLineCurved clone = new ParticleLineCurved(particle, particleFrequency, locations);

        for (CurveInfo curve : curves) {
            clone.addCurve(curve.clone());
        }

        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setRotateCurves(rotateCurves);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    public void addCurve(CurveInfo curve) {
        curves.add(curve);
    }

    public void removeCurve(int index) {
        curves.remove(index);
    }

    /**
     * @param rotateCurves whether curves are rotated with the shape, defaults to true
     */
    public void setRotateCurves(boolean rotateCurves) {
        this.rotateCurves = rotateCurves;
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