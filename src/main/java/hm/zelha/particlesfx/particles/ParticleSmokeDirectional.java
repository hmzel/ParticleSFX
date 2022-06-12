package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Directional;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Effect;

/** since Effect.SMOKE is Type.VISUAL, the radius is fixed and quite small */
public class ParticleSmokeDirectional extends Particle implements Directional {
    public ParticleSmokeDirectional(Direction direction, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        super(Effect.SMOKE, offsetX, offsetY, offsetZ, speed, count, 64);

        super.data = direction;
    }

    public ParticleSmokeDirectional(Direction direction, double offsetX, double offsetY, double offsetZ) {
        this(direction, offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleSmokeDirectional(double offsetX, double offsetY, double offsetZ) {
        this(Direction.NONE, offsetX, offsetY, offsetZ, 0, 1);
    }

    public ParticleSmokeDirectional(Direction direction, double speed, int count) {
        this(direction, 0, 0, 0, speed, count);
    }

    public ParticleSmokeDirectional(double speed, int count) {
        this(Direction.NONE, 0, 0, 0, speed, count);
    }

    public ParticleSmokeDirectional(Direction direction) {
        this(direction, 0, 0, 0, 0, 1);
    }

    public ParticleSmokeDirectional(double speed) {
        this(Direction.NONE, 0, 0, 0, speed, 1);
    }

    public ParticleSmokeDirectional(int count) {
        this(Direction.NONE, 0, 0, 0, 0, count);
    }

    public ParticleSmokeDirectional() {
        this(Direction.NONE, 0, 0, 0, 0, 1);
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
