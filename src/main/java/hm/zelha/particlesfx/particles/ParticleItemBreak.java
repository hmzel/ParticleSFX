package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.MinecraftKey;
import net.minecraft.server.v1_16_R2.ParticleParamItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * this particle can have the material of any tangible item, regardless of if it can break or not <br>
 * ex: stone works fine, but air will not because it isn't an item you can have in your hand. <br><br>
 *
 * warning: the speed of this particle is inconsistent due to gravity and other factors that aren't accounted for
 */
public class ParticleItemBreak extends TravellingParticle implements MaterialParticle {
    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(null, false, 0.105, velocity, null, offsetX, offsetY, offsetZ, count);

        setMaterial(material);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(null, false, 0.105, null, toGo, offsetX, offsetY, offsetZ, count);

        setMaterial(material);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.DRAGON_EGG, velocity, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.DRAGON_EGG, toGo, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, double offsetX, double offsetY, double offsetZ, int count) {
        this(material, (Location) null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(material, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(material, toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(Material.DRAGON_EGG, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(Material.DRAGON_EGG, toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, double offsetX, double offsetY, double offsetZ) {
        this(material, (Location) null, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Vector velocity, int count) {
        this(material, velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Location toGo, int count) {
        this(material, toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, int count) {
        this(Material.DRAGON_EGG, velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, int count) {
        this(Material.DRAGON_EGG, toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, int count) {
        this(material, (Location) null, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Vector velocity) {
        this(material, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Location toGo) {
        this(material, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity) {
        this(Material.DRAGON_EGG, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo) {
        this(Material.DRAGON_EGG, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material) {
        this(material, (Location) null, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak() {
        this(Material.DRAGON_EGG, (Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleItemBreak inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            setMaterial(((MaterialParticle) particle).getMaterial());
        }

        return this;
    }

    @Override
    public ParticleItemBreak clone() {
        return new ParticleItemBreak().inherit(this);
    }

    public void setMaterial(Material material) {
        Validate.notNull(material, "material cannot be null!");

        particle = new ParticleParamItem((net.minecraft.server.v1_16_R2.Particle) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("item")), CraftItemStack.asNMSCopy(new ItemStack(material)));
    }

    public Material getMaterial() {
        String s = this.particle.a().toLowerCase();

        for (Material m : Material.values()) {
            if (s.contains(m.name().toLowerCase())) {
                return m;
            }
        }

        return null;
    }
}
