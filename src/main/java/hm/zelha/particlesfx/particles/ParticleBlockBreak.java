package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R1.IRegistry;
import net.minecraft.server.v1_16_R1.MinecraftKey;
import net.minecraft.server.v1_16_R1.ParticleParamBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.block.data.CraftBlockData;

public class ParticleBlockBreak extends Particle implements MaterialParticle {
    public ParticleBlockBreak(Material material, double offsetX, double offsetY, double offsetZ, int count) {
        super(null, offsetX, offsetY, offsetZ, 1, count, 0);

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

    public ParticleBlockBreak(int count) {
        this(Material.DRAGON_EGG, 0, 0, 0, count);
    }

    public ParticleBlockBreak(Material material) {
        this(material, 0, 0, 0, 1);
    }

    public ParticleBlockBreak() {
        this(Material.DRAGON_EGG, 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockBreak inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleBlockBreak) {
            this.particle = ((ParticleBlockBreak) particle).particle;
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

        particle = new ParticleParamBlock((net.minecraft.server.v1_16_R1.Particle) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("block")), ((CraftBlockData) material.createBlockData()).getState());
    }

    public Material getMaterial() {
        String s = this.particle.a();

        for (Material m : Material.values()) {
            if (s.contains(m.name().toLowerCase())) {
                return m;
            }
        }

        return null;
    }
}
