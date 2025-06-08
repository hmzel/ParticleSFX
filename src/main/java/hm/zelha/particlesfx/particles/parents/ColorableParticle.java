package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.util.Color;

import javax.annotation.Nullable;

public interface ColorableParticle extends IParticle {
    ColorableParticle inherit(Particle particle);

    ColorableParticle clone();

    /**
     * @param color color to set, null if you want random coloring
     */
    void setColor(@Nullable Color color);

    void setColor(int red, int green, int blue);

    Particle setSpeed(double speed);

    Particle setRadius(int radius);

    /**
     * nullable to allow for randomly colored particles without being complicated
     *
     * @return color this particle is using
     */
    @Nullable
    Color getColor();
}