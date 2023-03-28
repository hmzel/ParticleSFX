package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.ParticleLineCurved;
import hm.zelha.particlesfx.shapers.ParticlePolygon;
import hm.zelha.particlesfx.shapers.ParticlePolygonFilled;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;

/** contains static utility methods with various functions meant to showcase how this dependency can be used. */
public final class ParticleSFX {

    private static Plugin plugin = null;

    private ParticleSFX() {
    }

    /**
     * Makes a 3D cube.
     *
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param size the X and Z radius will be the same as the given size, and the Y radius will be the size * 0.75.
     * @param particleFrequency particle amount
     * @return the shape displaying the cube
     */
    public static ParticlePolygon cube(Particle particle, LocationSafe center, double size, int particleFrequency) {
        return new ParticlePolygon(particle, center, 4, 2, size, size * 0.75, size, particleFrequency);
    }

    /**
     * Makes a filled 3D cube.
     *
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param size the X and Z radius will be the same as the given size, and the Y radius will be the size * 0.75.
     * @param particleFrequency particle amount
     * @return the shape displaying the cube
     */
    public static ParticlePolygonFilled cubeFilled(Particle particle, LocationSafe center, double size, int particleFrequency) {
        return new ParticlePolygonFilled(particle, center, 4, 2, size, size * 0.75, size, particleFrequency);
    }

    /**
     * the size parameter isn't followed exactly and will probably need some fine-tuning to get correct. <br>
     * if it helps, a size of 1 goes up from the center 1.2 blocks and down from the center 1.5 blocks. <br>
     * it'd be pretty complicated to fix and i don't want to spend that much time on an util method that barely anyone will use.
     *
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param size radius of the heart
     * @param particleFrequency particle amount
     * @return the shape displaying the heart
     */
    public static ParticleLineCurved heart2D(Particle particle, LocationSafe center, double size, int particleFrequency) {
        ParticleLineCurved heart = new ParticleLineCurved(particle, particleFrequency, new LocationSafe(center).add(0, 0, -size - (size * 0.4)), new LocationSafe(center).add(size * 1.2, 0, size - (size * 0.4)), new LocationSafe(center).add(-(size * 1.2), 0, size - (size * 0.4)), new LocationSafe(center).add(0, 0, -size - (size * 0.4)));
        Location[] locations = heart.getLocations();
        
        heart.addCurve(new CurveInfo(size * 0.4, locations[0].distance(locations[1]), locations[0].distance(locations[1]) / 2, 90, -65, 0));
        heart.addCurve(new CurveInfo(size * 0.7, locations[1].distance(locations[2]) / 2, locations[1].distance(locations[2]) / 4, 90, -20, 0));
        heart.addCurve(new CurveInfo(size * 0.7, locations[1].distance(locations[2]) / 2, locations[1].distance(locations[2]) / 4, 90, 20, 0));
        heart.addCurve(new CurveInfo(size * 0.4, locations[2].distance(locations[3]), locations[2].distance(locations[3]) / 2, 90, 65, 0));

        return heart;
    }

    /**
     * @param toFace the location to get the direction towards
     * @param location the starting location
     * @return a double array of the pitch and yaw of the direction where the pitch is [0] and the yaw is [1]
     */
    public static double[] getDirection(Location toFace, Location location) {
        Validate.isTrue(toFace.getWorld().equals(location.getWorld()), "Locations must be in the same world!");

        double x = toFace.getX() - location.getX();
        double y = toFace.getY() - location.getY();
        double z = toFace.getZ() - location.getZ();
        //i genuinely have no bloody clue why this works. if anyone sees this and understands what the heck this is please tell me im very curious
        //(code stolen from Location.setDirection(Vector) and condensed according to my code conventions)
        double pitch = Math.toDegrees(Math.atan(-y / Math.sqrt(NumberConversions.square(x) + NumberConversions.square(z)))) - 90;
        double yaw = Math.toDegrees((Math.atan2(-x, z) + (Math.PI * 2)) % (Math.PI * 2));

        return new double[] {pitch, yaw};
    }

    /**
     * @param plugin the plugin for all of this dependency's BukkitRunnables to be assigned to
     */
    public static void setPlugin(Plugin plugin) {
        ParticleSFX.plugin = plugin;
    }

    /**
     * @return the plugin that this dependency is using for BukkitRunnables
     */
    public static Plugin getPlugin() {
        return plugin;
    }
}