package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.Potionable;
import org.bukkit.Effect;
import org.bukkit.potion.PotionType;

/**
 * NOTE: this effect makes sound!
 * <p></p>
 * since Effect.POTION_BREAK is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticlePotionBreak extends Particle implements Potionable {
    public ParticlePotionBreak(PotionType type, int count) {
        super(Effect.POTION_BREAK, 0, 0, 0, 0, count, 64);

        super.data = type;
    }

    public ParticlePotionBreak(PotionType type) {
        this(type, 1);
    }

    public ParticlePotionBreak(int count) {
        this(PotionType.WATER, count);
    }

    public ParticlePotionBreak() {
        this(PotionType.WATER, 1);
    }

    @Override
    public void setPotionType(PotionType potion) {
        super.data = potion;
    }

    @Override
    public PotionType getPotionType() {
        return (PotionType) data;
    }
}
