package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.potion.PotionType;

/**
 * NOTE: this effect makes sound!
 * <br><br>
 * since Effect.POTION_BREAK is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <br><br>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their packet uses BlockPosition
 */
public class ParticlePotionBreak extends Particle {

    private PotionType type;

    /**@see ParticlePotionBreak*/
    public ParticlePotionBreak(PotionType type, int count) {
        super(EnumParticle.HEART, 0, 0, 0, 0, count, 0);

        setPotionType(type);
    }

    /**@see ParticlePotionBreak*/
    public ParticlePotionBreak(PotionType type) {
        this(type, 1);
    }

    /**@see ParticlePotionBreak*/
    public ParticlePotionBreak(int count) {
        this(PotionType.WATER, count);
    }

    /**@see ParticlePotionBreak*/
    public ParticlePotionBreak() {
        this(PotionType.WATER, 1);
    }

    @Override
    public ParticlePotionBreak inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticlePotionBreak) {
            type = ((ParticlePotionBreak) particle).type;
        }

        return this;
    }

    @Override
    public ParticlePotionBreak clone() {
        return new ParticlePotionBreak().inherit(this);
    }

    @Override
    protected Packet getStrangePacket(Location location) {
        return new PacketPlayOutWorldEvent(
                Effect.POTION_BREAK.getId(), new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                type.getDamageValue(), false
        );
    }

    public void setPotionType(PotionType type) {
        Validate.notNull(type, "Potion type can't be null!");

        this.type = type;
    }

    public PotionType getPotionType() {
        return type;
    }
}
