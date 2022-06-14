package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Color;

import javax.annotation.Nullable;

public interface ColorableParticle {

    /** @param color color to set, null if you want random coloring */
    public void setColor(@Nullable Color color);

    public void setColor(int red, int green, int blue);

    /** nullable to allow for randomly colored particles without use of boolean constructors*/
    @Nullable
    public Color getColor();
}
