package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class TravellingParticle extends Particle {

    private Location toGo;
    private Vector velocity;
    private final double control;

    protected TravellingParticle(Effect particle, double control, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.velocity = velocity;
        this.toGo = toGo;
        this.control = control;
    }

    public void display(Location location, Location toGo) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        display(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    public void displayForPlayer(Location location, Location toGo, Player player) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    public void displayForPlayers(Location location, Location toGo, Player... players) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location, players);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    public void display(Location location, Vector velocity) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        display(location);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    public void displayForPlayer(Location location, Vector velocity, Player player) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayer(location, player);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    public void displayForPlayers(Location location, Vector velocity, Player... players) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayers(location, players);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
    }

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by a decimal in all default implementations of
     * VelocityParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     * <p></p>
     * said decimal makes sure that every VelocityParticle implementation follows the same convention,
     * such that a Vector with x,y,z at 1 would make the particle move 1 block in all 3 axis on average, if speed is 1.
     *
     * @param velocity velocity to set
     */
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
    }

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
    public void setVelocity(double x, double y, double z) {

    }

    /**
     * nullable to save resources in {@link Particle#display(Location, Player...)}}
     */
    @Nullable
    public Location getLocationToGo() {
        return toGo;
    }

    /**
     * nullable to save resources in {@link Particle#display(Location, Player...)}}
     */
    @Nullable
    public Vector getVelocity() {
        return velocity;
    }

    public double getVelocityControl() {
        return control;
    }
}
