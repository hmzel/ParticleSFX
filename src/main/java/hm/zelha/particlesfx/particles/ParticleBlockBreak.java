package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftMagicNumbers;

public class ParticleBlockBreak extends Particle implements MaterialParticle {
    public ParticleBlockBreak(Material material, double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, count);

        setMaterial(material);
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.DRAGON_EGG, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBlockBreak(Material material, double offsetX, double offsetY, double offsetZ) {
        this(material, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockBreak(double offsetX, double offsetY, double offsetZ) {
        this(Material.DRAGON_EGG, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockBreak(Material material, int count) {
        this(material, 0, 0, 0, count);
    }

    public ParticleBlockBreak(Material material) {
        this(material, 0, 0, 0, 1);
    }

    public ParticleBlockBreak(int count) {
        this(Material.DRAGON_EGG, 0, 0, 0, count);
    }

    public ParticleBlockBreak() {
        this(Material.DRAGON_EGG, 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockBreak inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            setMaterial(((MaterialParticle) particle).getMaterial());
        }

        return this;
    }

    @Override
    public ParticleBlockBreak clone() {
        return new ParticleBlockBreak().inherit(this);
    }

    public void setMaterial(Material material) {
        Validate.notNull(material, "Material cannot be null!");
        Validate.isTrue(material.isBlock(), "Material must be a block!");

        particle = new ParticleParamBlock((net.minecraft.core.particles.Particle) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", "block")), ((CraftBlockData) material.createBlockData()).getState());
    }

    public Material getMaterial() {
        return CraftMagicNumbers.getMaterial(((ParticleParamBlock) particle).b().b());
    }
}