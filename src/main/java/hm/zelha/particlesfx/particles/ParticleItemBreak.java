package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * this particle can have the material of any tangible item, regardless of if it can break or not <br>
 * ex: stone works fine, but air will not because it isn't an item you can have in your hand. <br><br>
 *
 * warning: the speed of this particle is inconsistent due to gravity and other internal factors that aren't accounted for
 */
public class ParticleItemBreak extends TravellingParticle {

    private MaterialData data;

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ITEM_CRACK, false, 0.105, velocity, null, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ITEM_CRACK, false, 0.105, null, toGo, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(-13), velocity, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(-13), toGo, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, double offsetX, double offsetY, double offsetZ, int count) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(data, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(data, toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(-13), velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(-13), toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, double offsetX, double offsetY, double offsetZ) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, int count) {
        this(data, velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, int count) {
        this(data, toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, int count) {
        this(new MaterialData(-13), velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, int count) {
        this(new MaterialData(-13), toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, int count) {
        this(data, (Location) null, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity) {
        this(data, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo) {
        this(data, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity) {
        this(new MaterialData(-13), velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo) {
        this(new MaterialData(-13), toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data) {
        this(data, (Location) null, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak() {
        this(new MaterialData(-13), (Location) null, 0, 0, 0, 1);
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
                                (float) trueOffsetX, (float) trueOffsetY, (float) trueOffsetZ, speed, count, data.getItemTypeId(), data.getData()
                        )
                );
            }

            if (addition != null) {
                location.subtract(addition);
            }
        }
    }

    public void setMaterialData(MaterialData data) {
        Validate.notNull(data, "Data cannot be null!");

        this.data = data;
    }

    public MaterialData getMaterialData() {
        return data;
    }
}
