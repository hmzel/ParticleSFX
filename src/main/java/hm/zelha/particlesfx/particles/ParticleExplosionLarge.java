package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleExplosionLarge extends Particle {

    private double size;

    public ParticleExplosionLarge(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_LARGE, offsetX, offsetY, offsetZ, 0, count, 0);

        this.size = size;
    }

    public ParticleExplosionLarge(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionLarge(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosionLarge(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosionLarge(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleExplosionLarge(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleExplosionLarge(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleExplosionLarge() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i != count; i++) {
            Vector addition = generateFakeOffset();

            location.add(addition);

            for (int i2 = 0; i2 < players.size(); i2++) {
                EntityPlayer p = players.get(i2).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2);

                    if (distance > Math.pow(radius, 2)) continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldParticles(
                                particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                (float) -(size) + 2, 0, 0, (float) 1, 0
                        )
                );
            }

            location.subtract(addition);
        }
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}
