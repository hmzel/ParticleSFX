package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang.Validate;
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
    protected final List<ParticleShapeCompound> compounds = new ArrayList<>();
    protected final Rotation rot = new Rotation();
    protected final Rotation rot2 = new Rotation();
    protected final Rotation rotHelper = new Rotation();
    protected final Location originalCentroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Location lastRotatedAround = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Location centroid = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    //rh stands for rotation handler
    protected final Rotation rhRotHelper = new Rotation();
    protected final Location rhLocationHelper = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    protected final Vector rhVectorHelper = new Vector();
    private final double[] arrayHelper = new double[] {0, 0};

    public void rotate(double pitch, double yaw, double roll) {
        recalculateIfNeeded(null);
        rot.add(pitch, yaw, roll);
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to origin - centroid, apply rotation to vectorHelper, set location to centroid + vectorHelper
            LVMath.additionToLocation(locations.get(i), centroid, rot.apply(LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid)));
        }
    }

    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        Validate.isTrue(around.getWorld().equals(getWorld()), "Cant rotate around locations in different worlds!");

        recalculateIfNeeded(around);
        rot2.add(pitch, yaw, roll);
        //getting distance between original centroid and around, rotating it, and adding it to around to get the genuine centroid
        LVMath.additionToLocation(rhLocationHelper, around, rot2.apply(LVMath.subtractToVector(rhVectorHelper, originalCentroid, around)));
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //getting the distance between the centroid and the location, adding that to the rotated centroid, and setting that as the location
            LVMath.additionToLocation(locations.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid));
            LVMath.additionToLocation(origins.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid));
        }
    }

    public void face(Location toFace) {
        //if we don't recalculate now getDirection() may be off if locations were changed by outside influence
        recalculateIfNeeded(null);
        calculateCentroid(origins);

        double[] direction = getDirection(toFace, centroid);

        rotate(direction[0] - rot.getPitch(), direction[1] - rot.getYaw(), 0);
    }

    public void faceAroundLocation(Location toFace, Location around) {
        //if we don't recalculate now getDirection() may be off if locations were changed by outside influence
        recalculateIfNeeded(around);

        double[] direction = getDirection(toFace, around);

        rotateAroundLocation(around, direction[0] - rot2.getPitch(), direction[1] - rot2.getYaw(), 0);
    }

    public void move(double x, double y, double z) {
        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).add(x, y, z);
        }
    }

    public void move(Vector vector) {
        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).add(vector);
        }
    }

    public void move(Location location) {
        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).add(location);
        }
    }

    public void scale(double x, double y, double z) {
        calculateCentroid(locations);

        for (LocationSafe l : locations) {
            LVMath.subtractToVector(rhVectorHelper, l, centroid);
            rhVectorHelper.setX(rhVectorHelper.getX() * x);
            rhVectorHelper.setY(rhVectorHelper.getY() * y);
            rhVectorHelper.setZ(rhVectorHelper.getZ() * z);
            LVMath.additionToLocation(l, centroid, rhVectorHelper);
        }
    }

    public void scale(double scale) {
        scale(scale, scale, scale);
    }

    protected boolean recalculateIfNeeded(@Nullable Location around) {
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

        if (recalculate) {
            //need to set origins to what the locations would be if the shape wasnt rotated
            //aka, inverse the rotation, apply it to all locations, and set that as the origin for rot
            Rotation.Axis[] axes = rot.getRotationOrder();

            rhRotHelper.set(-rot.getAxisPitch(), -rot.getAxisYaw(), -rot.getAxisRoll());
            rhRotHelper.setAxis(-rot.getPitch(), -rot.getYaw(), -rot.getRoll());
            rhRotHelper.setRotationOrder(axes[2], axes[1], axes[0]);
            calculateCentroid(locations);

            for (int i = 0; i < locations.size(); i++) {
                //getting the distance between the centroid and the location, rotating it, adding it to the centroid, and setting that as the origin
                LVMath.additionToLocation(origins.get(i), centroid, rhRotHelper.apply(LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid)));
            }
        }

        if (aroundHasChanged) {
            //get origin centroid, set vectorHelper to the distance between centroid and lastRotatedAround, rotate vectorHelper by the
            //inverse of rot2, set originalCentroid to lastRotatedAround + vectorHelper
            Rotation.Axis[] axes = rot2.getRotationOrder();

            rhRotHelper.set(-rot2.getAxisPitch(), -rot2.getAxisYaw(), -rot2.getAxisRoll());
            rhRotHelper.setAxis(-rot2.getPitch(), -rot2.getYaw(), -rot2.getRoll());
            rhRotHelper.setRotationOrder(axes[2], axes[1], axes[0]);
            calculateCentroid(origins);
            LVMath.additionToLocation(originalCentroid, lastRotatedAround, rhRotHelper.apply(LVMath.subtractToVector(rhVectorHelper, centroid, lastRotatedAround)));
        }

        return recalculate;
    }

    protected double[] getDirection(Location toFace, Location around) {
        Validate.isTrue(toFace.getWorld().equals(around.getWorld()), "Locations must be in the same world!");

        LVMath.subtractToVector(rhVectorHelper, toFace, around);

        //i genuinely have no bloody clue why this works. if anyone sees this and understands what the heck this is please tell me im very curious
        //(code stolen from Location.setDirection(Vector) and condensed according to my code conventions)
        double pitch = Math.toDegrees(Math.atan(-rhVectorHelper.getY() / Math.sqrt(NumberConversions.square(rhVectorHelper.getX()) + NumberConversions.square(rhVectorHelper.getZ()))));
        double yaw = Math.toDegrees((Math.atan2(-rhVectorHelper.getX(), rhVectorHelper.getZ()) + (Math.PI * 2)) % (Math.PI * 2));

        if (originalCentroid.getY() > around.getY()) {
            pitch += 90;
        } else {
            pitch -= 90;
        }

        arrayHelper[0] = pitch;
        arrayHelper[1] = yaw;

        return arrayHelper;
    }

    protected void calculateCentroid(List<? extends Location> locations) {
        centroid.zero();

        for (int i = 0; i < locations.size(); i++) {
            centroid.add(locations.get(i));
        }

        centroid.multiply(1d / locations.size());
    }

    protected void addCompound(ParticleShapeCompound compound, RotationHandler shape) {
        if (shape instanceof ParticleShapeCompound) {
            ParticleShapeCompound c = (ParticleShapeCompound) shape;

            for (int i = 0; i < c.getShapeAmount(); i++) {
                addCompound(compound, (RotationHandler) c.getShape(i));
            }

            return;
        }

        shape.compounds.add(compound);
    }

    protected void removeCompound(ParticleShapeCompound compound, RotationHandler shape) {
        if (shape instanceof ParticleShapeCompound) {
            ParticleShapeCompound c = (ParticleShapeCompound) shape;

            for (int i = 0; i < c.getShapeAmount(); i++) {
                removeCompound(compound, (RotationHandler) c.getShape(i));
            }

            return;
        }

        shape.compounds.remove(compound);
    }

    public void setWorld(World world) {
        lastRotatedAround.setWorld(world);
        centroid.setWorld(world);
        rhLocationHelper.setWorld(world);
        originalCentroid.setWorld(world);

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).setWorld(world);
            origins.get(i).setWorld(world);
        }
    }

    public void setRotation(double pitch, double yaw, double roll) {
        rotate(pitch - rot.getPitch(), yaw - rot.getYaw(), roll - rot.getRoll());
    }

    public void setRotationOrder(Rotation.Axis first, Rotation.Axis second, Rotation.Axis third) {
        recalculateIfNeeded(null);
        rot.setRotationOrder(first, second, third);
        rotate(0, 0, 0);
    }

    public void setPitch(double pitch) {
        rotate(pitch - rot.getPitch(), 0, 0);
    }

    public void setYaw(double yaw) {
        rotate(0, yaw - rot.getYaw(), 0);
    }

    public void setRoll(double roll) {
        rotate(0, 0, roll - rot.getRoll());
    }

    public void setAroundRotation(Location around, double pitch, double yaw, double roll) {
        rotateAroundLocation(around, pitch - rot2.getPitch(), yaw - rot2.getYaw(), roll - rot2.getRoll());
    }

    public void setAroundRotationOrder(Location around, Rotation.Axis first, Rotation.Axis second, Rotation.Axis third) {
        recalculateIfNeeded(around);
        rot2.setRotationOrder(first, second, third);
        rotateAroundLocation(around, 0, 0, 0);
    }

    public void setAroundPitch(Location around, double pitch) {
        rotateAroundLocation(around, pitch - rot2.getPitch(), 0, 0);
    }

    public void setAroundYaw(Location around, double yaw) {
        rotateAroundLocation(around, 0, yaw - rot2.getYaw(), 0);
    }

    public void setAroundRoll(Location around, double roll) {
        rotateAroundLocation(around, 0, 0, roll - rot2.getRoll());
    }

    public void setAxisRotation(double pitch, double yaw, double roll) {
        recalculateIfNeeded(null);
        rot.setAxis(pitch, yaw, roll);
        rotate(0, 0, 0);
    }

    public void setAxisPitch(double pitch) {
        recalculateIfNeeded(null);
        rot.setAxisPitch(pitch);
        rotate(0, 0, 0);
    }

    public void setAxisYaw(double yaw) {
        recalculateIfNeeded(null);
        rot.setAxisYaw(yaw);
        rotate(0, 0, 0);
    }

    public void setAxisRoll(double roll) {
        recalculateIfNeeded(null);
        rot.setAxisRoll(roll);
        rotate(0, 0, 0);
    }

    public void setAroundAxisRotation(Location around, double pitch, double yaw, double roll) {
        recalculateIfNeeded(around);
        rot2.setAxis(pitch, yaw, roll);
        rotateAroundLocation(around, 0, 0, 0);
    }

    public void setAroundAxisPitch(Location around, double pitch) {
        recalculateIfNeeded(around);
        rot2.setAxisPitch(pitch);
        rotateAroundLocation(around, 0, 0, 0);
    }

    public void setAroundAxisYaw(Location around, double yaw) {
        recalculateIfNeeded(around);
        rot2.setAxisYaw(yaw);
        rotateAroundLocation(around, 0, 0, 0);
    }

    public void setAroundAxisRoll(Location around, double roll) {
        recalculateIfNeeded(around);
        rot2.setAxisRoll(roll);
        rotateAroundLocation(around, 0, 0, 0);
    }

    public World getWorld() {
        return rhLocationHelper.getWorld();
    }

    public Rotation.Axis[] getRotationOrder() {
        return rot.getRotationOrder().clone();
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

    public Rotation.Axis[] getAroundRotationOrder() {
        return rot2.getRotationOrder().clone();
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

    public double getAxisPitch() {
        return rot.getAxisPitch();
    }

    public double getAxisYaw() {
        return rot.getAxisYaw();
    }

    public double getAxisRoll() {
        return rot.getAxisRoll();
    }

    public double getAroundAxisPitch() {
        return rot2.getAxisPitch();
    }

    public double getAroundAxisYaw() {
        return rot2.getAxisYaw();
    }

    public double getAroundAxisRoll() {
        return rot2.getAxisRoll();
    }

    public Location[] getLocations() {
        return locations.toArray(new Location[0]);
    }

    public int getLocationAmount() {
        return locations.size();
    }

    public double getTotalDistance() {
        double dist = 0;

        for (int i = 0; i < locations.size() - 1; i++) {
            dist += locations.get(i).distance(locations.get(i + 1));
        }

        return dist;
    }

    public Location getClonedCenter() {
        Location l = new Location(getWorld(), 0, 0, 0);

        for (int i = 0; i < locations.size(); i++) {
            l.add(locations.get(i));
        }

        return l.multiply(1d / locations.size());
    }
}
