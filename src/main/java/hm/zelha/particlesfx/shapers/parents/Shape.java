package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Rotation;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

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

    Shape clone();

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

    /**
     * Similar to {@link java.util.function.Consumer} <br>
     * in the case of phases {@link ShapeDisplayMechanic.Phase#BEFORE_ROTATION} and {@link ShapeDisplayMechanic.Phase#AFTER_ROTATION}
     * the given mechanic will run before the location is modified to display the next particle, allowing you to modify the
     * addition vector however you want, though doing so may be very volatile
     * <br><br>
     * keep in mind that all changes to the given objects will be reflected in the display() method, and considering that
     * the display() method often displays hundreds of particles, try to make sure the mechanic isn't very
     * resource-intensive
     * <br><br>
     * {@link ShapeDisplayMechanic#apply(Particle, Location, Vector, int)}
     *
     * @param phase phase that the mechanic should run at
     * @param mechanic mechanic to run during display
     */
    void addMechanic(ShapeDisplayMechanic.Phase phase, ShapeDisplayMechanic mechanic);

    /**
     * adds a player for this shape to display to, defaults to all online players in the shape's world if the list is empty
     *
     * @param player player to add
     */
    void addPlayer(Player player);

    /**
     * adds a player for this shape to display to, defaults to all online players in the shape's world if the list is empty
     *
     * @param uuid ID of player to add
     */
    void addPlayer(UUID uuid);

    /**
     * @see Shape#addMechanic(ShapeDisplayMechanic.Phase, ShapeDisplayMechanic)
     * @param index index of mechanic in list
     */
    void removeMechanic(int index);

    /**
     * @see Shape#addPlayer(Player)
     * @param index index of player to remove from list
     */
    void removePlayer(int index);

    /**
     * @see Shape#addPlayer(Player)
     * @param player player to remove from list
     */
    void removePlayer(Player player);

    void setParticle(Particle particle);

    /** @param particleFrequency amount of times to display the particle per full animation */
    void setParticleFrequency(int particleFrequency);

    /**
     * 0 means that the entire animation will be played when .display() is called
     *
     * @param particlesPerDisplay amount of particles that will be shown per display
     */
    void setParticlesPerDisplay(int particlesPerDisplay);

    /**
     * @param delay amount of ticks between {@link Shape#display()} being called
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

    Particle getParticle();

    int getParticleFrequency();

    /**
     * @see Shape#setParticlesPerDisplay(int)
     * @return the amount of particles per display
     */
    int getParticlesPerDisplay();

    /**
     * @return the UUIDs of all the players that this shape displays to. displays to all players if this list is empty
     */
    List<UUID> getPlayers();

    /**
     * @return amount of players this shape displays to
     */
    int getPlayerAmount();

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
