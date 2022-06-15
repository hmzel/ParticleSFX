package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public interface TravellingParticle {

    public void display(Location location, Location toGo);

    public void displayForPlayer(Location location, Location toGo, Player player);

    public void displayForPlayers(Location location, Location toGo, Player... players);

    public void setLocationToGo(@Nullable Location location);

    @Nullable
    public Location getLocationToGo();
}
