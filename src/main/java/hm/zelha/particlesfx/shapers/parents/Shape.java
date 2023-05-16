package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.Rotation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public interface Shape {

    /**
     * @return this object
     */
    Shape start();

    /**
     * @return this object
     */
    Shape stop();

    void display();

    void rotate(double pitch, double yaw, double roll);

    void rotateAroundLocation(Location around, double pitch, double yaw, double roll);

    void face(Location toFace);

    void faceAroundLocation(Location toFace, Location around);

    /**
     * adds the given x, y, z values to every location in the shape
     *
     * @param x x addition
     * @param y y addition
     * @param z z addition
     */
    void move(double x, double y, double z);

    /**
     * adds the given vector to every location in the shape
     *
     * @param vector vector to add
     */
    void move(Vector vector);

    /**
     * adds the given location to every location in the shape
     *
     * @param location location to add
     */
    void move(Location location);

    /**
     * Rescales this shape such that a shape with the xyz radiuses of 4, 3, 4, resized by 1.25, 1.25, 1.25, would have its
     * radiuses become 5, 3.75, 5.
     *
     * @param x x scale
     * @param y y scale
     * @param z z scale
     */
    void scale(double x, double y, double z);

    /**
     * Rescales this shape such that a shape with the xyz radiuses of 4, 3, 4, resized by 1.25, would have its
     * radiuses become 5, 3.75, 5.
     *
     * @param scale scale amount
     */
    void scale(double scale);

    Shape clone();

    /**
     * @param delay amount of ticks between {@link ParticleShaper#display()} being called
     */
    void setDelay(int delay);

    /**
     * sets the current position of the shape's animation <br>
     * (aka, sets the tracker that tells the shape how many particles have been displayed until this point) <br>
     *
     * @param position position for shape to display at
     */
    void setDisplayPosition(int position);

    void setWorld(World world);

    void setRotation(double pitch, double yaw, double roll);

    void setRotationOrder(Rotation.Axis first, Rotation.Axis second, Rotation.Axis third);

    void setPitch(double pitch);

    void setYaw(double yaw);

    void setRoll(double roll);

    void setAroundRotation(Location around, double pitch, double yaw, double roll);

    void setAroundRotationOrder(Location around, Rotation.Axis first, Rotation.Axis second, Rotation.Axis third);

    void setAroundPitch(Location around, double pitch);

    void setAroundYaw(Location around, double yaw);

    void setAroundRoll(Location around, double roll);

    void setAxisRotation(double pitch, double yaw, double roll);

    void setAxisPitch(double pitch);

    void setAxisYaw(double yaw);

    void setAxisRoll(double roll);

    void setAroundAxisRotation(Location around, double pitch, double yaw, double roll);

    void setAroundAxisPitch(Location around, double pitch);

    void setAroundAxisYaw(Location around, double yaw);

    void setAroundAxisRoll(Location around, double roll);

    World getWorld();

    Rotation.Axis[] getRotationOrder();

    double getPitch();

    double getYaw();

    double getRoll();

    Rotation.Axis[] getAroundRotationOrder();

    double getAroundPitch();

    double getAroundYaw();

    double getAroundRoll();

    double getAxisPitch();

    double getAxisYaw();

    double getAxisRoll();

    double getAroundAxisPitch();

    double getAroundAxisYaw();

    double getAroundAxisRoll();

    Location[] getLocations();

    int getLocationAmount();

    /**
     * @return the distance between every location in this shape
     */
    double getTotalDistance();

    /**
     * @return a new Location object set to the center of this shape
     */
    Location getClonedCenter();
}
