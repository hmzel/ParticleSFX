package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.Color;
import net.minecraft.server.v1_11_R1.EnumParticle;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public abstract class ColorableParticle extends Particle {

    protected Color color;
    protected int brightness;

    protected ColorableParticle(EnumParticle particle, @Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count);

        setColor(color);
        setBrightness(brightness);
    }

    @Override
    public ColorableParticle inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ColorableParticle) {
            color = ((ColorableParticle) particle).color;
            brightness = ((ColorableParticle) particle).brightness;
        }

        return this;
    }

    @Override
    public abstract ColorableParticle clone();

    @Override
    protected Vector getXYZ(Location location) {
        Vector xyz = super.getXYZ(location);

        if (color != null) {
            xyz.add(generateFakeOffset());
        }

        return xyz;
    }

    @Override
    protected Vector getOffsets(Location location) {
        Vector offsets = super.getOffsets(location);

        if (color != null) {
            offsets.setX(color.getRed() / 255D);
            offsets.setY(color.getGreen() / 255D);
            offsets.setZ(color.getBlue() / 255D);
        }

        return offsets;
    }

    @Override
    protected float getPacketSpeed() {
        if (color != null) return (float) (brightness * 0.01);

        return super.getPacketSpeed();
    }

    @Override
    protected int getPacketCount() {
        if (color != null) return 0;

        return super.getCount();
    }

    /**
     * @param color color to set, null if you want random coloring
     */
    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public void setColor(int red, int green, int blue) {
        if (color != null && !color.isLocked()) {
            color.setRed(red);
            color.setGreen(green);
            color.setBlue(blue);
        } else {
            this.color = new Color(red, green, blue);
        }
    }

    /**
     * @param brightness how saturated the color should be, from 0-100
     */
    public void setBrightness(int brightness) {
        Validate.isTrue(brightness >= 0 && brightness <= 100, "Brightness must be between 0 and 100!");

        this.brightness = brightness;
    }

    /**
     * nullable to allow for randomly colored particles without being complicated
     *
     * @return color this particle is using
     */
    @Nullable
    public Color getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }
}