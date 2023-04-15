package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.List;

public abstract class MaterialParticle extends TravellingParticle {

    protected MaterialData data;

    protected MaterialParticle(EnumParticle particle, boolean inverse, double control, MaterialData data, Vector velocity, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, inverse, control, velocity, toGo, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    @Override
    public MaterialParticle inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            data = ((MaterialParticle) particle).data;
        }

        return this;
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < ((toGo == null && velocity == null) ? 1 : count); i++) {
            int count = 0;
            float speed = 1;
            double trueOffsetX = offsetX;
            double trueOffsetY = offsetY;
            double trueOffsetZ = offsetZ;
            Vector addition = null;

            if (toGo != null || velocity != null) {
                addition = generateFakeOffset();

                location.add(addition);
            }

            if (velocity != null) {
                trueOffsetX = velocity.getX() * control;
                trueOffsetY = velocity.getY() * control;
                trueOffsetZ = velocity.getZ() * control;
            } else if (toGo != null) {
                trueOffsetX = (toGo.getX() - location.getX()) * control;
                trueOffsetY = (toGo.getY() - location.getY()) * control;
                trueOffsetZ = (toGo.getZ() - location.getZ()) * control;
            } else {
                speed = 0;
                count = this.count;
            }

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
                                (float) trueOffsetX, (float) trueOffsetY, (float) trueOffsetZ, speed, count, getPacketData()
                        )
                );
            }

            if (addition != null) {
                location.subtract(addition);
            }
        }
    }

    protected abstract int[] getPacketData();

    public void setMaterialData(MaterialData data) {
        Validate.notNull(data, "Data cannot be null!");
        Validate.isTrue(data.getItemType().isBlock(), "Material must be a block!");

        this.data = data;
    }

    public MaterialData getMaterialData() {
        return data;
    }
}
