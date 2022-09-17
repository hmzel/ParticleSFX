package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.CircleInfo;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleCylinder extends ParticleShaper {

    private final List<CircleInfo> circles = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final Vector vectorHelper2 = new Vector(0, 0, 0);
    private final Location locationHelper2 = new Location(null, 0, 0, 0);
    private final CircleInfo circleHelper;
    private int circleFrequency;

    public ParticleCylinder(Particle particle, int circleFrequency, double particleFrequency, int particlesPerDisplay, CircleInfo... circles) {
        super(particle, 0, 0, 0, particleFrequency, particlesPerDisplay);

        Validate.isTrue(circles != null && circles.length >= 2, "Array must contain 2 or more CircleInfos!");
        Validate.isTrue(circleFrequency > 1, "circleFrequency must be greater than 1! if you only want one circle, use ParticleCircle");

        World world = circles[0].getCenter().getWorld();

        for (CircleInfo circle : circles) {
            Validate.notNull(circle, "Circles cant be null!");
            Validate.isTrue(circle.getCenter().getWorld().equals(world), "Circle's worlds must be the same!");
            this.circles.add(circle);
            locations.add(circle.getCenter());
            rot.addOrigins(circle.getCenter());
            rot2.addOrigins(circle.getCenter());
        }

        locationHelper.setWorld(world);
        locationHelper2.setWorld(world);

        this.circleHelper = circles[0].clone();
        this.circleFrequency = circleFrequency;
    }

    public ParticleCylinder(Particle particle, int circleFrequency, double particleFrequency, CircleInfo... circles) {
        this(particle, circleFrequency, particleFrequency, 0, circles);
    }

    public ParticleCylinder(Particle particle, int particlesPerDisplay, CircleInfo... circles) {
        this(particle, 100, 0, particlesPerDisplay, circles);
    }

    public ParticleCylinder(Particle particle, CircleInfo... circles) {
        this(particle, 100, 0, circles);
    }

    @Override
    public void display() {
        double increase = (Math.PI * 2) / (frequency / circleFrequency);
        double totalDist = 0;
        double distToTravel = 0;

        circleHelper.inherit(circles.get(0));

        //adding the distance between every circle to totalDist
        for (int i = 0; i < locations.size() - 1; i++) totalDist += locations.get(i).distance(locations.get(i + 1));

        for (int i = 0, circle = 0; i < circleFrequency; i++) {
            CircleInfo circle1 = circles.get(circle);
            CircleInfo circle2 = circles.get(circle + 1);
            RotationHandler rot = circleHelper.getRotationHandler();
            double distance = circleHelper.getCenter().distance(circle2.getCenter());

            while (distToTravel > distance) {
                //i think this only happens because of double inconsistency so its fine to just ignore
                //in every case where this breaks the while loop the final circle winds up exactly where it should be,
                //anything off has to be in the thousandths of a decimal
                if (circle + 2 >= circles.size()) break;

                distToTravel -= distance;
                circle++;
                circle1 = circles.get(circle);
                circle2 = circles.get(circle + 1);

                circleHelper.inherit(circle1);

                distance = circleHelper.getCenter().distance(circle2.getCenter());
            }

            //basically just (distToTravel * distance) but with NaN prevention
            double incControl = (distToTravel * ((Double.isFinite((1 / distance))) ? (1 / distance) : 0));
            //using Math.abs() because it looks wonky in cases where the rotation is negative
            double pitchInc = (circle1.getPitch() - circle2.getPitch()) * incControl;
            double yawInc = (circle1.getYaw() - circle2.getYaw()) * incControl;
            double rollInc = (circle1.getRoll() - circle2.getRoll()) * incControl;
            double xRadiusInc = (circle2.getXRadius() - circle1.getXRadius()) * incControl;
            double zRadiusInc = (circle2.getZRadius() - circle1.getZRadius()) * incControl;

            //setting vectorHelper to (end - start) / (absolute vector sum / distToTravel)
            LVMath.divide(LVMath.subtractToVector(vectorHelper, circle2.getCenter(), circle1.getCenter()), (Math.abs(vectorHelper.getX()) + Math.abs(vectorHelper.getY()) + Math.abs(vectorHelper.getZ())) * ((Double.isFinite((1 / distToTravel))) ? (1 / distToTravel) : 0));
            circleHelper.getCenter().add(vectorHelper);
            circleHelper.setXRadius(circleHelper.getXRadius() + xRadiusInc);
            circleHelper.setZRadius(circleHelper.getZRadius() + zRadiusInc);
            rot.add(pitchInc, yawInc, rollInc);
            locationHelper.zero().add(circleHelper.getCenter());

            for (double radian = 0; radian < Math.PI * 2; radian += increase) {
                //setting vectorHelper2 to where the current particle should be in correlation to the current circle's center (locationHelper)
                rot.apply(vectorHelper2.setX(circleHelper.getXRadius() * Math.cos(radian)).setY(0).setZ(circleHelper.getZRadius() * Math.sin(radian)));
                getCurrentParticle().display(locationHelper.add(vectorHelper2));
                locationHelper.subtract(vectorHelper2);
            }

            distToTravel = totalDist / (circleFrequency - 1);
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        rot2.add(pitch, yaw, roll);
        rot2.apply(around, locations);
        rot2.apply(around, rot.getOrigins());

        //TODO: figure out a way to rotate circlehelper and vectorhelper along with everything else to prevent weirdness
        // probably after the rotationhandler fix
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        Location centroid = locationHelper2.zero();

        for (int i = 0; i < circles.size(); i++) centroid.add(rot.getOrigins().get(i));

        centroid.multiply(1D / circles.size());
        rot.add(pitch, yaw, roll);
        rot.apply(centroid, locations);
    }

    @Override
    public void move(double x, double y, double z) {
        rot.moveOrigins(x, y, z);
        rot2.moveOrigins(x, y, z);

        for (Location l : locations) l.add(x, y, z);
    }

    @Override
    public void face(Location toFace) {
        Location centroid = locationHelper2.zero();

        for (int i = 0; i < circles.size(); i++) centroid.add(rot.getOrigins().get(i));

        centroid.multiply(1D / circles.size());

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
    }

    public void addCircle(CircleInfo circle) {
        Validate.notNull(circle, "Circles cant be null!");
        Validate.isTrue(circle.getCenter().getWorld().equals(circles.get(0).getCenter().getWorld()), "Circle's worlds must be the same!");
        circles.add(circle);
        locations.add(circle.getCenter());
        rot.addOrigins(circle.getCenter());
        rot2.addOrigins(circle.getCenter());
    }

    public void removeCircle(int index) {
        circles.remove(index);
        locations.remove(index);
        rot.removeOrigin(index);
        rot2.removeOrigin(index);
    }

    public void setCircleFrequency(int circleFrequency) {
        this.circleFrequency = circleFrequency;
    }

    public CircleInfo getCircle(int index) {
        return circles.get(index);
    }

    public double getCircleFrequency() {
        return circleFrequency;
    }
}