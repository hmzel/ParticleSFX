package hm.zelha.particlesfx.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Corner {

    private final List<Corner> connections = new ArrayList<>();
    private final LocationSafe location;

    public Corner(LocationSafe location) {
        Validate.notNull(location, "Location cant be null!");
        Validate.notNull(location.getWorld(), "Location cannot have a null world!");

        this.location = location;
    }

    public Corner(World world, double x, double y, double z) {
        Validate.notNull(world, "Location cannot have a null world!");

        this.location = new LocationSafe(world, x, y, z);
    }

    public Corner clone() {
        Corner corner = new Corner(location.clone());

        corner.connections.addAll(connections);

        return corner;
    }

    public void connect(int index, Corner corner) {
        Validate.notNull(corner, "Connection can't be null!");
        Validate.isTrue(corner != this, "Corners cant connect to themselves!");

        connections.add(index, corner);
    }

    public void connect(Corner corner) {
        connect(connections.size(), corner);
    }

    public void disconnect(int index) {
        connections.remove(index);
    }

    public void disconnect(Corner corner) {
        connections.remove(corner);
    }

    public Corner getConnection(int index) {
        return connections.get(index);
    }

    public LocationSafe getLocation() {
        return location;
    }

    public int getConnectionAmount() {
        return connections.size();
    }
}
