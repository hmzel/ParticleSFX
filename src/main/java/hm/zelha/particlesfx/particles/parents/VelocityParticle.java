package hm.zelha.particlesfx.particles.parents;

import org.bukkit.util.Vector;

public interface VelocityParticle {

    public void setVelocity(Vector vector);

    public void setVelocity(double x, double y, double z);

    public Vector getVelocity();
}
