package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface ShapeDisplayMechanic {
    public void apply(Particle particle, Location current, Vector addition);
}
