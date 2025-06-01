package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface ColorableParticle {
    void display(Location location);

    void displayForPlayers(Location location, Player... players);

    void displayForPlayers(Location location, List<UUID> players);

    /**
     * @param particle particle for this object to copy data from
     * @return this object
     */
    ColorableParticle inherit(Particle particle);

    ColorableParticle clone();

    /**
     * @param color color to set, null if you want random coloring
     */
    void setColor(@Nullable Color color);

    void setColor(int red, int green, int blue);

    void setOffset(double x, double y, double z);

    void setOffsetX(double offsetX);

    void setOffsetY(double offsetY);

    void setOffsetZ(double offsetZ);

    Particle setSpeed(double speed);

    void setCount(int count);

    Particle setRadius(int radius);

    /**
     * nullable to allow for randomly colored particles without being complicated
     *
     * @return color this particle is using
     */
    @Nullable
    Color getColor();

    double getOffsetX();

    double getOffsetY();

    double getOffsetZ();

    double getSpeed();

    int getCount();

    int getRadius();
}