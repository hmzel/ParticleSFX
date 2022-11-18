package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticleSphere extends ParticleShaper {

    //TODO: see if theres any way to improve particlesPerDisplay in this class
    // maybe make this extend ParticleCircle at some point? that makes sense but doesnt at the same time so idrk. decide later

    //circumference tracker
    private final List<Double> cirTracker = new ArrayList<>();
    private int circleFrequency;
    private double xRadius;
    private double yRadius;
    private double zRadius;
    private double limit = 0;
    private double totalArea = 0;
    private boolean limitInverse = false;
    private boolean recalculate = true;

    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, double particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setCircleFrequency(circleFrequency);
        rot.set(pitch, yaw, roll);

        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.zRadius = zRadius;

        start();
    }

    public ParticleSphere(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int circleFrequency, double particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, circleFrequency, particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int circleFrequency, double particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationSafe center, double radius, int circleFrequency, double particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, (int) (particleFrequency / 20), particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationSafe center, double radius, double particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (particleFrequency / 20), particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (radius * 75 / 20), radius * 75);
    }

    @Override
    public void display() {
        int currentCir = 0;
        double continuation = 0;
        double limitation = Math.PI * limit / 100;
        //the long decimal number is used to cut PI to the 29th decimal place to prevent some double weirdness that i dont even understand
        double loopEndFix = Math.PI - 3.5897932384626433832795028841972e-9;
        double loopStart = limitation;
        double loopEnd = Math.PI;
        double increase = (Math.PI - limitation) / (circleFrequency - 1);
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        double current = overallCount;

        if (limitInverse) {
            loopEnd -= limitation;
            //cutting loopEnd down to the 29th decimal. reason already stated
            loopEndFix = loopEnd - ((loopEnd) - (((int) ((loopEnd) * 29)) / 29D));
            loopStart = 0;
        }

        if (recalculate) {
            recalcCircumferenceAndArea(loopEndFix, loopStart, loopEnd, increase);
        }

        main:
        for (double i = loopStart; true; i += increase) {
            if (i > loopEndFix) {
                i = loopEnd;
            }

            double curve = Math.sin(i);
            double circleInc = (Math.PI * 2) / Math.floor(particleFrequency * (cirTracker.get(currentCir) / totalArea));

            if (!Double.isFinite(circleInc)) {
                circleInc = Math.PI * 2;
            }

            for (double radian = continuation; true; radian += circleInc) {
                if (radian > (Math.PI * 2) + continuation) {
                    continuation = radian - Math.PI * 2;
                    break;
                }

                if (trackCount && current != 0) {
                    current--;
                    continue;
                }

                Particle particle = getCurrentParticle();

                locationHelper.zero().add(locations.get(0));
                vectorHelper.setX(Math.cos(radian) * (xRadius * curve));
                vectorHelper.setY(yRadius * Math.cos(i));
                vectorHelper.setZ(Math.sin(radian) * (zRadius * curve));
                applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
                rot.apply(vectorHelper);
                applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper);
                locationHelper.add(vectorHelper);

                if (!players.isEmpty()) {
                    particle.displayForPlayers(locationHelper, players);
                } else {
                    particle.display(locationHelper);
                }

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

            if (i == loopEnd) break;

            currentCir++;
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public ParticleSphere clone() {
        ParticleSphere clone = new ParticleSphere(particle, locations.get(0).clone(), xRadius, yRadius, zRadius, getPitch(), getYaw(), getRoll(), circleFrequency, particleFrequency);

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

    private void recalcCircumferenceAndArea(double loopEndFix, double loopStart, double loopEnd, double increase) {
        cirTracker.clear();

        totalArea = 0;

        for (double i = loopStart; true; i += increase) {
            if (i > loopEndFix) {
                i = loopEnd;
            }

            double curve = Math.sin(i);
            double circumference;

            if (xRadius == zRadius) {
                circumference = Math.PI * 2 * Math.abs(xRadius * curve);
            } else {
                double x = Math.abs(xRadius * curve);
                double z = Math.abs(zRadius * curve);

                circumference = Math.PI * 2 * Math.sqrt((Math.pow(x, 2) + Math.pow(z, 2)) / 2);
            }

            cirTracker.add(circumference);
            totalArea += circumference;

            if (i == loopEnd) {
                recalculate = false;
                break;
            }
        }
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.add(center);
        setWorld(center.getWorld());
        originalCentroid.zero().add(center);
        center.setChanged(true);

        if (locations.size() > 1) {
            locations.remove(0);
        }
    }

    public void setxRadius(double xRadius) {
        this.xRadius = xRadius;
        recalculate = true;
    }

    public void setyRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    public void setzRadius(double zRadius) {
        this.zRadius = zRadius;
        recalculate = true;
    }

    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    /**
     * @param limit percentage of the sphere that should generate, such that 0 would be the entire sphere and 50 would be a half sphere.
     */
    public void setLimit(double limit) {
        Validate.isTrue(limit >= 0 && limit <= 100, "Limit is meant to be a percentage, and cannot be below 0 or above 100");

        this.limit = limit;
        recalculate = true;
    }

    /**
     * @param limitInverse determines if the limit cuts off the top or the bottom. default false (top)
     */
    public void setLimitInverse(boolean limitInverse) {
        this.limitInverse = limitInverse;
    }

    public Location getCenter() {
        return locations.get(0);
    }

    public double getxRadius() {
        return xRadius;
    }

    public double getyRadius() {
        return yRadius;
    }

    public double getzRadius() {
        return zRadius;
    }

    public int getCircleFrequency() {
        return circleFrequency;
    }

    /**
     * @return percentage of the sphere that should generate, such that 0 would be the entire sphere and 50 would be a half sphere.
     */
    public double getLimit() {
        return limit;
    }

    /**
     * @return if the limit cuts off the top or the bottom. default false (top)
     */
    public boolean isLimitInverse() {
        return limitInverse;
    }
}