package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Corner {

    private final List<Corner> connections = new ArrayList<>();
    private LocationSafe location;

    public Corner(LocationSafe location) {
        setLocation(location);
    }

    public Corner(World world, double x, double y, double z) {
        setLocation(new LocationSafe(world, x, y, z));
    }

    public Corner clone() {
        Corner corner = new Corner(location.clone());

        corner.connections.addAll(connections);

        return corner;
    }

    /**
     * @param index index for the given corner to be added to this corner's connection list
     * @param corner corner to connect to
     */
    public void connect(int index, Corner corner) {
        Validate.notNull(corner, "Connection can't be null!");
        Validate.isTrue(corner != this, "Corners cant connect to themselves!");

        connections.add(index, corner);
    }

    /**
     * @param corner corner to connect to
     */
    public void connect(Corner corner) {
        connect(connections.size(), corner);
    }

    /**
     * @param index index of connection to be removed from this corner's list
     */
    public void disconnect(int index) {
        connections.remove(index);
    }

    /**
     * @param corner connection to be removed from this corner's list
     */
    public void disconnect(Corner corner) {
        connections.remove(corner);
    }

    /**
     * @param index index to set
     * @param corner connection to replace the corner at index
     */
    public void setConnection(int index, Corner corner) {
        Validate.notNull(corner, "Connection can't be null!");
        Validate.isTrue(corner != this, "Corners cant connect to themselves!");

        connections.set(index, corner);
    }

    public void setLocation(LocationSafe location) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "Location's world cannot be null!");

        if (this.location != null) {
            location.setChanged(true);
        }

        this.location = location;
    }

    public Corner getConnection(int index) {
        return connections.get(index);
    }

    public Location getLocation() {
        return location;
    }

    public int getConnectionAmount() {
        return connections.size();
    }
}
