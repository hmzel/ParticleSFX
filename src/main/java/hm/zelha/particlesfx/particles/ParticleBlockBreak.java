package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_9_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

/**
 * warning: the speed of this particle is inconsistent due to gravity and other factors that aren't accounted for
 */
public class ParticleBlockBreak extends MaterialParticle {
    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.BLOCK_DUST, false, 0.105, data, velocity, null, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.BLOCK_DUST, false, 0.105, data, null, toGo, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(Material.DRAGON_EGG), velocity, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(Material.DRAGON_EGG), toGo, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, double offsetX, double offsetY, double offsetZ, int count) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(data, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(data, toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(Material.DRAGON_EGG), velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(Material.DRAGON_EGG), toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, double offsetX, double offsetY, double offsetZ) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Vector velocity, int count) {
        this(data, velocity, 0, 0, 0, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Location toGo, int count) {
        this(data, toGo, 0, 0, 0, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Vector velocity, int count) {
        this(new MaterialData(Material.DRAGON_EGG), velocity, 0, 0, 0, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Location toGo, int count) {
        this(new MaterialData(Material.DRAGON_EGG), toGo, 0, 0, 0, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, int count) {
        this(data, (Location) null, 0, 0, 0, count);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Vector velocity) {
        this(data, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data, Location toGo) {
        this(data, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Vector velocity) {
        this(new MaterialData(Material.DRAGON_EGG), velocity, 0, 0, 0, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(Location toGo) {
        this(new MaterialData(Material.DRAGON_EGG), toGo, 0, 0, 0, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak(MaterialData data) {
        this(data, (Location) null, 0, 0, 0, 1);
    }

    /**@see ParticleBlockBreak*/
    public ParticleBlockBreak() {
        this(new MaterialData(Material.DRAGON_EGG), (Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleBlockBreak inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleBlockBreak clone() {
        return new ParticleBlockBreak().inherit(this);
    }

    @Override
    protected int[] getPacketData() {
        return new int[] {(data.getData() << 12 | data.getItemTypeId() & 4095)};
    }
}
