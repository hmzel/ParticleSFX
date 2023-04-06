package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

/** contains static utility methods with various functions meant to showcase how this dependency can be used. */
public final class ParticleSFX {

    private static final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private static Plugin plugin = null;

    private ParticleSFX() {
    }

    /**
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
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param sides how many sides the pyramid should have
     * @param size the X, Y, and Z radius of this pyramid
     * @param particleFrequency particle amount
     * @return the shape displaying the pyramid
     */
    public static ParticlePolygon pyramid(Particle particle, LocationSafe center, int sides, double size, int particleFrequency) {
        Validate.isTrue(sides > 2, "Pyramids must have more than 2 sides!");

        return new ParticlePolygon(particle, center, particleFrequency,
                new PolygonLayer(1, 0, 0, size / 2),
                new PolygonLayer(sides, size, size, -(size / 2))
        );
    }

    /**
     * @param particle particle to use
     * @param center the center of where the shape should be
     * @param sides how many sides the pyramid should have
     * @param size the X, Y, and Z radius of this pyramid
     * @param particleFrequency particle amount
     * @return the shape displaying the pyramid
     */
    public static ParticlePolygonFilled pyramidFilled(Particle particle, LocationSafe center, int sides, double size, int particleFrequency) {
        Validate.isTrue(sides > 2, "Pyramids must have more than 2 sides!");

        return new ParticlePolygonFilled(particle, center, particleFrequency,
                new PolygonLayer(1, 0, 0, size / 2),
                new PolygonLayer(sides, size, size, -(size / 2)),
                new PolygonLayer(1, 0, 0, -(size / 2))
        );
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
     * @param xRadius what the x radius should be
     * @param zRadius what the z radius should be
     * @param height what the height should be
     * @param particleFrequency particle amount
     * @return the shape displaying the mushroom
     */
    public static ParticleCylinder mushroom(Particle particle, LocationSafe bottom, double xRadius, double zRadius, double height, int particleFrequency) {
        ParticleCylinder shroom = new ParticleCylinder(particle, (int) ((((xRadius + zRadius) / 2) + height) * 2.5), particleFrequency,
                new CircleInfo(new LocationSafe(bottom), xRadius * 0.225, zRadius * 0.225),
                new CircleInfo(new LocationSafe(bottom).add(0, height * 0.15, 0), xRadius * 0.15, xRadius * 0.15),
                new CircleInfo(new LocationSafe(bottom).add(0, height * 0.85, 0), xRadius * 0.15, xRadius * 0.15),
                new CircleInfo(new LocationSafe(bottom).add(0, height, 0), 0, 0)
        );

        for (int i = 0; i < 15; i++) {
            double radian = Math.PI * 0.5 / 15 * i;

            shroom.addCircleInfo(new CircleInfo(new LocationSafe(bottom).add(0, (height * 0.71) + (height * 0.27 * Math.cos(radian)), 0), xRadius * Math.sin(radian), zRadius * Math.sin(radian)));
        }

        shroom.addCircleInfo(new CircleInfo(new LocationSafe(bottom).add(0, height * 0.71, 0), xRadius * 0.9, zRadius * 0.9));
        shroom.addCircleInfo(new CircleInfo(new LocationSafe(bottom).add(0, height * 0.85, 0), xRadius * 0.15, zRadius * 0.15));

        return shroom;
    }

    /**
     * @param particle particle to use
     * @param bottom where the bottom of the shape should be
     * @param radius what the radius should be
     * @param height what the height should be
     * @param particleFrequency particle amount
     * @return the shape displaying the mushroom
     */
    public static ParticleCylinder mushroom(Particle particle, LocationSafe bottom, double radius, double height, int particleFrequency) {
        return mushroom(particle, bottom, radius, radius, height, particleFrequency);
    }

    /**
     * @param particle particle to use
     * @param bottom where the bottom of the shape should be
     * @param size the X and Z radius will be the same as the given size, and the height will be the size * 2.6
     * @param particleFrequency particle amount
     * @return the shape displaying the mushroom
     */
    public static ParticleCylinder mushroom(Particle particle, LocationSafe bottom, double size, int particleFrequency) {
        return mushroom(particle, bottom, size, size, size * 2.6, particleFrequency);
    }

    /**
     * @param rainParticle particle to use for rain
     * @param cloudParticle particle to use for the cloud
     * @param center the center of where the cloud should be and where the rain should spawn
     * @param xRadius what the x radius should be
     * @param yRadius what the y radius should be
     * @param zRadius what the z radius should be
     * @param rainAmount amount of rain particles
     * @param cloudAmount amount of cloud particles
     * @return the shape displaying the raincloud
     */
    public static ParticleFluid raincloud(Particle rainParticle, Particle cloudParticle, LocationSafe center, double xRadius, double yRadius, double zRadius, int rainAmount, int cloudAmount) {
        ParticleFluid rain = new ParticleFluid(rainParticle, center, 0.75, 0.5, 1);
        Vector v = new Vector();

        rain.addMechanic(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, ((particle, current, addition, count) -> {
            if (count == 1) {
                Location l = rain.getSpawnLocation();

                if (rain.getParticleFrequency() < rainAmount) {
                    for (int i = 0; i < (int) Math.ceil(rainAmount / 100D); i++) {
                        v.setX(rng.nextDouble(xRadius * 2) - xRadius);
                        v.setY(rng.nextDouble(yRadius * 2) - yRadius);
                        v.setZ(rng.nextDouble(zRadius * 2) - zRadius);

                        l.add(v);
                        rain.setParticleFrequency(rain.getParticleFrequency() + 1);
                        l.subtract(v);
                    }
                } else {
                    rain.setParticleFrequency(rain.getParticleFrequency() - (int) Math.ceil(rainAmount / 100D));
                }

                cloudParticle.setOffset(xRadius, yRadius, zRadius);

                for (int i = 0; i < cloudAmount; i++) {
                    if (!rain.getPlayers().isEmpty()) {
                        cloudParticle.displayForPlayers(l, rain.getPlayers());
                    } else {
                        cloudParticle.display(l);
                    }
                }
            }
        }));

        return rain;
    }

    /**
     * @param rainParticle particle to use for rain
     * @param cloudParticle particle to use for the cloud
     * @param center the center of where the cloud should be and where the rain should spawn
     * @param xRadius what the x radius should be
     * @param yRadius what the y radius should be
     * @param zRadius what the z radius should be
     * @param rainAmount amount of rain and cloud particles
     * @return the shape displaying the raincloud
     */
    public static ParticleFluid raincloud(Particle rainParticle, Particle cloudParticle, LocationSafe center, double xRadius, double yRadius, double zRadius, int rainAmount) {
        return raincloud(rainParticle, cloudParticle, center, xRadius, yRadius, zRadius, rainAmount, rainAmount);
    }

    /**
     * @param rainParticle particle to use for rain
     * @param cloudParticle particle to use for the cloud
     * @param center the center of where the cloud should be and where the rain should spawn
     * @param xRadius what the x radius should be
     * @param zRadius what the z radius should be
     * @param rainAmount amount of rain and cloud particles
     * @return the shape displaying the raincloud
     */
    public static ParticleFluid raincloud(Particle rainParticle, Particle cloudParticle, LocationSafe center, double xRadius, double zRadius, int rainAmount) {
        return raincloud(rainParticle, cloudParticle, center, xRadius, (xRadius + zRadius) / 2 * 0.4, zRadius, rainAmount, rainAmount);
    }

    /**
     * @param rainParticle particle to use for rain
     * @param cloudParticle particle to use for the cloud
     * @param center the center of where the cloud should be and where the rain should spawn
     * @param radius what the radius should be
     * @param rainAmount amount of rain and cloud particles
     * @return the shape displaying the raincloud
     */
    public static ParticleFluid raincloud(Particle rainParticle, Particle cloudParticle, LocationSafe center, double radius, int rainAmount) {
        return raincloud(rainParticle, cloudParticle, center, radius, radius / 2 * 0.4, radius, rainAmount, rainAmount);
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

            mechParticle.setOffset(top.getXRadius(), top.getCenter().distance(spi.getCircleInfo(0).getCenter()) / 10, top.getZRadius());

            if (!spi.getPlayers().isEmpty()) {
                mechParticle.displayForPlayers(top.getCenter(), spi.getPlayers());
            } else {
                mechParticle.display(top.getCenter());
            }

            mechParticle.setOffset(0, 0, 0);
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