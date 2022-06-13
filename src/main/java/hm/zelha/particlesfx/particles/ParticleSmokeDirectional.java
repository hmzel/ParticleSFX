package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.BlockDirectional;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

/**
 * since Effect.SMOKE is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticleSmokeDirectional extends Particle implements BlockDirectional {
    public ParticleSmokeDirectional(BlockDirection direction, int count) {
        super(Effect.SMOKE, 0, 0, 0, 0, count, 64);

        super.data = direction;
    }

    public ParticleSmokeDirectional(BlockDirection direction) {
        this(direction, 1);
    }

    public ParticleSmokeDirectional(int count) {
        this(BlockDirection.NONE, count);
    }

    public ParticleSmokeDirectional() {
        this(BlockDirection.NONE, 1);
    }

    @Override
    public void setDirection(BlockDirection direction) {
        super.data = direction;
    }

    @Override
    public BlockDirection getDirection() {
        return (BlockDirection) super.data;
    }
}
