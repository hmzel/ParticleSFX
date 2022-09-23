package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RotationHandler {

    protected final List<LocationS> locations = new ArrayList<>();
    protected final List<Location> origins = new ArrayList<>();
    protected final List<Location> aroundOrigins = new ArrayList<>();
    protected final Rotation rot = new Rotation();
    protected final Rotation rot2 = new Rotation();
    private final Location lastRotatedAround = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Location centroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private final Vector vectorHelper = new Vector(0, 0, 0);
    private final double[] arrayHelper = new double[] {0, 0};

    public void rotate(double pitch, double yaw, double roll) {
        rot.add(pitch, yaw, roll);

        if (locations.size() == 1) return;

        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).isChanged()) {
                recalculateAllOrigins();
                break;
            }
        }

        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to origin - centroid, apply rotation to vectorHelper, set location to centroid + vectorHelper
            LVMath.additionToLocation(locations.get(i), centroid, rot.apply(LVMath.subtractToVector(vectorHelper, origins.get(i), centroid)));
        }
    }

    //TODO: make non-circular shapes mimic circular shape behaviour when rotating around a location
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

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to around origin - around, apply rotation to vectorHelper, set location to around + vectorHelper
            LVMath.additionToLocation(locations.get(i), around, rot2.apply(LVMath.subtractToVector(vectorHelper, aroundOrigins.get(i), around)));
        }

        if (locations.size() == 1) return;

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to around origin - around, apply rotation to vectorHelper, set origin to around + vectorHelper
            LVMath.additionToLocation(origins.get(i), around, rot2.apply(LVMath.subtractToVector(vectorHelper, aroundOrigins.get(i), around)));
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
        //inverse the rotation for rot2, apply that to rot's origins using the last rotated around location,
        //and set that as the origins for rot2
        Rotation rotHelper = LocationS.getRotHelper();

        rotHelper.set(-rot2.getPitch(), -rot2.getYaw(), -rot2.getRoll());

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setChanged(false);

            Vector v = LVMath.subtractToVector(vectorHelper, ((locations.size() != 1) ? origins.get(i) : locations.get(i)), lastRotatedAround);

            rotHelper.applyRoll(v);
            rotHelper.applyYaw(v);
            rotHelper.applyPitch(v);

            LVMath.additionToLocation(aroundOrigins.get(i), lastRotatedAround, v);
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

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);

            if (origins.size() > i) origins.get(i).setWorld(world);

            aroundOrigins.get(i).setWorld(world);
        }
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

        return  dist;
    }
}
