package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.ArrayListSafe;
import hm.zelha.particlesfx.util.LVMath;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.Rotation;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RotationHandler {

    protected final List<LocationSafe> locations = new ArrayListSafe<>(this);
    protected final List<Location> origins = new ArrayList<>();
    protected final Rotation rot = new Rotation();
    protected final Rotation rot2 = new Rotation();
    protected final Location originalCentroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Location lastRotatedAround = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Location centroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    //rh stands for rotation handler
    protected final Location rhLocationHelper = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Vector rhVectorHelper = new Vector(0, 0, 0);
    private final double[] arrayHelper = new double[] {0, 0};

    public void rotate(double pitch, double yaw, double roll) {
        if (locations.size() == 1) {
            rot.add(pitch, yaw, roll);
            return;
        }

        recalculateIfNeeded(null);
        rot.add(pitch, yaw, roll);
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to origin - centroid, apply rotation to vectorHelper, set location to centroid + vectorHelper
            LVMath.additionToLocation(locations.get(i), centroid, rot.apply(LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid)));
        }
    }

    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        Validate.isTrue(around.getWorld().equals(centroid.getWorld()), "Cant rotate around locations in different worlds!");

        recalculateIfNeeded(around);
        rot2.add(pitch, yaw, roll);

        if (locations.size() == 1) {
            //literally just rotating the one location this line makes it seem more complicated than it is
            LVMath.additionToLocation(locations.get(0), around, rot2.apply(LVMath.subtractToVector(rhVectorHelper, originalCentroid, around)));
            return;
        }

        //getting distance between original centroid and around, rotating it, and adding it to around to get the genuine location
        LVMath.additionToLocation(rhLocationHelper, around, rot2.apply(LVMath.subtractToVector(rhVectorHelper, originalCentroid, around)));
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //getting the distance between the centroid and the location, adding that to the rotated centroid, and setting that as the location
            LVMath.additionToLocation(locations.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid));
            LVMath.additionToLocation(origins.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid));
        }
    }

    public void face(Location toFace) {
        if (locations.size() == 1) centroid.zero().add(locations.get(0)); else calculateCentroid(origins);

        double[] direction = getDirection(toFace, centroid);

        rotate(direction[0] - rot.getPitch(), direction[1] - rot.getYaw(), 0);
    }

    public void faceAroundLocation(Location toFace, Location around) {
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

    protected void recalculateIfNeeded(@Nullable Location around) {
        boolean recalculate = false;
        boolean aroundHasChanged = false;

        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).isChanged()) {
                recalculate = true;

                locations.get(i).setChanged(false);
            }
        }

        if (around != null) {
            lastRotatedAround.setPitch(around.getPitch());
            lastRotatedAround.setYaw(around.getYaw());

            if (!around.equals(lastRotatedAround)) {
                aroundHasChanged = true;

                lastRotatedAround.zero().add(around);
            }
        }

        if (locations.size() != 1 && recalculate) {
            //need to set both origins to what they would be if the rotation was 0, 0, 0
            //aka, inverse the current rotation for rot, apply it to all locations, and set that as the origin for rot
            //then we inverse the rotation for rot2, apply that to rot's origins using the last rotated around location,
            //and set that as the origins for rot2
            Rotation rotHelper = LocationSafe.getRotHelper();

            rotHelper.set(-rot.getPitch(), -rot.getYaw(), -rot.getRoll());
            calculateCentroid(locations);

            for (int i = 0; i < locations.size(); i++) {
                Vector v = LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid);

                rotHelper.applyRoll(v);
                rotHelper.applyYaw(v);
                rotHelper.applyPitch(v);

                LVMath.additionToLocation(origins.get(i), centroid, v);
            }
        }

        if (recalculate || aroundHasChanged) {
            //get origin centroid, set vectorHelper to the distance between centroid and lastRotatedAround, rotate vectorHelper by the
            //inverse of rot2, set originalCentroid to lastRotatedAround + vectorHelper
            Rotation rotHelper = LocationSafe.getRotHelper();

            if (locations.size() == 1) {
                centroid.zero().add(locations.get(0));
            } else {
                calculateCentroid(origins);
            }

            rotHelper.set(-rot2.getPitch(), -rot2.getYaw(), -rot2.getRoll());
            LVMath.subtractToVector(rhVectorHelper, centroid, lastRotatedAround);
            rotHelper.applyRoll(rhVectorHelper);
            rotHelper.applyYaw(rhVectorHelper);
            rotHelper.applyPitch(rhVectorHelper);
            LVMath.additionToLocation(originalCentroid, lastRotatedAround, rhVectorHelper);
        }
    }

    protected void calculateCentroid(List<? extends Location> locations) {
        centroid.zero();

        for (int i = 0; i < locations.size(); i++) centroid.add(locations.get(i));

        centroid.multiply(1d / locations.size());
    }

    private double[] getDirection(Location toFace, Location around) {
        Validate.isTrue(toFace.getWorld().equals(around.getWorld()), "Locations must be in the same world!");

        LVMath.subtractToVector(rhVectorHelper, toFace, around);

        //i genuinely have no bloody clue why this works. if anyone sees this and understands what the heck this is please tell me im very curious
        //(code stolen from Location.setDirection(Vector) and condensed according to my code conventions)
        double pitch = Math.toDegrees(Math.atan(-rhVectorHelper.getY() / Math.sqrt(NumberConversions.square(rhVectorHelper.getX()) + NumberConversions.square(rhVectorHelper.getZ()))));
        double yaw = Math.toDegrees((Math.atan2(-rhVectorHelper.getX(), rhVectorHelper.getZ()) + (Math.PI * 2)) % (Math.PI * 2));

        if (originalCentroid.getY() > around.getY()) pitch += 90; else pitch -= 90;

        arrayHelper[0] = pitch;
        arrayHelper[1] = yaw;

        return arrayHelper;
    }

    public void setWorld(World world) {
        lastRotatedAround.setWorld(world);
        centroid.setWorld(world);
        rhLocationHelper.setWorld(world);
        originalCentroid.setWorld(world);

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);

            if (origins.size() > i) origins.get(i).setWorld(world);
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

    public Location[] getLocations() {
        return locations.toArray(new Location[0]);
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
