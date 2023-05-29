package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.particles.DustColorTransitionOptions;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleMulticoloredDust extends ParticleColoredDust implements SizeableParticle {

    private Color transition = null;

    public ParticleMulticoloredDust(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(color, size, offsetX, offsetY, offsetZ, count);

        particle = new ParticleParamDustTransition(color, transition, size, pureColor);
    }

    public ParticleMulticoloredDust(double size, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, size, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMulticoloredDust(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        this(color, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMulticoloredDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleMulticoloredDust(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMulticoloredDust(double size, double offsetX, double offsetY, double offsetZ) {
        this(null, size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMulticoloredDust(double offsetX, double offsetY, double offsetZ) {
        this(null, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleMulticoloredDust(@Nullable Color color, double size, int count) {
        this(color, size, 0, 0, 0, count);
    }

    public ParticleMulticoloredDust(@Nullable Color color, double size) {
        this(color, size, 0, 0, 0, 1);
    }

    public ParticleMulticoloredDust(@Nullable Color color) {
        this(color, 1, 0, 0, 0, 1);
    }

    public ParticleMulticoloredDust(int count) {
        this(null, 1, 0, 0, 0, count);
    }

    public ParticleMulticoloredDust() {
        this(null, 1, 0, 0, 0, 1);
    }

    @Override
    public ParticleMulticoloredDust inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleMulticoloredDust) {
            this.transition = ((ParticleMulticoloredDust) particle).transition;
        }

        return this;
    }

    @Override
    public ParticleMulticoloredDust clone() {
        return new ParticleMulticoloredDust().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (((ParticleParamDustTransition) particle).check(color, transition, size, pureColor)) {
            particle = new ParticleParamDustTransition(color, transition, size, pureColor);
        }

        super.display(location, players);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location);
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.setX(offsetX).setY(offsetY).setZ(offsetZ);
    }

    @Override
    protected float getPacketSpeed() {
        return (float) speed;
    }

    @Override
    protected int getPacketCount() {
        return count;
    }

    /**
     * @param transition the color this particle will transition to as it fades. doesn't work with pureColor
     */
    public void setTransitionColor(@Nullable Color transition) {
        this.transition = transition;
    }

    /**
     * @return the color this particle will transition to as it fades. doesn't work with pureColor
     */
    public Color getTransitionColor() {
        return transition;
    }


    private static class ParticleParamDustTransition extends DustColorTransitionOptions {

        private static final ThreadLocalRandom rng = ThreadLocalRandom.current();
        private final Color color;
        private final Color transition;
        private final double size;
        private final boolean pureColor;

        private ParticleParamDustTransition(Color color, Color transition, double size, boolean pureColor) {
            super(new Vector3f(rng.nextFloat(), rng.nextFloat(), rng.nextFloat()), new Vector3f(rng.nextFloat(), rng.nextFloat(), rng.nextFloat()), (float) size);

            this.color = (color != null) ? color.clone() : null;
            this.transition = (transition != null) ? transition.clone() : null;
            this.size = size;
            this.pureColor = pureColor;

            if (color != null) {
                g.set(color.getRed(), color.getGreen(), color.getBlue());
                g.div(255F);
                d().set(g);
            }

            if (transition != null) {
                d().set(transition.getRed(), transition.getGreen(), transition.getBlue());
                d().div(255F);
            }

            if (pureColor) {
                g.mul(Float.MAX_VALUE);
            }
        }

        public boolean check(Color color, Color transition, double size, boolean pureColor) {
            if (color != null && !color.equals(this.color)) return true;
            if (transition != null && !transition.equals(this.transition)) return true;
            if (color == null && this.color != null) return true;
            if (transition == null && this.transition != null) return true;
            if (size != this.size) return true;

            return pureColor != this.pureColor;
        }
    }
}