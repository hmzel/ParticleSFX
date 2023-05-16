package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_10_R1.EnumParticle;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ParticleBlockDust extends Particle implements MaterialParticle {

    protected MaterialData data;

    public ParticleBlockDust(MaterialData data, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.FALLING_DUST, offsetX, offsetY, offsetZ, 1, count, 0);

        setMaterialData(data);
    }

    public ParticleBlockDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(Material.DRAGON_EGG), offsetX, offsetY, offsetZ, count);
    }

    public ParticleBlockDust(MaterialData data, double offsetX, double offsetY, double offsetZ) {
        this(data,offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockDust(double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(Material.DRAGON_EGG), offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBlockDust(MaterialData data, int count) {
        this(data, 0, 0, 0, count);
    }

    public ParticleBlockDust(MaterialData data) {
        this(data, 0, 0, 0, 1);
    }

    public ParticleBlockDust(int count) {
        this(new MaterialData(Material.DRAGON_EGG), 0, 0, 0, count);
    }

    public ParticleBlockDust() {
        this(new MaterialData(Material.DRAGON_EGG), 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockDust inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof MaterialParticle) {
            data = ((MaterialParticle) particle).getMaterialData();
        }

        return this;
    }

    @Override
    public ParticleBlockDust clone() {
        return new ParticleBlockDust().inherit(this);
    }

    @Override
    protected int[] getPacketData() {
        return new int[] {(data.getData() << 12 | data.getItemTypeId() & 4095)};
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
