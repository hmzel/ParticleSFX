package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.MaterialParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

/**
 * this particle can have the material of any tangible item, regardless of if it can break or not <br>
 * ex: stone works fine, but air will not because it isn't an item you can have in your hand. <br><br>
 *
 * warning: the speed of this particle is inconsistent due to gravity and other factors that aren't accounted for
 */
public class ParticleItemBreak extends MaterialParticle {
    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ITEM_CRACK, false, 0.105, data, velocity, null, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ITEM_CRACK, false, 0.105, data, null, toGo, offsetX, offsetY, offsetZ, count);

        setMaterialData(data);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(-13), velocity, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        this(new MaterialData(-13), toGo, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, double offsetX, double offsetY, double offsetZ, int count) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(data, velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(data, toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(-13), velocity, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(new MaterialData(-13), toGo, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, double offsetX, double offsetY, double offsetZ) {
        this(data, (Location) null, offsetX, offsetY, offsetZ, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity, int count) {
        this(data, velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo, int count) {
        this(data, toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity, int count) {
        this(new MaterialData(-13), velocity, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo, int count) {
        this(new MaterialData(-13), toGo, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, int count) {
        this(data, (Location) null, 0, 0, 0, count);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Vector velocity) {
        this(data, velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data, Location toGo) {
        this(data, toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Vector velocity) {
        this(new MaterialData(-13), velocity, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(Location toGo) {
        this(new MaterialData(-13), toGo, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak(MaterialData data) {
        this(data, (Location) null, 0, 0, 0, 1);
    }

    /**@see ParticleItemBreak*/
    public ParticleItemBreak() {
        this(new MaterialData(-13), (Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleItemBreak inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleItemBreak clone() {
        return new ParticleItemBreak().inherit(this);
    }

    @Override
    protected int[] getPacketData() {
        return new int[] {data.getItemTypeId(), data.getData()};
    }
}
