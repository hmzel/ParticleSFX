package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationS;
import hm.zelha.particlesfx.util.Rotation;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RotationHandler {

    //TODO: replace aroundOrigins with originalCentroid (one location)

    protected final List<LocationS> locations = new ArrayList<>();
    protected final List<Location> origins = new ArrayList<>();
    protected final List<Location> aroundOrigins = new ArrayList<>();
    protected final Rotation rot = new Rotation();
    protected final Rotation rot2 = new Rotation();
    private final Location lastRotatedAround = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Location centroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Location locationHelper = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Vector vectorHelper = new Vector(0, 0, 0);
    private final double[] arrayHelper = new double[] {0, 0};

    public void rotate(double pitch, double yaw, double roll) {
        if (locations.size() == 1) {
            rot.add(pitch, yaw, roll);
            return;
        }

        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).isChanged()) {
                recalculateAllOrigins();
                break;
            }
        }

        rot.add(pitch, yaw, roll);
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to origin - centroid, apply rotation to vectorHelper, set location to centroid + vectorHelper
            LVMath.additionToLocation(locations.get(i), centroid, rot.apply(LVMath.subtractToVector(vectorHelper, origins.get(i), centroid)));
        }
    }

    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        Validate.isTrue(around.getWorld().equals(centroid.getWorld()), "Cant rotate around locations in different worlds!");

        lastRotatedAround.setPitch(around.getPitch());
        lastRotatedAround.setYaw(around.getYaw());

        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).isChanged()) {
                if (!around.equals(lastRotatedAround)) lastRotatedAround.zero().add(around);
                if (locations.size() != 1) recalculateAllOrigins(); else recalculateAroundOrigins();
                break;
            }
        }

        if (!around.equals(lastRotatedAround)) {
            lastRotatedAround.zero().add(around);
            recalculateAroundOrigins();
        }

        rot2.add(pitch, yaw, roll);

        if (locations.size() == 1) {
            //literally just rotating the one location this line makes it seem more complicated than it is
            LVMath.additionToLocation(locations.get(0), around, rot2.apply(LVMath.subtractToVector(vectorHelper, aroundOrigins.get(0), around)));
            return;
        }

        calculateCentroid(aroundOrigins);
        //getting distance between origin centroid and around, rotating it, and adding it to around to get the genuine location
        LVMath.additionToLocation(locationHelper, around, rot2.apply(LVMath.subtractToVector(vectorHelper, centroid, around)));
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //getting the distance between the centroid and the location, adding that to the rotated centroid, and setting that as the location
            LVMath.additionToLocation(locations.get(i), locationHelper, LVMath.subtractToVector(vectorHelper, locations.get(i), centroid));
            LVMath.additionToLocation(origins.get(i), locationHelper, LVMath.subtractToVector(vectorHelper, origins.get(i), centroid));
        }
    }

    public void face(Location toFace) {
        if (locations.size() == 1) centroid.zero().add(locations.get(0)); else calculateCentroid(origins);

        //best way to avoid duplicate code
        double[] direction = getDirection(toFace, centroid);

        rotate(direction[0] - rot.getPitch(), direction[1] - rot.getYaw(), 0);
    }

    public void faceAroundLocation(Location toFace, Location around) {
        //best way to avoid duplicate code
        double[] direction = getDirection(toFace, around);

        rotateAroundLocation(around, direction[0] - rot2.getPitch(), direction[1] - rot2.getYaw(), 0);
    }

    public void move(double x, double y, double z) {
        for (int i = 0; i < locations.size(); i++) locations.get(i).add(x, y, z);
    }

    public void move(Vector vector) {
        for (int i = 0; i < locations.size(); i++) locations.get(i).add(vector);
    }

    public void move(Location location) {
        for (int i = 0; i < locations.size(); i++) locations.get(i).add(location);
    }

    private double[] getDirection(Location toFace, Location around) {
        Validate.isTrue(toFace.getWorld().equals(around.getWorld()), "Locations must be in the same world!");

        LVMath.subtractToVector(vectorHelper, toFace, around);

        //i genuinely have no bloody clue why this works. if anyone sees this and understands what the heck this is please tell me im very curious
        //(code stolen from Location.setDirection(Vector) and condensed according to my code conventions)
        double pitch = Math.toDegrees(Math.atan(-vectorHelper.getY() / Math.sqrt(NumberConversions.square(vectorHelper.getX()) + NumberConversions.square(vectorHelper.getZ()))));
        double yaw = Math.toDegrees((Math.atan2(-vectorHelper.getX(), vectorHelper.getZ()) + (Math.PI * 2)) % (Math.PI * 2));

        if (around.equals(centroid)) {
            pitch -= 90;
        } else {
            calculateCentroid(aroundOrigins);

            if (centroid.getY() > around.getY()) pitch += 90; else pitch -= 90;
        }

        arrayHelper[0] = pitch;
        arrayHelper[1] = yaw;

        return arrayHelper;
    }

    private void recalculateAllOrigins() {
        //need to set both origins to what they would be if the rotation was 0, 0, 0
        //aka, inverse the current rotation for rot, apply it to all locations, and set that as the origin for rot
        //then we inverse the rotation for rot2, apply that to rot's origins using the last rotated around location,
        //and set that as the origins for rot2
        Rotation rotHelper = LocationS.getRotHelper();

        rotHelper.set(-rot.getPitch(), -rot.getYaw(), -rot.getRoll());
        calculateCentroid(locations);

        for (int i = 0; i < locations.size(); i++) {
            Vector v = LVMath.subtractToVector(vectorHelper, locations.get(i), centroid);

            rotHelper.applyRoll(v);
            rotHelper.applyYaw(v);
            rotHelper.applyPitch(v);

            LVMath.additionToLocation(origins.get(i), centroid, v);
        }

        recalculateAroundOrigins();
    }

    private void recalculateAroundOrigins() {
        //get origin centroid, set vectorHelper to the distance between centroid and lastRotatedAround, rotate vectorHelper by the
        //inverse of rot2, set locationHelper to lastRotatedAround + vectorHelper to get the genuine rotated centroid, set all aroundOrigins to
        //the rotated centroid + the distance between the origin centroid and origin locations
        Rotation rotHelper = LocationS.getRotHelper();

        if (locations.size() == 1) centroid.zero().add(locations.get(0)); else calculateCentroid(origins);

        rotHelper.set(-rot2.getPitch(), -rot2.getYaw(), -rot2.getRoll());
        LVMath.subtractToVector(vectorHelper, centroid, lastRotatedAround);
        rotHelper.applyRoll(vectorHelper);
        rotHelper.applyYaw(vectorHelper);
        rotHelper.applyPitch(vectorHelper);
        LVMath.additionToLocation(locationHelper, lastRotatedAround, vectorHelper);

        for (int i = 0; i < locations.size(); i++) {
            Location origin = (locations.size() == 1) ? locations.get(0) : origins.get(i);

            locations.get(i).setChanged(false);
            //getting the distance between the centroid and the location, adding that to the rotated centroid, and setting that as the location
            LVMath.additionToLocation(aroundOrigins.get(i), locationHelper, LVMath.subtractToVector(vectorHelper, origin, centroid));
        }
    }

    private void calculateCentroid(List<? extends Location> locations) {
        centroid.zero();

        for (int i = 0; i < locations.size(); i++) centroid.add(locations.get(i));

        centroid.multiply(1d / locations.size());
    }

    public void setWorld(World world) {
        lastRotatedAround.setWorld(world);
        centroid.setWorld(world);
        locationHelper.setWorld(world);

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);

            if (origins.size() > i) origins.get(i).setWorld(world);

            aroundOrigins.get(i).setWorld(world);
        }
    }

    public void setRotation(double pitch, double yaw, double roll) {
        rot.set(pitch, yaw, roll);
        rotate(0, 0, 0);
    }

    public void setPitch(double pitch) {
        rot.setPitch(pitch);
        rotate(0, 0, 0);
    }

    public void setYaw(double yaw) {
        rot.setYaw(yaw);
        rotate(0, 0, 0);
    }

    public void setRoll(double roll) {
        rot.setRoll(roll);
        rotate(0, 0, 0);
    }

    public void setAroundRotation(double pitch, double yaw, double roll) {
        rot2.set(pitch, yaw, roll);
        rotateAroundLocation(lastRotatedAround, 0, 0, 0);
    }

    public void setAroundPitch(double pitch) {
        rot2.setPitch(pitch);
        rotateAroundLocation(lastRotatedAround, 0, 0, 0);
    }

    public void setAroundYaw(double yaw) {
        rot2.setYaw(yaw);
        rotateAroundLocation(lastRotatedAround, 0, 0, 0);
    }

    public void setAroundRoll(double roll) {
        rot2.setRoll(roll);
        rotateAroundLocation(lastRotatedAround, 0, 0, 0);
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

    public double getAroundPitch() {
        return rot2.getPitch();
    }

    public double getAroundYaw() {
        return rot2.getYaw();
    }

    public double getAroundRoll() {
        return rot2.getRoll();
    }

    public int getLocationAmount() {
        return locations.size();
    }

    public double getTotalDistance() {
        double dist = 0;

        //adding the distance between every circle to dist
        for (int i = 0; i < locations.size() - 1; i++) dist += locations.get(i).distance(locations.get(i + 1));

        return dist;
    }

    public Location getClonedCenter() {
        Location l = new Location(centroid.getWorld(), 0, 0, 0);

        for (int i = 0; i < locations.size(); i++) l.add(locations.get(i));

        return l.multiply(1d / locations.size());
    }
}
