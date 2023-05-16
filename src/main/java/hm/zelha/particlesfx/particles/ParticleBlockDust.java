package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.ParticleParamBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;

public class ParticleBlockDust extends Particle implements MaterialParticle {
    public ParticleBlockDust(Material material, double offsetX, double offsetY, double offsetZ, int count) {
        super(null, offsetX, offsetY, offsetZ, 1, count, 0);

        setMaterial(material);
    }

    public ParticleBlockDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(Material.DRAGON_EGG, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBlockDust(Material material, double offsetX, double offsetY, double offsetZ) {
        this(material,offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockDust(double offsetX, double offsetY, double offsetZ) {
        this(Material.DRAGON_EGG, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockDust(Material material, int count) {
        this(material, 0, 0, 0, count);
    }

    public ParticleBlockDust(Material material) {
        this(material, 0, 0, 0, 1);
    }

    public ParticleBlockDust(int count) {
        this(Material.DRAGON_EGG, 0, 0, 0, count);
    }

    public ParticleBlockDust() {
        this(Material.DRAGON_EGG, 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockDust inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            setMaterial(((MaterialParticle) particle).getMaterial());
        }

        return this;
    }

    @Override
    public ParticleBlockDust clone() {
        return new ParticleBlockDust().inherit(this);
    }

    public void setMaterial(Material material) {
        Validate.notNull(material, "Material cannot be null!");
        Validate.isTrue(material.isBlock(), "Material must be a block!");

        particle = new ParticleParamBlock((net.minecraft.server.v1_16_R3.Particle) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("falling_dust")), ((CraftBlockData) material.createBlockData()).getState());
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
