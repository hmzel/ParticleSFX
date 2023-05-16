package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface SizeableParticle {
    void display(Location location);

    void displayForPlayers(Location location, Player... players);

    void displayForPlayers(Location location, List<UUID> players);

    /**
     * @param particle particle for this object to copy data from
     * @return this object
     */
    SizeableParticle inherit(Particle particle);

    SizeableParticle clone();

    void setSize(double size);

    void setOffset(double x, double y, double z);

    void setOffsetX(double offsetX);

    void setOffsetY(double offsetY);

    void setOffsetZ(double offsetZ);

    void setSpeed(double speed);

    void setCount(int count);

    void setRadius(int radius);

    double getSize();

    double getOffsetX();

    double getOffsetY();

    double getOffsetZ();

    double getSpeed();

    int getCount();

    int getRadius();
}