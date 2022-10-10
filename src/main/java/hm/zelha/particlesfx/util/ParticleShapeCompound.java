package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.shapers.parents.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ParticleShapeCompound extends RotationHandler {

    //List<Pair<>> is honestly just easier to use in this situation
    private final List<Pair<RotationHandler, Integer>> shapeLocationIndex = new ArrayList<>();
    private boolean recalc = false;

    public ParticleShapeCompound(RotationHandler... shapes) {
        for (int i = 0; i < shapes.length; i++) addShape(shapes[i]);
    }

    public void start() {
        for (int i = 0; i < shapeLocationIndex.size(); i++) {
            RotationHandler shape = shapeLocationIndex.get(i).getKey();

            if (shape instanceof ParticleShapeCompound) ((ParticleShapeCompound) shape).start();
            if (shape instanceof ParticleShaper) ((ParticleShaper) shape).start();
        }
    }

    public void stop() {
        for (int i = 0; i < shapeLocationIndex.size(); i++) {
            RotationHandler shape = shapeLocationIndex.get(i).getKey();

            if (shape instanceof ParticleShapeCompound) ((ParticleShapeCompound) shape).stop();
            if (shape instanceof ParticleShaper) ((ParticleShaper) shape).stop();
        }
    }

    public void display() {
        for (int i = 0; i < shapeLocationIndex.size(); i++) {
            RotationHandler shape = shapeLocationIndex.get(i).getKey();

            if (shape instanceof ParticleShapeCompound) ((ParticleShapeCompound) shape).display();
            if (shape instanceof ParticleShaper) ((ParticleShaper) shape).display();
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        for (int i = 0; i < shapeLocationIndex.size(); i++) {
            if (shapeLocationIndex.get(i).getKey().getLocationAmount() == 1) {
                shapeLocationIndex.get(i).getKey().rotate(pitch, yaw, roll);
            }
        }

        if (recalc) recalculateAllOrigins();

        rot.add(pitch, yaw, roll);
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //set vectorHelper to origin - centroid, apply rotation to vectorHelper, set location to centroid + vectorHelper
            LVMath.additionToLocation2(locations.get(i), centroid, rot.apply(LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid)));
        }
    }

    @Override
    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll) {
        Validate.isTrue(around.getWorld().equals(centroid.getWorld()), "Cant rotate around locations in different worlds!");

        lastRotatedAround.setPitch(around.getPitch());
        lastRotatedAround.setYaw(around.getYaw());

        if (recalc) {
            if (!around.equals(lastRotatedAround)) lastRotatedAround.zero().add(around);

            recalculateAllOrigins();
        }

        if (!around.equals(lastRotatedAround)) {
            lastRotatedAround.zero().add(around);
            recalcOriginCentroid();
        }

        rot2.add(pitch, yaw, roll);

        //getting distance between original centroid and around, rotating it, and adding it to around to get the genuine location
        LVMath.additionToLocation(rhLocationHelper, around, rot2.apply(LVMath.subtractToVector(rhVectorHelper, originalCentroid, around)));
        calculateCentroid(origins);

        for (int i = 0; i < locations.size(); i++) {
            //getting the distance between the centroid and the location, adding that to the rotated centroid, and setting that as the location
            LVMath.additionToLocation2(locations.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid));
            LVMath.additionToLocation2(origins.get(i), rhLocationHelper, LVMath.subtractToVector(rhVectorHelper, origins.get(i), centroid));
        }
    }

    /**
     * wondering why im using reflection? laziness! <p></p>
     *
     * i REALLY, *REALLY* do not want to make a List class that can handle everything the end user
     * can do without breaking everything. <p></p>
     *
     * i might later. at some point. we'll see. but im already going insane enough trying to make this spawn of hell.
     */
    private ArrayListSafe<LocationS> reflectLocations(RotationHandler shape) {
        ArrayListSafe<LocationS> list = null;

        try {
            Field f = RotationHandler.class.getDeclaredField("locations");

            f.setAccessible(true);

            list = (ArrayListSafe<LocationS>) f.get(shape);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Validate.notNull(list, "Something went wrong getting locations!");

        return list;
    }

    public void addShape(RotationHandler shape) {
        Validate.notNull(shape, "Shape cannot be null!");

        for (Pair<RotationHandler, Integer> pair : shapeLocationIndex) {
            Validate.isTrue(shape != pair.getKey(), "ParticleShapeCompounds can't hold the same shape twice!");
        }

        ArrayListSafe<LocationS> locations = reflectLocations(shape);

        if (!this.locations.isEmpty()) {
            Validate.isTrue(locations.get(0).getWorld().equals(this.locations.get(0).getWorld()), "Locations cannot have differing worlds!");
        } else {
            setWorld(locations.get(0).getWorld());
        }

        if (shapeLocationIndex.isEmpty()) {
            shapeLocationIndex.add(new MutablePair<>(shape, shape.getLocationAmount() - 1));
        } else {
            shapeLocationIndex.add(new MutablePair<>(shape, shapeLocationIndex.get(shapeLocationIndex.size() - 1).getValue() + shape.getLocationAmount()));
        }

        locations.addAddMechanics(this, (owner, added) -> {
            boolean locationAdded = false;

            for (Pair<RotationHandler, Integer> pair : shapeLocationIndex) {
                if (owner == pair.getKey()) {
                    this.locations.add(pair.getValue(), added);
                    this.origins.add(pair.getValue(), added.cloneToLocation());

                    locationAdded = true;
                }

                if (locationAdded) pair.setValue(pair.getValue() + 1);
            }
        });

        locations.addRemoveMechanics(this, (owner, index) -> {
            boolean locationRemoved = false;

            for (Pair<RotationHandler, Integer> pair : shapeLocationIndex) {
                if (owner == pair.getKey()) {
                    //adding index to the last pair's index to get the index of the locations in the list
                    int removeIndex = shapeLocationIndex.get(shapeLocationIndex.lastIndexOf(pair) - 1).getValue() + index;

                    this.locations.remove(removeIndex);
                    origins.remove(removeIndex);

                    locationRemoved = true;
                }

                if (locationRemoved) pair.setValue(pair.getValue() - 1);
            }
        });

        for (LocationS l : locations) {
            l.addRecalcMechanic(this, (location) -> recalc = true);
            this.locations.add(l);
            origins.add(l.cloneToLocation());
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    public void removeShape(int index) {
        ArrayListSafe<LocationS> locations = reflectLocations(getShape(index));
        int locAmount = getShape(index).getLocationAmount();
        int firstIndex;

        if (index > 0) firstIndex = shapeLocationIndex.get(index - 1).getValue() + 1; else firstIndex = 0;

        locations.removeMechanics(this);

        for (int i = firstIndex; i <= shapeLocationIndex.get(index).getValue(); i++) {
            locations.get(i).removeRecalcMechanic(this);
            this.locations.remove(firstIndex);
            origins.remove(firstIndex);
        }

        shapeLocationIndex.remove(index);

        for (int i = index; i < shapeLocationIndex.size(); i++) {
            shapeLocationIndex.get(i).setValue(shapeLocationIndex.get(i).getValue() - locAmount);
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    public RotationHandler getShape(int index) {
        return shapeLocationIndex.get(index).getKey();
    }

    public int getShapeAmount() {
        return shapeLocationIndex.size();
    }
}
