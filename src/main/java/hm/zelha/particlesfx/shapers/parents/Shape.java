package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.Rotation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public interface Shape {

    public void start();

    public void stop();

    public void display();

    public void rotate(double pitch, double yaw, double roll);

    public void rotateAroundLocation(Location around, double pitch, double yaw, double roll);

    public void face(Location toFace);

    public void faceAroundLocation(Location toFace, Location around);

    /**
     * adds the given x, y, z values to every location in the shape
     *
     * @param x x addition
     * @param y y addition
     * @param z z addition
     */
    public void move(double x, double y, double z);

    /**
     * adds the given vector to every location in the shape
     *
     * @param vector vector to add
     */
    public void move(Vector vector);

    /**
     * adds the given location to every location in the shape
     *
     * @param location location to add
     */
    public void move(Location location);

    /**
     * Rescales this shape such that a shape with the xyz radiuses of 4, 3, 4, resized by 1.25, 1.25, 1.25, would have its
     * radiuses become 5, 3.75, 5.
     *
     * @param x x scale
     * @param y y scale
     * @param z z scale
     */
    public void scale(double x, double y, double z);

    /**
     * Rescales this shape such that a shape with the xyz radiuses of 4, 3, 4, resized by 1.25, would have its
     * radiuses become 5, 3.75, 5.
     *
     * @param scale scale amount
     */
    public void scale(double scale);

    public Shape clone();

    /**
     * @param delay amount of ticks between {@link ParticleShaper#display()} being called
     */
    public void setDelay(int delay);

    /**
     * sets the current position of the shape's animation <br>
     * (aka, sets the tracker that tells the shape how many particles have been displayed until this point) <br>
     *
     * @param position position for shape to display at
     */
    public void setDisplayPosition(int position);

    public void setWorld(World world);

    public void setRotation(double pitch, double yaw, double roll);

    public void setRotationOrder(Rotation.Axis first, Rotation.Axis second, Rotation.Axis third);

    public void setPitch(double pitch);

    public void setYaw(double yaw);

    public void setRoll(double roll);

    public void setAroundRotation(Location around, double pitch, double yaw, double roll);

    public void setAroundRotationOrder(Location around, Rotation.Axis first, Rotation.Axis second, Rotation.Axis third);

    public void setAroundPitch(Location around, double pitch);

    public void setAroundYaw(Location around, double yaw);

    public void setAroundRoll(Location around, double roll);

    public void setAxisRotation(double pitch, double yaw, double roll);

    public void setAxisPitch(double pitch);

    public void setAxisYaw(double yaw);

    public void setAxisRoll(double roll);

    public void setAroundAxisRotation(Location around, double pitch, double yaw, double roll);

    public void setAroundAxisPitch(Location around, double pitch);

    public void setAroundAxisYaw(Location around, double yaw);

    public void setAroundAxisRoll(Location around, double roll);

    public World getWorld();

    public Rotation.Axis[] getRotationOrder();

    public double getPitch();

    public double getYaw();

    public double getRoll();

    public Rotation.Axis[] getAroundRotationOrder();

    public double getAroundPitch();

    public double getAroundYaw();

    public double getAroundRoll();

    public double getAxisPitch();

    public double getAxisYaw();

    public double getAxisRoll();

    public double getAroundAxisPitch();

    public double getAroundAxisYaw();

    public double getAroundAxisRoll();

    public Location[] getLocations();

    public int getLocationAmount();

    /**
     * @return the distance between every location in this shape
     */
    public double getTotalDistance();

    /**
     * @return a new Location object set to the center of this shape
     */
    public Location getClonedCenter();
}
