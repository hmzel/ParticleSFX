package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Directional;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

/**
 * since Effect.SMOKE is Type.VISUAL, the radius, speed, and offsets are unused internally, and the default radius is quite small.
 * <p></p>
 * Type.VISUAL effects are also locked to specific coordinates of the block they're played on because their internal system uses BlockPosition
 */
public class ParticleSmokeDirectional extends Particle implements Directional {
    public ParticleSmokeDirectional(Direction direction, int count) {
        super(Effect.SMOKE, 0, 0, 0, 0, count, 64);

        super.data = direction;
    }

    public ParticleSmokeDirectional(Direction direction) {
        this(direction, 1);
    }

    public ParticleSmokeDirectional(int count) {
        this(Direction.NONE, count);
    }

    public ParticleSmokeDirectional() {
        this(Direction.NONE, 1);
    }

    @Override
    public void setDirection(Direction direction) {
        super.data = direction;
    }

    @Override
    public Direction getDirection() {
        return (Direction) super.data;
    }
}
