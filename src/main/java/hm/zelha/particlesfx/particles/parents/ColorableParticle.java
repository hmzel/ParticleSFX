package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Color;

public interface ColorableParticle {

    public void setColor(Color color);

    public void setColor(int red, int green, int blue);

    public Color getColor();
}
