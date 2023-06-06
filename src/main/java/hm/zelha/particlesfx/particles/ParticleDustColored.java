package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleDustColored extends ColorableParticle {

    private boolean pureColor = false;

    public ParticleDustColored(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.REDSTONE, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustColored(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustColored(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleDustColored(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleDustColored(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleDustColored(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleDustColored(double size) {
        this(null, size, 0, 0, 0, 1);
    }

    public ParticleDustColored() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    public ParticleDustColored inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleDustColored) {
            pureColor = ((ParticleDustColored) particle).pureColor;
        }

        return this;
    }

    @Override
    public ParticleDustColored clone() {
        return new ParticleDustColored().inherit(this);
    }

    @Override
    protected Vector getOffsets(Location location) {
        Vector offsets = super.getOffsets(location);

        if (color == null) return offsets;

        if (pureColor) {
            offsets.setX(Float.MAX_VALUE * (color.getRed() / 255D));
            offsets.setY(Float.MAX_VALUE * (color.getGreen() / 255D));
            offsets.setZ(Float.MAX_VALUE * (color.getBlue() / 255D));

            if (offsets.getX() == 0) {
                offsets.setX(Float.MIN_VALUE);
            }

            if (offsets.getY() == 0) {
                offsets.setY(Float.MIN_VALUE);
            }

            if (offsets.getZ() == 0) {
                offsets.setZ(Float.MIN_VALUE);
            }
        } else if (color.getRed() == 0) {
            offsets.setX(0.0001);
        }

        return offsets;
    }

    @Override
    protected float getPacketSpeed() {
        if (pureColor) return 1;

        return super.getPacketSpeed();
    }

    /**
     * if this is set to true, the display method will use derivatives of Float.MAX_VALUE to try and eliminate the variations
     * in color when normally using reddust.
     * <br><br>
     * however, if this is the case brightness is unused and will always be 100 internally.
     * <br><br>
     * and you're kinda locked into specific colors because lowering the brightness will make it not pure
     * aka no purple or brown or other colors like that
     *
     * @param pureColor whether the color should be pure
     * @return this object
     */
    public ParticleDustColored setPureColor(boolean pureColor) {
        this.pureColor = pureColor;

        return this;
    }

    public boolean isPureColor() {
        return pureColor;
    }
}