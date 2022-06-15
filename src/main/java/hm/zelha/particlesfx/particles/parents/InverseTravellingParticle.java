package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

/** some travelling particles have to be handled differently due to internal minecraft jank */
public interface InverseTravellingParticle {

    public void display(Location location, Location toGo);

    public void displayForPlayer(Location location, Location toGo, Player player);

    public void displayForPlayers(Location location, Location toGo, Player... players);

    public void display(Location location, Vector velocity);

    public void displayForPlayer(Location location, Vector velocity, Player player);

    public void displayForPlayers(Location location, Vector velocity, Player... players);

    public void setLocationToGo(@Nullable Location location);

    public void setVelocity(@Nullable Vector velocity);

    @Nullable
    public Location getLocationToGo();

    @Nullable
    public Vector getVelocity();
}
