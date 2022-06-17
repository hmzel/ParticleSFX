package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleSmokeLarge extends Particle implements TravellingParticle {

    private Location toGo = null;
    private Vector velocity = null;

    public ParticleSmokeLarge(Location toGo, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.LARGE_SMOKE, offsetX, offsetY, offsetZ, speed, count, 0);

        this.toGo = toGo;
    }

    public ParticleSmokeLarge(Vector velocity, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.LARGE_SMOKE, offsetX, offsetY, offsetZ, speed, count, 0);

        if (velocity != null) this.velocity = velocity.multiply(0.06);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, speed, count);
    }

    public ParticleSmokeLarge(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1, 1);
    }

    public ParticleSmokeLarge(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1, 1);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ, double speed) {
        this((Location) null, offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleSmokeLarge(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleSmokeLarge(Location toGo, double speed, int count) {
        this(toGo, 0, 0, 0, speed, count);
    }

    public ParticleSmokeLarge(Vector velocity, double speed, int count) {
        this(velocity, 0, 0, 0, speed, count);
    }

    public ParticleSmokeLarge(double speed) {
        this((Location) null, 0, 0, 0, speed, 1);
    }

    public ParticleSmokeLarge(int count) {
        this((Location) null, 0, 0, 0, 0, count);
    }

    public ParticleSmokeLarge(Location toGo) {
        this(toGo, 0, 0, 0, 1, 1);
    }

    public ParticleSmokeLarge(Vector velocity) {
        this(velocity, 0, 0, 0, 1, 1);
    }

    public ParticleSmokeLarge() {
        this((Location) null, 0, 0, 0, 0, 1);
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
    public void setVelocity(Vector vector) {
        this.velocity = vector.multiply(0.06);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z).multiply(0.06);
    }

    @Nullable
    @Override
    public Location getLocationToGo() {
        return toGo;
    }

    @Override@Nullable
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public double getVelocityControl() {
        return 0.06;
    }
}
