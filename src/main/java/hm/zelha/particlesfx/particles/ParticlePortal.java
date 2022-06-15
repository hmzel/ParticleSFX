package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.InverseTravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticlePortal extends Particle implements InverseTravellingParticle {

    private Location toGo;
    private Vector velocity;

    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.FLYING_GLYPH, offsetX, offsetY, offsetZ, 0, count, 0);

        this.toGo = toGo;
    }

    public ParticlePortal(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.FLYING_GLYPH, offsetX, offsetY, offsetZ, 0, count, 0);

        this.velocity = velocity;
    }

    public ParticlePortal(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticlePortal(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticlePortal(Location toGo, int count) {
        this(toGo, 0, 0, 0, count);
    }

    public ParticlePortal(Vector velocity, int count) {
        this(velocity, 0, 0, 0, count);
    }

    public ParticlePortal(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticlePortal(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticlePortal(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticlePortal() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public void display(Location location, Location toGo) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        display(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    @Override
    public void displayForPlayer(Location location, Location toGo, Player player) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    @Override
    public void displayForPlayers(Location location, Location toGo, Player... players) {
        Location old = this.toGo;
        Vector oldVelocity = velocity;
        this.velocity = null;
        this.toGo = toGo;

        displayForPlayers(location, players);

        this.toGo = old;
        this.velocity = oldVelocity;
    }

    @Override
    public void display(Location location, Vector velocity) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        display(location);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    @Override
    public void displayForPlayer(Location location, Vector velocity, Player player) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayer(location, player);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    @Override
    public void displayForPlayers(Location location, Vector velocity, Player... players) {
        Vector old = this.velocity;
        Location oldToGo = toGo;
        this.toGo = null;
        this.velocity = velocity;

        displayForPlayers(location, players);

        this.toGo = oldToGo;
        this.velocity = old;
    }

    @Override
    public void setLocationToGo(@Nullable Location location) {
        this.toGo = location;
    }

    @Override
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z);
    }

    @Nullable
    @Override
    public Location getLocationToGo() {
        return toGo;
    }

    @Nullable
    @Override
    public Vector getVelocity() {
        return velocity;
    }
}
