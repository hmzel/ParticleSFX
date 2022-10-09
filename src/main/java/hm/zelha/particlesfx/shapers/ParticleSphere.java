package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationS;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ParticleSphere extends ParticleShaper {

    //maybe make this extend ParticleCircle at some point? that makes sense but doesnt at the same time so idrk. decide later

    //circumference tracker
    private final List<Double> cirTracker = new ArrayList<>();
    private double xRadius;
    private double yRadius;
    private double zRadius;
    private double circleFrequency;
    private double totalArea = 0;
    private boolean recalculate = true;

    public ParticleSphere(Particle particle, LocationS center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, double particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setCircleFrequency(circleFrequency);
        rot.set(pitch, yaw, roll);

        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.zRadius = zRadius;

        start();
    }

    public ParticleSphere(Particle particle, LocationS center, double xRadius, double yRadius, double zRadius, int circleFrequency, double particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, circleFrequency, particleFrequency);
    }

    public ParticleSphere(Particle particle, LocationS center, double xRadius, double yRadius, double zRadius, double particleFrequency) {
        this(particle, center, xRadius, yRadius, zRadius, 0, 0, 0, (int) (particleFrequency / 20), particleFrequency);
    }

    @Override
    public void display() {
        int current = 0;
        double continuation = 0;

        if (recalculate) {
            cirTracker.clear();

            totalArea = 0;

            for (double i = 0; true; i += Math.PI / (circleFrequency - 1)) {
                //the long decimal number is used to cut PI to the 29th decimal place to prevent some weirdness that i dont even understand
                if (i > Math.PI - 3.5897932384626433832795028841972e-9) i = Math.PI;

                double curve = Math.sin(i);
                double circumference;

                if (xRadius == zRadius) {
                    circumference = Math.PI * 2 * (xRadius * curve);
                } else {
                    double x = xRadius * curve;
                    double z = zRadius * curve;

                    circumference = Math.PI * 2 * Math.sqrt((Math.pow(x, 2) + Math.pow(z, 2)) / 2);
                }

                cirTracker.add(circumference);
                totalArea += circumference;

                if (i == Math.PI) {
                    recalculate = false;
                    break;
                }
            }
        }

        for (double i = 0; true; i += Math.PI / (circleFrequency - 1)) {
            if (i > Math.PI - 3.5897932384626433832795028841972e-9) i = Math.PI;

            double curve = Math.sin(i);
            double increase = (Math.PI * 2) / Math.floor(particleFrequency * (cirTracker.get(current) / totalArea));

            if (!Double.isFinite(increase)) increase = Math.PI * 2;

            for (double radian = continuation; true; radian += increase) {
                if (radian > (Math.PI * 2) + continuation) {
                    continuation = radian - Math.PI * 2;
                    break;
                }

                vectorHelper.setX(Math.cos(radian) * (xRadius * curve));
                vectorHelper.setY(yRadius * Math.cos(i));
                vectorHelper.setZ(Math.sin(radian) * (zRadius * curve));
                rot.apply(vectorHelper);
                locationHelper.zero().add(locations.get(0));
                getCurrentParticle().display(locationHelper.add(vectorHelper));
            }

            if (i == Math.PI) break;

            current++;
        }
    }

    public void setCenter(LocationS center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.clear();
        locations.add(center);
        originalCentroid.zero().add(center);
        setWorld(center.getWorld());
        center.setChanged(true);
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

    public void setCircleFrequency(double circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
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

    public double getCircleFrequency() {
        return circleFrequency;
    }
}