package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Color;
import org.bukkit.Effect;

import javax.annotation.Nullable;

public class ColorableParticle extends Particle {

    private Color color;
    private int brightness;

    protected ColorableParticle(Effect particle, @Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.color = color;
        this.brightness = brightness;
    }

    /**
     * @param color color to set, null if you want random coloring
     */
    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public void setColor(int red, int green, int blue) {
        this.color = Color.fromRGB(red, green, blue);
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     * nullable to allow for randomly colored particles without use of boolean constructors
     */
    @Nullable
    public Color getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }
}
