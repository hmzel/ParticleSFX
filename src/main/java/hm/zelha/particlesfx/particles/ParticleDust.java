package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Effect;

import javax.annotation.Nullable;

public class ParticleDust extends Particle implements ColorableParticle {

    private Color color;
    private int brightness;

    public ParticleDust(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(Effect.COLOURED_DUST, offsetX, offsetY, offsetZ, 1, count, 0);

        Validate.isTrue(brightness >= 0 && brightness <= 100, "Brightness must be between 0 and 100!");

        this.color = color;
        this.brightness = brightness;
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDust(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleDust(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleDust(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleDust(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleDust() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    @Override
    public void setColor(int red, int green, int blue) {
        this.color = Color.fromRGB(red, green, blue);
    }

    @Override
    public void setBrightness(int brightness) {
        Validate.isTrue(brightness >= 0 && brightness <= 100, "Brightness must be between 0 and 100!");

        this.brightness = brightness;
    }

    @Override@Nullable
    public Color getColor() {
        return color;
    }

    @Override
    public int getBrightness() {
        return brightness;
    }
}
