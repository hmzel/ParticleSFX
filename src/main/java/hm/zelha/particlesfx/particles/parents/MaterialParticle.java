package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface MaterialParticle {
    void display(Location location);

    void displayForPlayers(Location location, Player... players);

    void displayForPlayers(Location location, List<UUID> players);

    /**
     * @param particle particle for this object to copy data from
     * @return this object
     */
    MaterialParticle inherit(Particle particle);

    MaterialParticle clone();

    void setMaterial(Material material);

    void setOffset(double x, double y, double z);

    void setOffsetX(double offsetX);

    void setOffsetY(double offsetY);

    void setOffsetZ(double offsetZ);

    void setSpeed(double speed);

    void setCount(int count);

    void setRadius(int radius);

    Material getMaterial();

    double getOffsetX();

    double getOffsetY();

    double getOffsetZ();

    double getSpeed();

    int getCount();

    int getRadius();
}