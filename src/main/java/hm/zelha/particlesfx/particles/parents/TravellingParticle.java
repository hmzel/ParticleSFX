package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public interface TravellingParticle {

    public void display(Location location, Location toGo);

    public void displayForPlayer(Location location, Location toGo, Player player);

    public void displayForPlayers(Location location, Location toGo, Player... players);

    public void display(Location location, Vector velocity);

    public void displayForPlayer(Location location, Vector velocity, Player player);

    public void displayForPlayers(Location location, Vector velocity, Player... players);

    public void setLocationToGo(@Nullable Location location);

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by a decimal in all default implementations of
     * VelocityParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     * <p></p>
     * said decimal makes sure that every VelocityParticle implementation follows the same convention,
     * such that a Vector with x,y,z at 1 would make the particle move 1 block in all 3 axis on average, if speed is 1.
     *
     * @param vector velocity to set
     */
    public void setVelocity(Vector vector);

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by a decimal in all default implementations of
     * VelocityParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     * <p></p>
     * said decimal makes sure that every VelocityParticle implementation follows the same convention,
     * such that a Vector with x,y,z at 1 would make the particle move 1 block in all 3 axis on average, if speed is 1.
     *
     * @param x x velocity
     * @param y y velocity
     * @param z z velocity
     */
    public void setVelocity(double x, double y, double z);

    @Nullable
    public Location getLocationToGo();

    /** nullable to save resources in {@link Particle#display(Location, Player...)}}*/
    @Nullable
    public Vector getVelocity();

    public double getVelocityControl();
}
