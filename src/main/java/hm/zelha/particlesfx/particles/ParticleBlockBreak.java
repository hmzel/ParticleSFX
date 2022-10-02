package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.material.MaterialData;

import java.util.List;

public class ParticleBlockBreak extends Particle {

    private MaterialData data;

    public ParticleBlockBreak(MaterialData data, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(EnumParticle.BLOCK_CRACK, offsetX, offsetY, offsetZ, speed, count, 0);

        Validate.notNull(data, "Data cannot be null!");
        Validate.isTrue(data.getItemTypeId() == -13 || data.getItemType().isBlock(), "Material must be a block!");

        this.data = data;
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ, double speed, int count) {
        this(new MaterialData(-13), offsetX, offsetY, offsetZ, speed, count);
    }

    public ParticleBlockBreak(MaterialData data, double offsetX, double offsetY, double offsetZ) {
        this(data, offsetX, offsetY, offsetZ, 1, 1);
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ, double speed) {
        this(new MaterialData(-13), offsetX, offsetY, offsetZ, speed, 1);
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(-13), offsetX, offsetY, offsetZ, 0, count);
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(-13), offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleBlockBreak(MaterialData data, double speed, int count) {
        this(data, 0, 0, 0, speed, count);
    }

    public ParticleBlockBreak(double speed) {
        this(new MaterialData(-13), 0, 0, 0, speed, 1);
    }

    public ParticleBlockBreak(int count) {
        this(new MaterialData(-13), 0, 0, 0, 0, count);
    }

    public ParticleBlockBreak(MaterialData data) {
        this(data, 0, 0, 0, 1, 1);
    }

    public ParticleBlockBreak() {
        this(new MaterialData(-13), 0, 0, 0, 0, 1);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i < players.size(); i++) {
            EntityPlayer p = players.get(i).getHandle();

            if (p == null)
            if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

            if (radius != 0) {
                double distance = Math.sqrt(Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2));

                if (distance > radius) continue;
            }

            p.playerConnection.sendPacket(
                    new PacketPlayOutWorldParticles(
                            EnumParticle.BLOCK_CRACK, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                            (float) offsetX, (float) offsetY, (float) offsetZ, (float) speed, count, (data.getData() << 12 | data.getItemTypeId() & 4095)
                    )
            );
        }
    }

    public void setMaterialData(MaterialData data) {
        Validate.notNull(data, "Data cannot be null!");
        Validate.isTrue(data.getItemType().isBlock(), "Material must be a block!");

        this.data = data;
    }

    public MaterialData getMaterialData() {
        return data;
    }
}
