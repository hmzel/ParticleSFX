package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Directional;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

/**
 * sadly, the directional version of the smoke particle is locked to the center of whatever block it's played on
 * <p></p>
 * since Effect.SMOKE is Type.VISUAL, the radius and speed is fixed, and the radius is quite small.
 */
public class ParticleSmokeDirectional extends Particle implements Directional {
    public ParticleSmokeDirectional(Direction direction, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.SMOKE, offsetX, offsetY, offsetZ, 0, count, 64);

        super.data = direction;
    }

    public ParticleSmokeDirectional(Direction direction, double offsetX, double offsetY, double offsetZ) {
        this(direction, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeDirectional(double offsetX, double offsetY, double offsetZ) {
        this(Direction.NONE, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSmokeDirectional(Direction direction, int count) {
        this(direction, 0, 0, 0, count);
    }

    public ParticleSmokeDirectional(Direction direction) {
        this(direction, 0, 0, 0, 1);
    }

    public ParticleSmokeDirectional(int count) {
        this(Direction.NONE, 0, 0, 0, count);
    }

    public ParticleSmokeDirectional() {
        this(Direction.NONE, 0, 0, 0, 1);
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
