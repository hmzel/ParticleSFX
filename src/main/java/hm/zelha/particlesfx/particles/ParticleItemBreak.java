package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * this particle can have any material, regardless of if it can break or not <br>
 * however, the speed of this particle is inconsistent
 */
public class ParticleItemBreak extends TravellingParticle implements MaterialParticle {
    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0.105, velocity, null, offsetX, offsetY, offsetZ, count);

        setMaterial(material);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0.105, null, toGo, offsetX, offsetY, offsetZ, count);

        setMaterial(material);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.AIR, velocity, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.AIR, toGo, offsetX, offsetY, offsetZ, count);
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
        this(Material.AIR, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(Material.AIR, toGo, offsetX, offsetY, offsetZ, 1);
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
        this(Material.AIR, velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, int count) {
        this(Material.AIR, toGo, 0, 0, 0, count);
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
        this(Material.AIR, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo) {
        this(Material.AIR, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Material material) {
        this(material, (Location) null, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak() {
        this(Material.AIR, (Location) null, 0, 0, 0, 1);
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

        particle = new ParticleParamItem((net.minecraft.core.particles.Particle) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", "item")), CraftItemStack.asNMSCopy(new ItemStack(material)));
    }

    public Material getMaterial() {
        return CraftMagicNumbers.getMaterial(((ParticleParamItem) particle).b().g());
    }
}