package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface TravellingParticle {

    public void display(Location location, Location toGo);

    public void displayForPlayer(Location location, Location toGo, Player player);

    public void displayForPlayers(Location location, Location toGo, Player... players);

    public void setLocationToGo(Location location);

    public Location getLocationToGo();
}
