package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses surface area and circumference to position particles, as opposed to {@link ParticleSphereSFSA} which uses the
 * the <a href="https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42">Sunflower Seed Arrangement</a>
 * <br><br>
 * This class's sphere looks worse, but is much less chaotic and easier to color with secondary particles.
 */
public class ParticleSphere extends ParticleSphereSFSA {

    //TODO: see if theres any way to improve particlesPerDisplay in this class
    // maybe make this extend ParticleCircle at some point? that makes sense but doesnt at the same time so idrk. decide later

    //circumference tracker
    protected final List<Double> cirTracker = new ArrayList<>();
    protected int circleFrequency;
    protected double surfaceArea = 0;
    protected boolean recalculate = true;

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        super(particle, center, xRadius, yRadius, zRadius, pitch, yaw, roll, particleFrequency);

        setCircleFrequency(circleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius, double pitch, double yaw, double roll, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, pitch, yaw, roll, circleFrequency, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int circleFrequency, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius, int circleFrequency, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double xRadius, double yRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, particleFrequency / 20, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        this(particle, center, radius, radius, radius, 0, 0, 0, particleFrequency / 20, particleFrequency);
    }

    /**@see ParticleSphere*/
    public ParticleSphere(Particle particle, LocationSafe center, double radius) {
        this(particle, center, radius, radius, radius, 0, 0, 0, (int) (radius * 75 / 20), (int) (radius * 75));
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
            double circleInc = (Math.PI * 2) / Math.floor(particleFrequency * (cirTracker.get(currentCir) / surfaceArea));

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

        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setLimit(limit);
        clone.setLimitInverse(limitInverse);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    protected void recalcCircumferenceAndArea(double loopEndFix, double loopStart, double loopEnd, double increase) {
        cirTracker.clear();

        surfaceArea = 0;

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
            surfaceArea += circumference;

            if (i == loopEnd) {
                recalculate = false;
                break;
            }
        }
    }

    public void setxRadius(double xRadius) {
        super.setxRadius(xRadius);

        recalculate = true;
    }

    public void setzRadius(double zRadius) {
        super.setzRadius(zRadius);

        recalculate = true;
    }

    public void setCircleFrequency(int circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    public void setLimit(double limit) {
        super.setLimit(limit);

        recalculate = true;
    }

    public int getCircleFrequency() {
        return circleFrequency;
    }
}