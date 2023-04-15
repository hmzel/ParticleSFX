package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;

import java.util.List;

public abstract class SizeableParticle extends Particle {

    protected double size;

    protected SizeableParticle(EnumParticle particle, double size, double offsetX, double offsetY, double offsetZ, double speed, int count, int radius) {
        super(particle, offsetX, offsetY, offsetZ, speed, count, radius);

        this.size = size;
    }

    @Override
    public SizeableParticle inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof SizeableParticle) {
            size = ((SizeableParticle) particle).size;
        }

        return this;
    }

    @Override
    public abstract SizeableParticle clone();

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
