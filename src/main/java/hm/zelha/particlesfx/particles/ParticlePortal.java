package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class ParticlePortal extends Particle implements TravellingParticle {

    private Location toGo;

    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.PORTAL, offsetX, offsetY, offsetZ, 0, count, 0);

        this.toGo = toGo;
    }

    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ) {
        this(null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(Location toGo, int count) {
        this(toGo, 0, 0, 0, count);
    }

    public ParticlePortal(int count) {
        this(null, 0, 0, 0, count);
    }

    public ParticlePortal(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticlePortal() {
        this(null, 0, 0, 0, 1);
    }

    @Override
    public void display(Location location, Location toGo) {
        Location old = this.toGo;
        this.toGo = toGo;

        display(location);

        this.toGo = old;
    }

    @Override
    public void displayForPlayer(Location location, Location toGo, Player player) {
        Location old = this.toGo;
        this.toGo = toGo;

        displayForPlayer(location, player);

        this.toGo = old;
    }

    @Override
    public void displayForPlayers(Location location, Location toGo, Player... players) {
        Location old = this.toGo;
        this.toGo = toGo;

        displayForPlayers(location, players);

        this.toGo = old;
    }

    @Override
    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
    }

    @Override@Nullable
    public Location getLocationToGo() {
        return toGo;
    }
}
