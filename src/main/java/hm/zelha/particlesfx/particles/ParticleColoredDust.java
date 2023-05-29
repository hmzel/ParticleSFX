package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.particles.ParticleParamRedstone;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleColoredDust extends ColorableParticle implements SizeableParticle {

    protected boolean pureColor = false;
    protected double size;

    public ParticleColoredDust(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super("", color, 100, offsetX, offsetY, offsetZ, count);

        particle = new ParticleParamDust(color, size, pureColor);

        setSize(size);
    }

    public ParticleColoredDust(double size, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, size, offsetX, offsetY, offsetZ, count);
    }

    public ParticleColoredDust(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        this(color, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleColoredDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleColoredDust(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleColoredDust(double size, double offsetX, double offsetY, double offsetZ) {
        this(null, size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleColoredDust(double offsetX, double offsetY, double offsetZ) {
        this(null, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleColoredDust(@Nullable Color color, double size, int count) {
        this(color, size, 0, 0, 0, count);
    }

    public ParticleColoredDust(@Nullable Color color, double size) {
        this(color, size, 0, 0, 0, 1);
    }

    public ParticleColoredDust(@Nullable Color color) {
        this(color, 1, 0, 0, 0, 1);
    }

    public ParticleColoredDust(int count) {
        this(null, 1, 0, 0, 0, count);
    }

    public ParticleColoredDust() {
        this(null, 1, 0, 0, 0, 1);
    }

    @Override
    public ParticleColoredDust inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleColoredDust) {
            pureColor = ((ParticleColoredDust) particle).pureColor;
            size = ((ParticleColoredDust) particle).size;
        }

        return this;
    }

    @Override
    public ParticleColoredDust clone() {
        return new ParticleColoredDust().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (particle instanceof ParticleParamDust) {
            ParticleParamDust dust = ((ParticleParamDust) particle);

            if ((color != null && !color.equals(dust.color)) || (color == null && dust.color != null) || size != dust.size || pureColor != dust.pureColor) {
                particle = new ParticleParamDust(color, size, pureColor);
            }
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

    /** @deprecated Unused in this class. */
    @Deprecated
    @Override
    public void setBrightness(int brightness) {
    }

    /** only changes between 0 and 4. */
    @Override
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * if this is set to true, the particle will use derivatives of Float.MAX_VALUE to try and eliminate the variations
     * in color when normally using colored dust.
     * aka no purple or brown or other colors like that
     *
     * @param pureColor whether the color should be pure
     * @return this object
     */
    public ParticleColoredDust setPureColor(boolean pureColor) {
        this.pureColor = pureColor;

        return this;
    }

    /** @deprecated Unused in this class. */
    @Deprecated
    @Override
    public int getBrightness() {
        return 100;
    }

    /** only changes between 0 and 4. */
    @Override
    public double getSize() {
        return size;
    }

    /**
     * if this is set to true, the particle will use derivatives of Float.MAX_VALUE to try and eliminate the variations
     * in color when normally using colored dust.
     * aka no purple or brown or other colors like that
     *
     * @return whether the color should be pure
     */
    public boolean isPureColor() {
        return pureColor;
    }


    private static class ParticleParamDust extends ParticleParamRedstone {

        private static final ThreadLocalRandom rng = ThreadLocalRandom.current();
        private final Color color;
        private final double size;
        private final boolean pureColor;

        public ParticleParamDust(Color color, double size, boolean pureColor) {
            super(new Vector3f(rng.nextFloat(), rng.nextFloat(), rng.nextFloat()), (float) size);

            this.color = (color != null) ? color.clone() : null;
            this.size = size;
            this.pureColor = pureColor;

            if (color != null) {
                g.set(color.getRed(), color.getGreen(), color.getBlue());
                g.div(255F);
            }

            if (pureColor) {
                g.mul(Float.MAX_VALUE);
            }
        }
    }
}