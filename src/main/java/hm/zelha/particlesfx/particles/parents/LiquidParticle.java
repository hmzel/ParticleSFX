package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.LiquidParticleState;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface LiquidParticle {
    void display(Location location);

    void displayForPlayers(Location location, Player... players);

    void displayForPlayers(Location location, List<UUID> players);

    /**
     * @param state The type of liquid particle this object represents, keep in mind some LiquidParticles don't support all states.
     * @return this object
     */
    LiquidParticle setLiquidState(LiquidParticleState state);

    void setOffset(double x, double y, double z);

    void setOffsetX(double offsetX);

    void setOffsetY(double offsetY);

    void setOffsetZ(double offsetZ);

    Particle setSpeed(double speed);

    void setCount(int count);

    Particle setRadius(int radius);

    /**
     * @return The type of liquid particle this object represents, keep in mind some LiquidParticles don't support all states.
     */
    LiquidParticleState getLiquidState();

    double getOffsetX();

    double getOffsetY();

    double getOffsetZ();

    double getSpeed();

    int getCount();

    int getRadius();
}
