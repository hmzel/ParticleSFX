package hm.zelha.particlesfx.particles.parents;

import org.bukkit.util.Vector;

public interface VelocityParticle {

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by 0.05 in all default implementations of
     * VelocityParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     *
     * @param vector velocity to set
     */
    public void setVelocity(Vector vector);

    /**
     * since particle velocity is very volatile, the given velocity is automatically multiplied by 0.05 in all default implementations of
     * VelocityParticle to prevent new users from setting the velocity to 1, 1, 1 and watching the particle fly into the sun. : )
     *
     * @param x x velocity
     * @param y y velocity
     * @param z z velocity
     */
    public void setVelocity(double x, double y, double z);

    public Vector getVelocity();
}
