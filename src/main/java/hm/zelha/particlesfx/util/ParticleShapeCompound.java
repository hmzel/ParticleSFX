package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.shapers.parents.RotationHandler;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParticleShapeCompound extends RotationHandler {

    private final Map<RotationHandler, Integer> shapeLocationIndex = new LinkedHashMap<>();
    private boolean recalc = false;

    public ParticleShapeCompound(RotationHandler... shapes) {
        for (int i = 0; i < shapes.length; i++) {
            addShape(shapes[i]);
        }
    }

    public void start() {
        for (RotationHandler shape : shapeLocationIndex.keySet()) {
            if (shape instanceof ParticleShapeCompound) {
                ((ParticleShapeCompound) shape).start();
            }

            if (shape instanceof ParticleShaper) {
                ((ParticleShaper) shape).start();
            }
        }
    }

    public void stop() {
        for (RotationHandler shape : shapeLocationIndex.keySet()) {
            if (shape instanceof ParticleShapeCompound) {
                ((ParticleShapeCompound) shape).stop();
            }

            if (shape instanceof ParticleShaper) {
                ((ParticleShaper) shape).stop();
            }
        }
    }

    public void display() {
        for (RotationHandler shape : shapeLocationIndex.keySet()) {
            if (shape instanceof ParticleShapeCompound) {
                ((ParticleShapeCompound) shape).display();
            }

            if (shape instanceof ParticleShaper) {
                ((ParticleShaper) shape).display();
            }
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        for (RotationHandler shape : shapeLocationIndex.keySet()) {
            if (shape.getLocationAmount() == 1) {
                shape.rotate(pitch, yaw, roll);
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
            if (!around.equals(lastRotatedAround)) {
                lastRotatedAround.zero().add(around);
            }

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
        try {
            Field f = RotationHandler.class.getDeclaredField("locations");

            f.setAccessible(true);

            return (ArrayListSafe<LocationS>) f.get(shape);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getLogger().severe("Something went wrong getting shape's locations, this should never happen");
            e.printStackTrace();

            return null;
        }
    }

    public void addShape(RotationHandler shape) {
        Validate.notNull(shape, "Shape cannot be null!");

        for (RotationHandler mapShape : shapeLocationIndex.keySet()) {
            Validate.isTrue(shape != mapShape, "ParticleShapeCompounds can't hold the same shape twice!");
        }

        ArrayListSafe<LocationS> locations = reflectLocations(shape);

        if (locations == null) return;

        if (!this.locations.isEmpty()) {
            Validate.isTrue(locations.get(0).getWorld().equals(this.locations.get(0).getWorld()), "Locations cannot have differing worlds!");
        } else {
            setWorld(locations.get(0).getWorld());
        }

        if (shapeLocationIndex.isEmpty()) {
            shapeLocationIndex.put(shape, shape.getLocationAmount() - 1);
        } else {
            RotationHandler last = (RotationHandler) shapeLocationIndex.keySet().toArray()[shapeLocationIndex.size() - 1];

            shapeLocationIndex.put(shape, shapeLocationIndex.get(last) + shape.getLocationAmount());
        }

        locations.addAddMechanics(this, (owner, added) -> {
            boolean locationAdded = false;

            for (Map.Entry<RotationHandler, Integer> entry : shapeLocationIndex.entrySet()) {
                if (owner == entry.getKey()) {
                    this.locations.add(entry.getValue(), added);
                    this.origins.add(entry.getValue(), added.cloneToLocation());

                    locationAdded = true;
                }

                if (locationAdded) entry.setValue(entry.getValue() + 1);
            }
        });

        locations.addRemoveMechanics(this, (owner, index) -> {
            RotationHandler last = null;
            boolean locationRemoved = false;

            for (Map.Entry<RotationHandler, Integer> entry : shapeLocationIndex.entrySet()) {
                if (owner == entry.getKey()) {
                    //adding index to the last pair's index to get the index of the locations in the list
                    int removeIndex = shapeLocationIndex.get(last) + index;

                    this.locations.remove(removeIndex);
                    origins.remove(removeIndex);

                    locationRemoved = true;
                }

                if (locationRemoved) entry.setValue(entry.getValue() - 1);

                last = entry.getKey();
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
        RotationHandler[] arrayKeySet = shapeLocationIndex.keySet().toArray(new RotationHandler[0]);
        int locAmount = arrayKeySet[index].getLocationAmount();
        int firstIndex;

        if (locations == null) return;

        if (index > 0) {
            //getting the location index of the last shape and adding 1
            firstIndex = shapeLocationIndex.get(arrayKeySet[index - 1]) + 1;
        } else {
            firstIndex = 0;
        }

        locations.removeMechanics(this);

        for (LocationS l : locations) {
            l.removeRecalcMechanic(this);
            this.locations.remove(firstIndex);
            origins.remove(firstIndex);
        }

        shapeLocationIndex.remove(arrayKeySet[index]);

        for (int i = index; i < shapeLocationIndex.size(); i++) {
            //subtracting locAmount from all shape location indexes at 'index' and beyond
            shapeLocationIndex.put(arrayKeySet[i], shapeLocationIndex.get(arrayKeySet[i]) - locAmount);
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    public RotationHandler getShape(int index) {
        return (RotationHandler) shapeLocationIndex.keySet().toArray()[index];
    }

    public int getShapeAmount() {
        return shapeLocationIndex.size();
    }
}
