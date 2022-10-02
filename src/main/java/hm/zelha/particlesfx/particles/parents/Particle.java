package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Particle {

    private final List<CraftPlayer> players = ((CraftServer) Bukkit.getServer()).getOnlinePlayers();
    private final List<CraftPlayer> listHelper = new ArrayList<>();
    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private final Vector vectorHelper = new Vector(0, 0, 0);
    protected final EnumParticle particle;
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;
    protected double speed;
    protected int count;
    protected int radius;

    protected Particle(EnumParticle particle, double offsetX, double offsetY, double offsetZ, double speed, int count, int radius) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;
        this.offsetX = Math.abs(offsetX);
        this.offsetY = Math.abs(offsetY);
        this.offsetZ = Math.abs(offsetZ);
        this.speed = speed;
        this.count = count;
        this.radius = radius;
    }

    public void display(Location location) {
        display(location, players);
    }

    public void displayForPlayers(Location location, Player... players) {
        listHelper.clear();

        for (int i = 0; i < players.length; i++) listHelper.add((CraftPlayer) players[i]);

        display(location, listHelper);
    }

    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < players.size(); i++) {
            EntityPlayer p = players.get(i).getHandle();

            if (p == null) continue;
            if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

            if (radius != 0) {
                double distance = Math.sqrt(Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2));

                if (distance > radius) continue;
            }

            p.playerConnection.sendPacket(
                    new PacketPlayOutWorldParticles(
                            particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                            (float) offsetX, (float) offsetY, (float) offsetZ, (float) speed, count
                    )
            );
        }
    }

    /**
     * generates a random number between -offset and +offset <p>
     * this isnt exactly how its done client-side using the actual packet, but honestly i prefer this because its more controllable
     *
     * @return a vector meant to be added to a location to mimic particle offset
     */
    protected Vector generateFakeOffset() {
        return vectorHelper.setX(rng.nextDouble(offsetX * 2) - offsetX).setY(rng.nextDouble(offsetY * 2) - offsetY).setZ(rng.nextDouble(offsetZ * 2) - offsetZ);
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }

    public int getRadius() {
        return radius;
    }
}
























