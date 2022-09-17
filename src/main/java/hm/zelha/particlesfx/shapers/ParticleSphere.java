package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticleSphere extends ParticleShaper {

    private final List<Double> listHelper = new ArrayList<>();
    private Location center;
    private double xRadius;
    private double yRadius;
    private double zRadius;
    private double circleFrequency;
    private double totalArea = 0;
    private boolean recalculate = true;

    public ParticleSphere(Particle particle, Location center, double xRadius, double yRadius, double zRadius, double pitch, double yaw, double roll, int circleFrequency, double particleFrequency, int particlesPerDisplay) {
        super(particle, pitch, yaw, roll, particleFrequency, particlesPerDisplay);

        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= particleFrequency, "You can't have more circles than particles!");
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");
        locationHelper.setWorld(center.getWorld());
        rot2.addOrigins(center);

        this.center = center;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.zRadius = zRadius;
        this.circleFrequency = circleFrequency;
    }

    @Override
    public void display() {
        int current = 0;
        double continuation = 0;

        if (recalculate) {
            listHelper.clear();

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

                listHelper.add(circumference);
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
            double increase = (Math.PI * 2) / Math.floor(frequency * (listHelper.get(current) / totalArea));

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
                locationHelper.zero().add(center);
                getCurrentParticle().display(locationHelper.add(vectorHelper));
            }

            if (i == Math.PI) break;

            current++;
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);
        rot2.apply(around, Collections.singletonList(center));
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        rot.add(pitch, yaw, roll);
    }

    @Override
    public void move(double x, double y, double z) {
        center.add(new Vector(x, y, z));
    }

    @Override
    public void face(Location toFace) {
        double xDiff = toFace.getX() - center.getX();
        double yDiff = toFace.getY() - center.getY();
        double zDiff = toFace.getZ() - center.getZ();
        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY));

        if (zDiff < 0.0D) yaw += Math.abs(180.0D - yaw) * 2.0D;

        setPitch(pitch);
        setYaw(yaw - 90);
    }

    public void setCenter(Location center) {
        this.center = center;
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

    public void setPitch(double pitch) {
        rot.setPitch(pitch);
    }

    public void setYaw(double yaw) {
        rot.setYaw(yaw);
    }

    public void setRoll(double roll) {
        rot.setRoll(roll);
    }

    public void setCircleFrequency(double circleFrequency) {
        Validate.isTrue(circleFrequency >= 3, "You cant have a sphere with only 2 points!");
        Validate.isTrue(circleFrequency <= frequency, "You can't have more circles than particles!");

        this.circleFrequency = circleFrequency;
        recalculate = true;
    }

    public Location getCenter() {
        return center;
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

    public double getPitch() {
        return rot.getPitch();
    }

    public double getYaw() {
        return rot.getYaw();
    }

    public double getRoll() {
        return rot.getRoll();
    }

    public double getCircleFrequency() {
        return circleFrequency;
    }
}






















//        @Override
//    public void display() {
//        int total = 0;
//        int current = 0;
//        double totalV = 0;
//
//        for (double i = 0; true; i += Math.PI / (circleFrequency - 1)) {
//            if (i > Math.PI - 3.5897932384626433832795028841972e-9) i = Math.PI;
//
//            //starts at 0, goes to 1 at half of Math.PI, then goes back down to 0 at Math.PI
//            //idk what to call the variables surrounding this but that explains it right?
//            listHelper.add(((i <= Math.PI * 0.5) ? i / (Math.PI * 0.5) : (((Math.PI * 0.5) - (i - (Math.PI * 0.5))) / (Math.PI * 0.5))));
//
//            if (i == Math.PI) break;
//        }
//
//        for (int i = 0; i < listHelper.size(); i++) totalV += listHelper.get(i);
//        for (int i = 0; i < listHelper.size(); i++) listHelper.set(i, listHelper.get(i) / (totalV / 2));
//
//        for (double i = 0; true; i += Math.PI / (circleFrequency - 1)) {
//            //i <3 double inconsistency
//            if (i > Math.PI - 3.5897932384626433832795028841972e-9) i = Math.PI;
//
//            double curve = Math.sin(i);
//            double increase = (Math.PI * 2) / Math.round(frequency * (listHelper.get(current) * 0.5));
//
//            if (!Double.isFinite(increase)) increase = Math.PI * 2;
//
//            System.out.println(increase);
//
//            for (double radian = 0; radian < Math.PI * 2; radian += increase) {//freq is inconsistent because this starts at 0
//                vectorHelper.setX(Math.cos(radian) * (xRadius * curve));
//                vectorHelper.setY(yRadius * Math.cos(i));
//                vectorHelper.setZ(Math.sin(radian) * (zRadius * curve));
//                rot.apply(vectorHelper);
//                locationHelper.zero().add(center);
//                getCurrentParticle().display(locationHelper.add(vectorHelper));
//
//                total++;
//            }
//
//            if (i == Math.PI) break;
//
//            current++;
//        }
//
//        listHelper.clear();
//        System.out.println(total);
//    }