package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.ParticleCircle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface ShapeDisplayMechanic {

    /**
     * @param particle particle to be displayed <br><br>
     * @param current the location the vector will be/has been added to, in some cases
     *               this is the last position the particle was displayed, in others it is the center of the shape <br><br>
     * @param addition the vector that will be/has been added to the location before the particle is displayed <br><br>
     * @param count current amount of particles that have been displayed (set back to 0 once the entire shape is displayed)
     */
    void apply(Particle particle, Location current, Vector addition, int count);

    enum Phase {
        /**
         * Ran before any particles are displayed on every call of the display() method.
         */
        BEFORE_DISPLAY,
        /**
         * Ran before any particles are displayed on every full display of the shape.
         */
        BEFORE_DISPLAY_FULL,
        /**
         * Ran before rotation is applied to the location the particle will be displayed to. <br><br>
         * Always ran before the particle is displayed regardless of if rotation is applied in this class or not.
         */
        BEFORE_ROTATION,
        /**
         * Ran after rotation is applied to the location the particle will be displayed to. <br><br>
         * Only ran if rotation is applied in this class. (ex. {@link ParticleCircle})
         */
        AFTER_ROTATION,
        /**
         * Ran after a particle is displayed at the current location.
         */
        AFTER_DISPLAY_PARTICLE,
        /**
         * Ran after all particles are displayed on every call of the display() method.
         */
        AFTER_DISPLAY,
        /**
         * Ran after all particles are displayed on every full display of the shape.
         */
        AFTER_DISPLAY_FULL
    }
}