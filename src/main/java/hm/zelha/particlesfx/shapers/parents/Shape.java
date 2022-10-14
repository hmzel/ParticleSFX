package hm.zelha.particlesfx.shapers.parents;

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

    public void move(double x, double y, double z);

    public void move(Vector vector);

    public void move(Location location);

    public void setWorld(World world);

    public void setPitch(double pitch);

    public void setYaw(double yaw);

    public void setRoll(double roll);

    public void setAroundPitch(double pitch);

    public void setAroundYaw(double yaw);

    public void setAroundRoll(double roll);

    public int getLocationAmount();

    public double getTotalDistance();

    public Location getClonedCenter();
}
