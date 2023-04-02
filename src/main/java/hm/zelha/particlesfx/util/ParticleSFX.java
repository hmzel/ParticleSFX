package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.*;
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
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param size radius of the star
     * @param particleFrequency particle amount
     * @return the shape displaying the star
     */
    public static ParticleLine star(Particle particle, LocationSafe center, double size, int particleFrequency) {
        return new ParticleLine(
                particle, particleFrequency,
                new LocationSafe(center).add(0, 0, size),
                new LocationSafe(center).add(size * -0.2, 0, size * 0.33),
                new LocationSafe(center).add(-size, 0, size * 0.33),
                new LocationSafe(center).add(size * -0.33, 0, size * -0.2),
                new LocationSafe(center).add(size * -0.56, 0, size * -0.89),
                new LocationSafe(center).add(0, 0, size * -0.46),
                new LocationSafe(center).add(size * 0.56, 0, size * -0.89),
                new LocationSafe(center).add(size * 0.33, 0, size * -0.2),
                new LocationSafe(center).add(size, 0, size * 0.33),
                new LocationSafe(center).add(size * 0.2, 0, size * 0.33),
                new LocationSafe(center).add(0, 0, size)
        );
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param size radius of the pentagram
     * @param particleFrequency particle amount
     * @return the shape displaying the pentagram
     */
    public static ParticleLine pentagram(Particle particle, LocationSafe center, double size, int particleFrequency) {
        return new ParticleLine(
                particle, particleFrequency,
                new LocationSafe(center).add(0, 0, size),
                new LocationSafe(center).add(size * -0.56, 0, size * -0.89),
                new LocationSafe(center).add(size, 0, size * 0.33),
                new LocationSafe(center).add(-size, 0, size * 0.33),
                new LocationSafe(center).add(size * 0.56, 0, size * -0.89),
                new LocationSafe(center).add(0, 0, size)
        );
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param xRadius what the X radius should be
     * @param zRadius what the Z radius should be
     * @param circleSize radius of the circles that the donut will be made out of
     * @param circleFrequency number of circles to make the donut out of
     * @param particleFrequency particle amount
     * @return the shape displaying the donut
     */
    public static ParticleCylinder donut(Particle particle, LocationSafe center, double xRadius, double zRadius, double circleSize, int circleFrequency, int particleFrequency) {
        CircleInfo[] circles = new CircleInfo[circleFrequency];

        for (int i = 0; i < circleFrequency; i++) {
            double radian = Math.PI * 2 / circleFrequency * i;

            circles[i] = new CircleInfo(new LocationSafe(center).add(xRadius * Math.cos(radian), 0, zRadius * Math.sin(radian)), circleSize, circleSize, 90, (360D / circleFrequency * i), 0);
        }

        return new ParticleCylinder(particle, circleFrequency, particleFrequency, circles);
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param radius what the radius should be
     * @param circleSize radius of the circles that the donut will be made out of
     * @param circleFrequency number of circles to make the donut out of
     * @param particleFrequency particle amount
     * @return the shape displaying the donut
     */
    public static ParticleCylinder donut(Particle particle, LocationSafe center, double radius, double circleSize, int circleFrequency, int particleFrequency) {
        return donut(particle, center, radius, radius, circleSize, circleFrequency, particleFrequency);
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param radius what the radius should be
     * @param circleFrequency number of circles to make the donut out of
     * @param particleFrequency particle amount
     * @return the shape displaying the donut
     */
    public static ParticleCylinder donut(Particle particle, LocationSafe center, double radius, int circleFrequency, int particleFrequency) {
        return donut(particle, center, radius, radius, radius / 5, circleFrequency, particleFrequency);
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param radius what the radius should be
     * @param particleFrequency particle amount
     * @return the shape displaying the donut
     */
    public static ParticleCylinder donut(Particle particle, LocationSafe center, double radius, int particleFrequency) {
        return donut(particle, center, radius, radius, radius / 5, (int) (radius * 15), particleFrequency);
    }

    /**
     * @param particle particle to use
     * @param bottom where the bottom of the shape should be
     * @param xRadius what the X radius should be
     * @param zRadius what the Z radius should be
     * @param height how high this shape should extend
     * @param particleFrequency particle amount
     * @return the shape displaying the tornado
     */
    public static ParticleSpiral tornado(Particle particle, LocationSafe bottom, double xRadius, double zRadius, double height, int particleFrequency) {
        ParticleSpiral spi = new ParticleSpiral(particle, 15, 1, particleFrequency / 2,
                new CircleInfo(bottom, 0, 0),
                new CircleInfo(new LocationSafe(bottom).add(0, height * 0.75, 0), xRadius * 0.6, zRadius * 0.6),
                new CircleInfo(new LocationSafe(bottom).add(0, height, 0), xRadius, zRadius)
        );

        spi.addMechanic(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, ((mechParticle, current, addition, count) -> {
            CircleInfo top = spi.getCircleInfo(spi.getCircleInfoAmount() - 1);

            if (count == 1) {
                for (int i = 0; i < spi.getCircleInfoAmount(); i++) {
                    //i cant figure out a way to do this that makes the spin look decent at every size so this is good enough
                    spi.getCircleInfo(i).setYaw(spi.getCircleInfo(i).getYaw() - Math.toDegrees(Math.PI * 0.04 * Math.sqrt((Math.pow(top.getXRadius(), 2) + Math.pow(top.getZRadius(), 2)) / 2)));
                }
            }

            mechParticle.setOffsetX(top.getXRadius());
            mechParticle.setOffsetY(top.getCenter().distance(spi.getCircleInfo(0).getCenter()) / 10);
            mechParticle.setOffsetZ(top.getZRadius());

            if (!spi.getPlayers().isEmpty()) {
                mechParticle.displayForPlayers(top.getCenter(), spi.getPlayers());
            } else {
                mechParticle.display(top.getCenter());
            }

            mechParticle.setOffsetX(0);
            mechParticle.setOffsetY(0);
            mechParticle.setOffsetZ(0);
        }));

        return spi;
    }

    /**
     * @param particle particle to use
     * @param bottom where the bottom of the shape should be
     * @param radius what the radius should be
     * @param height how high this shape should extend
     * @param particleFrequency particle amount
     * @return the shape displaying the tornado
     */
    public static ParticleSpiral tornado(Particle particle, LocationSafe bottom, double radius, double height, int particleFrequency) {
        return tornado(particle, bottom, radius, radius, height, particleFrequency);
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