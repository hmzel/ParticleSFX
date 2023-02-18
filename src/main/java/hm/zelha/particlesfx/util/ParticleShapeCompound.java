package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.RotationHandler;
import hm.zelha.particlesfx.shapers.parents.Shape;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class ParticleShapeCompound extends RotationHandler implements Shape {

    protected final Map<Shape, Integer> shapeLocationIndex = new LinkedHashMap<>();
    protected final Map<String, Shape> nameMap = new HashMap<>();
    protected boolean recalc = false;

    public ParticleShapeCompound(Shape... shapes) {
        for (Shape shape : shapes) {
            addShape(shape);
        }
    }

    @Override
    public void start() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.start();
        }
    }

    @Override
    public void stop() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.stop();
        }
    }

    @Override
    public void display() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.display();
        }
    }

    @Override
    public void rotate(double pitch, double yaw, double roll) {
        super.rotate(pitch, yaw, roll);

        for (Shape shape : shapeLocationIndex.keySet()) {
            if (shape.getLocationAmount() == 1) {
                shape.rotate(pitch, yaw, roll);
            }
        }
    }

    @Override
    public ParticleShapeCompound clone() {
        Shape[] shapes = new Shape[shapeLocationIndex.size()];

        int i = 0;

        for (Shape shape : shapeLocationIndex.keySet()) {
            shapes[i] = shape.clone();
            i++;
        }

        ParticleShapeCompound clone = new ParticleShapeCompound(shapes);

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);

        return clone;
    }

    @Override
    public void setDelay(int delay) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setDelay(delay);
        }
    }

    @Override
    public void setDisplayPosition(int position) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setDisplayPosition(position);
        }
    }

    @Override
    protected boolean recalculateIfNeeded(@Nullable Location around) {
        boolean aroundHasChanged = false;

        if (around != null) {
            lastRotatedAround.setPitch(around.getPitch());
            lastRotatedAround.setYaw(around.getYaw());

            if (!around.equals(lastRotatedAround)) {
                aroundHasChanged = true;

                lastRotatedAround.zero().add(around);
            }
        }

        if (recalc) {
            //need to set origins to what the locations would be if the shape wasnt rotated
            //aka, inverse the rotation, apply it to all locations, and set that as the origin for rot
            Rotation.Axis[] axes = rot.getRotationOrder();

            rotHelper.set(-rot.getAxisPitch(), -rot.getAxisYaw(), -rot.getAxisRoll());
            rotHelper.setAxis(-rot.getPitch(), -rot.getYaw(), -rot.getRoll());
            rotHelper.setRotationOrder(axes[2], axes[1], axes[0]);
            calculateCentroid(locations);

            for (int i = 0; i < locations.size(); i++) {
                //getting the distance between the centroid and the location, rotating it, adding it to the centroid, and setting that as the origin
                LVMath.additionToLocation(origins.get(i), centroid, rotHelper.apply(LVMath.subtractToVector(rhVectorHelper, locations.get(i), centroid)));
            }
        }

        if (aroundHasChanged) {
            //get origin centroid, set vectorHelper to the distance between centroid and lastRotatedAround, rotate vectorHelper by the
            //inverse of rot2, set originalCentroid to lastRotatedAround + vectorHelper
            Rotation.Axis[] axes = rot2.getRotationOrder();

            rotHelper.set(-rot2.getAxisPitch(), -rot2.getAxisYaw(), -rot2.getAxisRoll());
            rotHelper.setAxis(-rot2.getPitch(), -rot2.getYaw(), -rot2.getRoll());
            rotHelper.setRotationOrder(axes[2], axes[1], axes[0]);
            calculateCentroid(origins);
            LVMath.additionToLocation(originalCentroid, lastRotatedAround, rotHelper.apply(LVMath.subtractToVector(rhVectorHelper, centroid, lastRotatedAround)));
        }

        return recalc;
    }

    /**
     * wondering why im using reflection? laziness! <br><br>
     *
     * i REALLY, *REALLY* do not want to make a List class that can handle everything the end user
     * can do without breaking everything. <br><br>
     *
     * i might later. at some point. we'll see. but im already going insane enough trying to make this spawn of hell.
     */
    protected ArrayListSafe<LocationSafe> reflectLocations(Shape shape) {
        try {
            Field f = RotationHandler.class.getDeclaredField("locations");

            f.setAccessible(true);

            return (ArrayListSafe<LocationSafe>) f.get(shape);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Something went wrong getting shape's locations, this should never happen", ex);

            return null;
        }
    }

    public void addShape(Shape shape, String name) {
        nameMap.put(name, shape);
        addShape(shape);
    }

    public void addShape(Shape shape) {
        Validate.notNull(shape, "Shape cannot be null!");

        for (Shape mapShape : shapeLocationIndex.keySet()) {
            Validate.isTrue(shape != mapShape, "ParticleShapeCompounds can't hold the same shape twice!");
        }

        ArrayListSafe<LocationSafe> locations = reflectLocations(shape);

        if (locations == null) return;

        if (!this.locations.isEmpty()) {
            Validate.isTrue(locations.get(0).getWorld().equals(this.locations.get(0).getWorld()), "Locations cannot have differing worlds!");
        } else {
            setWorld(locations.get(0).getWorld());
        }

        if (shapeLocationIndex.isEmpty()) {
            shapeLocationIndex.put(shape, shape.getLocationAmount() - 1);
        } else {
            Shape last = (Shape) shapeLocationIndex.keySet().toArray()[shapeLocationIndex.size() - 1];

            shapeLocationIndex.put(shape, shapeLocationIndex.get(last) + shape.getLocationAmount());
        }

        locations.addAddMechanics(this, (owner, added) -> {
            boolean locationAdded = false;

            for (Map.Entry<Shape, Integer> entry : shapeLocationIndex.entrySet()) {
                if (owner == entry.getKey()) {
                    this.locations.add(entry.getValue(), added);
                    this.origins.add(entry.getValue(), added.clone());

                    locationAdded = true;
                }

                if (locationAdded) {
                    entry.setValue(entry.getValue() + 1);
                }
            }
        });

        locations.addRemoveMechanics(this, (owner, index) -> {
            Shape last = null;
            boolean locationRemoved = false;

            for (Map.Entry<Shape, Integer> entry : shapeLocationIndex.entrySet()) {
                if (owner == entry.getKey()) {
                    //adding index to the last pair's index to get the index of the locations in the list
                    int removeIndex = shapeLocationIndex.get(last) + index;

                    this.locations.remove(removeIndex);
                    origins.remove(removeIndex);

                    locationRemoved = true;
                }

                if (locationRemoved) {
                    entry.setValue(entry.getValue() - 1);
                }

                last = entry.getKey();
            }
        });

        for (LocationSafe l : locations) {
            l.addRecalcMechanic(this, (location) -> recalc = true);
            this.locations.add(l);
            origins.add(l.clone());
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    public void removeShape(String name) {
        if (!nameMap.containsKey(name)) return;

        Shape shape = nameMap.get(name);
        int i = 0;

        for (Shape mapShape : shapeLocationIndex.keySet()) {
            if (mapShape == shape) {
                removeShape(i);

                return;
            }

            i++;
        }
    }

    public void removeShape(int index) {
        Shape[] shapes = shapeLocationIndex.keySet().toArray(new Shape[0]);
        Shape shape = shapes[index];
        ArrayListSafe<LocationSafe> locations = reflectLocations(shape);
        int firstIndex;

        if (locations == null) return;

        if (index > 0) {
            //getting the location index of the last shape and adding 1
            firstIndex = shapeLocationIndex.get(shapes[index - 1]) + 1;
        } else {
            firstIndex = 0;
        }

        locations.removeMechanics(this);

        for (LocationSafe l : locations) {
            l.removeRecalcMechanic(this);
            this.locations.remove(firstIndex);
            origins.remove(firstIndex);
        }

        shapeLocationIndex.remove(shape);

        for (String string : nameMap.keySet()) {
            if (nameMap.get(string) == shape) {
                nameMap.remove(string);
            }
        }

        for (int i = index; i < shapeLocationIndex.size(); i++) {
            //subtracting locAmount from all shape location indexes at 'index' and beyond
            shapeLocationIndex.put(shapes[i], shapeLocationIndex.get(shapes[i]) - shape.getLocationAmount());
        }

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    public Shape getShape(String name) {
        return nameMap.get(name);
    }

    public Shape getShape(int index) {
        return (Shape) shapeLocationIndex.keySet().toArray()[index];
    }

    public int getShapeAmount() {
        return shapeLocationIndex.size();
    }
}
