package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.material.MaterialData;

public class ParticleBlockBreak extends Particle {

    private MaterialData data;

    public ParticleBlockBreak(MaterialData data, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.TILE_BREAK, offsetX, offsetY, offsetZ, speed, count, 0);

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

    public void setMaterialData(MaterialData data) {
        Validate.notNull(data, "Data cannot be null!");
        Validate.isTrue(data.getItemType().isBlock(), "Material must be a block!");

        this.data = data;
    }

    public MaterialData getMaterialData() {
        return data;
    }
}
