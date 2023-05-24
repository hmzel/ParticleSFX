package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.core.IRegistry;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.resources.MinecraftKey;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;

public class ParticleBlockMarker extends Particle implements MaterialParticle {
    public ParticleBlockMarker(Material material, double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, 1, count, 0);

        setMaterial(material);
    }

    public ParticleBlockMarker(double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.BARRIER, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBlockMarker(Material material, double offsetX, double offsetY, double offsetZ) {
        this(material,offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockMarker(double offsetX, double offsetY, double offsetZ) {
        this(Material.BARRIER, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockMarker(Material material, int count) {
        this(material, 0, 0, 0, count);
    }

    public ParticleBlockMarker(Material material) {
        this(material, 0, 0, 0, 1);
    }

    public ParticleBlockMarker(int count) {
        this(Material.BARRIER, 0, 0, 0, count);
    }

    public ParticleBlockMarker() {
        this(Material.BARRIER, 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockMarker inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            setMaterial(((MaterialParticle) particle).getMaterial());
        }

        return this;
    }

    @Override
    public ParticleBlockMarker clone() {
        return new ParticleBlockMarker().inherit(this);
    }

    public void setMaterial(Material material) {
        Validate.notNull(material, "Material cannot be null!");
        Validate.isTrue(material.isBlock(), "Material must be a block!");

        particle = new ParticleParamBlock((net.minecraft.core.particles.Particle) IRegistry.aa.a(new MinecraftKey("block_marker")), ((CraftBlockData) material.createBlockData()).getState());
    }

    public Material getMaterial() {
        return CraftMagicNumbers.getMaterial(((ParticleParamBlock) particle).c().b());
    }
}
