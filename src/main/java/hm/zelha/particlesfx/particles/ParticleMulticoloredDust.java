package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.network.PacketDataSerializer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleMulticoloredDust extends ParticleColoredDust implements SizeableParticle {

    private Color transition = null;

    public ParticleMulticoloredDust(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(color, size, offsetX, offsetY, offsetZ, count);

        particle = new ParticleParamDustTransition();
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


    private class ParticleParamDustTransition extends DustColorTransitionOptions {
        public ParticleParamDustTransition() {
            super(null, null, 0);
        }

        @Override
        public void a(PacketDataSerializer var0) {
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

            var0.writeFloat(red);
            var0.writeFloat(green);
            var0.writeFloat(blue);
            var0.writeFloat((float) size);
            var0.writeFloat(red2);
            var0.writeFloat(green2);
            var0.writeFloat(blue2);
        }
    }
}