package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.UUID;

public interface MaterialParticle {
    public void display(Location location);

    public void displayForPlayers(Location location, Player... players);

    public void displayForPlayers(Location location, List<UUID> players);

    /**
     * @param particle particle for this object to copy data from
     * @return this object
     */
    public MaterialParticle inherit(Particle particle);

    public abstract MaterialParticle clone();

    public void setMaterialData(MaterialData data);

    public void setOffset(double x, double y, double z);

    public void setOffsetX(double offsetX);

    public void setOffsetY(double offsetY);

    public void setOffsetZ(double offsetZ);

    public void setSpeed(double speed);

    public void setCount(int count);

    public void setRadius(int radius);

    public MaterialData getMaterialData();

    public double getOffsetX();

    public double getOffsetY();

    public double getOffsetZ();

    public double getSpeed();

    public int getCount();

    public int getRadius();
}
