package hm.zelha.particlesfx.particles;

import com.mojang.math.Vector3fa;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.particles.DustColorTransitionOptions;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleMulticoloredDust extends ParticleColoredDust implements SizeableParticle {

    private Color transition = null;

    public ParticleMulticoloredDust(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(color, size, offsetX, offsetY, offsetZ, count);

        particle = new ParticleParamDustTransition(1, 1, 1, 1, 1, 1, (float) size, Color.WHITE, Color.WHITE, false);
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
            this.particle = ((ParticleMulticoloredDust) particle).particle;
        }

        return this;
    }

    @Override
    public ParticleMulticoloredDust clone() {
        return new ParticleMulticoloredDust().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        ParticleParamDustTransition dust = (ParticleParamDustTransition) particle;

        if ((color != null && !dust.color.equals(color)) || (transition != null && !dust.transition.equals(transition)) || dust.size != size || dust.pureColor != pureColor) {
            float red = 1, green = 1, blue = 1, red2, green2, blue2;

            if (color != null) {
                red = color.getRed() / 255F;
                green = color.getGreen() / 255F;
                blue = color.getBlue() / 255F;
            }

            if (transition != null) {
                red2 = transition.getRed() / 255F;
                green2 = transition.getGreen() / 255F;
                blue2 = transition.getBlue() / 255F;
            } else {
                red2 = red;
                green2 = green;
                blue2 = blue;
            }

            if (pureColor) {
                red *= Float.MAX_VALUE;
                green *= Float.MAX_VALUE;
                blue *= Float.MAX_VALUE;
            }

            particle = new ParticleParamDustTransition(red, green, blue, red2, green2, blue2, (float) size, color, transition, pureColor);
        }

        super.display(location, players);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location);
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.zero().setX(offsetX).setY(offsetY).setZ(offsetZ);
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

        private final Color color;
        private final Color transition;
        private final double size;
        private final boolean pureColor;

        public ParticleParamDustTransition(float r, float g, float b, float r2, float g2, float b2, float size, Color color, Color transition, boolean pureColor) {
            super(new Vector3fa(r, g, b), new Vector3fa(r2, g2, b2), size);

            this.color = (color == null) ? Color.WHITE : color.clone();
            this.transition = (transition == null) ? Color.WHITE : transition.clone();
            this.size = size;
            this.pureColor = pureColor;
        }
    }
}