package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Particle {

    protected final EnumParticle particle;
    protected final Vector fakeOffsetHelper = new Vector();
    protected final Vector xyzHelper = new Vector();
    protected final Vector offsetHelper = new Vector();
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;
    protected double speed;
    protected int count;
    protected int radius;
    private final List<CraftPlayer> players = ((CraftServer) Bukkit.getServer()).getOnlinePlayers();
    private final List<CraftPlayer> listHelper = new ArrayList<>();
    private final ThreadLocalRandom rng = ThreadLocalRandom.current();

    protected Particle(EnumParticle particle, double offsetX, double offsetY, double offsetZ, double speed, int count, int radius) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;

        setOffset(offsetX, offsetY, offsetZ);
        setSpeed(speed);
        setCount(count);
        setRadius(radius);
    }

    public void display(Location location) {
        display(location, players);
    }

    public void displayForPlayers(Location location, Player... players) {
        listHelper.clear();

        for (int i = 0; i < players.length; i++) {
            listHelper.add((CraftPlayer) players[i]);
        }

        display(location, listHelper);
    }

    public void displayForPlayers(Location location, List<UUID> players) {
        listHelper.clear();

        for (int i = 0; i < players.size(); i++) {
            Player p = Bukkit.getPlayer(players.get(i));

            if (p == null) continue;

            listHelper.add((CraftPlayer) p);
        }

        display(location, listHelper);
    }

    /**
     * @param particle particle for this object to copy data from
     * @return this object
     */
    public Particle inherit(Particle particle) {
        offsetX = particle.offsetX;
        offsetY = particle.offsetY;
        offsetZ = particle.offsetZ;
        speed = particle.speed;
        count = particle.count;
        radius = particle.radius;

        return this;
    }

    public abstract Particle clone();

    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < ((getPacketCount() != count) ? count : 1); i++) {
            for (int k = 0; k < players.size(); k++) {
                EntityPlayer p = players.get(k).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2);

                    if (distance > Math.pow(radius, 2)) continue;
                }

                Vector xyz = getXYZ(location);
                Vector offsets = getOffsets(location);
                Packet strangePacket = getStrangePacket();

                if (strangePacket != null) {
                    p.playerConnection.sendPacket(strangePacket);
                } else {
                    p.playerConnection.sendPacket(
                            new PacketPlayOutWorldParticles(
                                    particle, true, (float) xyz.getX(), (float) xyz.getY(), (float) xyz.getZ(), (float) offsets.getX(),
                                    (float) offsets.getY(), (float) offsets.getZ(), getPacketSpeed(), getPacketCount(), getPacketData()
                            )
                    );
                }
            }
        }
    }

    /**
     * generates a random number between -offset and +offset <br>
     * this isnt exactly how its done client-side using the actual packet, but honestly i prefer this because its more controllable
     *
     * @return a vector meant to be added to a location to mimic particle offset
     */
    protected Vector generateFakeOffset() {
        fakeOffsetHelper.zero();

        if (offsetX != 0) {
            fakeOffsetHelper.setX(rng.nextDouble(offsetX * 2) - offsetX);
        }

        if (offsetY != 0) {
            fakeOffsetHelper.setY(rng.nextDouble(offsetY * 2) - offsetY);
        }

        if (offsetZ != 0) {
            fakeOffsetHelper.setZ(rng.nextDouble(offsetZ * 2) - offsetZ);
        }

        return fakeOffsetHelper;
    }

    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location);
    }

    protected Vector getOffsets(Location location) {
        return offsetHelper.zero().setX(offsetX).setY(offsetY).setZ(offsetZ);
    }

    protected float getPacketSpeed() {
        return (float) speed;
    }

    protected int getPacketCount() {
        return count;
    }

    protected int[] getPacketData() {
        return new int[0];
    }

    protected Packet getStrangePacket() {
        return null;
    }

    public void setOffset(double x, double y, double z) {
        setOffsetX(x);
        setOffsetY(y);
        setOffsetZ(z);
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = Math.abs(offsetX);
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = Math.abs(offsetY);
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = Math.abs(offsetZ);
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
