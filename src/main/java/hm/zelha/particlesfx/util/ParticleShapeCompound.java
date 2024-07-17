package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.RotationHandler;
import hm.zelha.particlesfx.shapers.parents.Shape;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

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
    public Shape start() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.start();
        }

        return this;
    }

    @Override
    public Shape stop() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.stop();
        }

        return this;
    }

    @Override
    public void display() {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.display();
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
    public void scale(double x, double y, double z) {
        super.scale(x, y, z);

        //some shapes have extra code in their scale methods, we need to run that without modifying the locations twice

        for (Shape shape : shapeLocationIndex.keySet()) {
            double[][] coordinateArray = new double[shape.getLocationAmount()][3];
            int i = 0;

            for (Location l : shape.getLocations()) {
                coordinateArray[i][0] = l.getX();
                coordinateArray[i][1] = l.getY();
                coordinateArray[i][2] = l.getZ();
                i++;
            }

            i = 0;

            shape.scale(x, y, z);

            for (Location l : shape.getLocations()) {
                l.zero().add(coordinateArray[i][0], coordinateArray[i][1], coordinateArray[i][2]);
                i++;
            }
        }
    }

    @Override
    public void addMechanic(ShapeDisplayMechanic.Phase phase, ShapeDisplayMechanic mechanic) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.addMechanic(phase, mechanic);
        }
    }

    @Override
    public void addPlayer(Player player) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.addPlayer(player);
        }
    }

    @Override
    public void addPlayer(UUID uuid) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.addPlayer(uuid);
        }
    }

    @Override
    public void removeMechanic(int index) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.removeMechanic(index);
        }
    }

    @Override
    public void removePlayer(int index) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.removePlayer(index);
        }
    }

    @Override
    public void removePlayer(Player player) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.removePlayer(player);
        }
    }

    @Override
    public void setParticle(Particle particle) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setParticle(particle);
        }
    }

    @Override
    public void setParticleFrequency(int particleFrequency) {
        int totalFrequency = 0;

        for (Shape shape : shapeLocationIndex.keySet()) {
            totalFrequency += shape.getParticleFrequency();
        }

        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setParticleFrequency((int) Math.max(Math.round(shape.getParticleFrequency() * ((double) particleFrequency / totalFrequency)), 2));
        }
    }

    @Override
    public Shape setParticlesPerDisplay(int particlesPerDisplay) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setParticlesPerDisplay(particlesPerDisplay);
        }

        return this;
    }

    @Override
    public Shape setDelay(int delay) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setDelay(delay);
        }

        return this;
    }

    @Override
    public void setDisplayPosition(int position) {
        for (Shape shape : shapeLocationIndex.keySet()) {
            shape.setDisplayPosition(position);
        }
    }

    @Override
    @Deprecated
    public Particle getParticle() {
        return null;
    }

    @Override
    public int getParticleFrequency() {
        int totalFrequency = 0;

        for (Shape shape : shapeLocationIndex.keySet()) {
            totalFrequency += shape.getParticleFrequency();
        }

        return totalFrequency;
    }

    @Override
    public int getParticlesPerDisplay() {
        int particles = 0;

        for (Shape shape : shapeLocationIndex.keySet()) {
            if (particles < shape.getParticlesPerDisplay()) {
                particles = shape.getParticlesPerDisplay();
            }
        }

        return particles;
    }

    @Override
    public List<UUID> getPlayers() {
        List<UUID> players = new ArrayList<>();

        for (Shape shape : shapeLocationIndex.keySet()) {
            for (UUID uuid : shape.getPlayers()) {
                if (!players.contains(uuid)) {
                    players.add(uuid);
                }
            }
        }

        return players;
    }

    @Override
    public int getPlayerAmount() {
        return getPlayers().size();
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

        return recalc;
    }

    /**
     * Adds the given shape to this ParticleShapeCompound under the given name, so that you can easily access the shape via
     * {@link ParticleShapeCompound#getShape(String)}
     *
     * @param shape shape to add
     * @param name name of shape
     */
    public void addShape(Shape shape, String name) {
        for (String string : nameMap.keySet()) {
            Validate.isTrue(!string.equals(name), "Shapes can't have the same name!");
        }

        nameMap.put(name, shape);
        addShape(shape);
    }

    public void addShape(Shape shape) {
        Validate.notNull(shape, "Shape cannot be null!");

        for (Shape mapShape : shapeLocationIndex.keySet()) {
            Validate.isTrue(shape != mapShape, "ParticleShapeCompounds can't hold the same shape twice!");
        }

        ArrayListSafe<LocationSafe> locations = getLocationsList((RotationHandler) shape);

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
                    if (last != null) {
                        index += shapeLocationIndex.get(last);
                    }

                    this.locations.remove(index);
                    origins.remove(index);

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

        addCompound(this, (RotationHandler) shape);

        if (getPitch() + getYaw() + getRoll() + getAroundPitch() + getAroundYaw() + getAroundRoll() != 0) {
            recalc = true;
        }
    }

    /**
     * Removes the shape with the given name from this ParticleShapeCompound. will do nothing if there are no shapes with the given name
     * 
     * @param name name of the shape to remove
     */
    public void removeShape(String name) {
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
        ArrayListSafe<LocationSafe> locations = getLocationsList((RotationHandler) shape);
        int firstIndex = (index > 0) ? shapeLocationIndex.get(shapes[index - 1]) + 1 : 0;

        if (locations == null) return;

        locations.removeMechanics(this);
        shapeLocationIndex.remove(shape);
        removeCompound(this, (RotationHandler) shape);

        for (LocationSafe l : locations) {
            l.removeRecalcMechanic(this);
            this.locations.remove(firstIndex);
            origins.remove(firstIndex);
        }

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

    /**
     * @param name name of shape, as added via {@link ParticleShapeCompound#addShape(Shape, String)}
     * @return the shape with the given name
     */
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