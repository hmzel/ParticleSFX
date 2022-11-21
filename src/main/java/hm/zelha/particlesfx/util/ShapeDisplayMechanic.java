package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface ShapeDisplayMechanic {

    /**
     * @param particle particle to be displayed <p></p>
     * @param current the location the vector will be/has been added to, in some cases
     *               this is the last position the particle was displayed, in others it is the center of the shape <p></p>
     * @param addition the vector that will be/has been added to the location before the particle is displayed
     */
    public void apply(Particle particle, Location current, Vector addition);

    public enum Phase {
        BEFORE_ROTATION,
        AFTER_ROTATION,
        AFTER_DISPLAY
    }
}
