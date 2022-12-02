package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.Main;
import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.shapers.parents.Shape;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class ParticleImage extends ParticleShaper {

    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private BufferedImage image = null;
    private String link;
    private File path;
    private double xRadius;
    private double zRadius;

    public ParticleImage(ColorableParticle particle, LocationSafe center, String link, double xRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        setImage(link);
        start();
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, File path, double xRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        setImage(path);
        start();
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, String link, double radius, int particleFrequency) {
        this(particle, center, link, radius, radius, particleFrequency);
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, File path, double radius, int particleFrequency) {
        this(particle, center, path, radius, radius, particleFrequency);
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, String link, int particleFrequency) {
        this(particle, center, link, 0, 0, particleFrequency);
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, File path, int particleFrequency) {
        this(particle, center, path, 0, 0, particleFrequency);
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, String link) {
        this(particle, center, link, 0, 0, 2000);
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, File path) {
        this(particle, center, path, 0, 0, 2000);
    }

    @Override
    public void display() {
        if (image == null) return;

        double limit = particleFrequency;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        for (int i = overallCount; i < limit; i++) {
            ColorableParticle particle = (ColorableParticle) getCurrentParticle();
            double x = rng.nextDouble(image.getWidth());
            double z = rng.nextDouble(image.getHeight());
            Object data = image.getRaster().getDataElements((int) x, (int) z, null);
            ColorModel model = image.getColorModel();

            if (model.hasAlpha() && model.getAlpha(data) == 0) {
                limit++;

                continue;
            }

            particle.setColor(Color.fromRGB(model.getRed(data), model.getGreen(data), model.getBlue(data)));
            locationHelper.zero().add(getCenter());
            vectorHelper.setX(((x / image.getWidth() * 2) - 1) * xRadius);
            vectorHelper.setY(0);
            vectorHelper.setZ(((z / image.getHeight() * 2) - 1) * -zRadius);
            applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
            rot.apply(vectorHelper);
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_ROTATION, particle, locationHelper, vectorHelper);
            locationHelper.add(vectorHelper);

            if (!players.isEmpty()) {
                particle.displayForPlayers(locationHelper, players);
            } else {
                particle.display(locationHelper);
            }

            overallCount++;

            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY, particle, locationHelper, vectorHelper);

            if (trackCount) {
                currentCount++;
                hasRan = true;

                if (currentCount >= particlesPerDisplay) {
                    currentCount = 0;
                    break;
                }
            }
        }

        if (!trackCount || !hasRan) {
            overallCount = 0;
        }
    }

    @Override
    public Shape clone() {
        ParticleImage clone;

        if (link != null) {
            clone = new ParticleImage((ColorableParticle) particle, locations.get(0).clone(), link, xRadius, zRadius, particleFrequency);
        } else {
            clone = new ParticleImage((ColorableParticle) particle, locations.get(0).clone(), path, xRadius, zRadius, particleFrequency);
        }

        for (Pair<Particle, Integer> pair : secondaryParticles) {
            clone.addParticle(pair.getKey(), pair.getValue());
        }

        for (Pair<ShapeDisplayMechanic, ShapeDisplayMechanic.Phase> pair : mechanics) {
            clone.addMechanic(pair.getValue(), pair.getKey());
        }

        for (UUID id : players) {
            clone.addPlayer(id);
        }

        clone.setParticlesPerDisplay(particlesPerDisplay);

        return clone;
    }

    @Override
    protected Particle getCurrentParticle() {
        Particle particle = super.getCurrentParticle();

        if (!(particle instanceof ColorableParticle)) {
            stop();

            throw new IllegalArgumentException("ParticleImage particles must be colorable!");
        }

        return particle;
    }

    public void setImage(String link) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    image = ImageIO.read(new URL(link));
                    ParticleImage.this.link = link;
                    path = null;

                    if (xRadius == 0 && zRadius == 0) {
                        if (image.getWidth() >= image.getHeight()) {
                            setXRadius(3 * ((double) image.getWidth() / image.getHeight()));
                            setZRadius(3);
                        } else {
                            setXRadius(3);
                            setZRadius(3 * ((double) image.getHeight() / image.getWidth()));
                        }
                    }
                } catch (Throwable ex) {
                    Bukkit.getServer().getLogger().log(Level.SEVERE, "Failed to load image from " + link, ex);

                    image = null;
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public void setImage(File path) {
        try {
            image = ImageIO.read(path);
            this.path = path;
            this.link = null;

            if (xRadius == 0 && zRadius == 0) {
                if (image.getWidth() >= image.getHeight()) {
                    setXRadius(3 * ((double) image.getWidth() / image.getHeight()));
                    setZRadius(3);
                } else {
                    setXRadius(3);
                    setZRadius(3 * ((double) image.getHeight() / image.getWidth()));
                }
            }
        } catch (Throwable ex) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Failed to load image from " + path.getPath(), ex);

            image = null;
        }
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.add(center);
        setWorld(center.getWorld());
        originalCentroid.zero().add(center);
        center.setChanged(true);

        if (locations.size() > 1) {
            locations.remove(0);
        }
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    public Location getCenter() {
        return locations.get(0);
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getZRadius() {
        return zRadius;
    }
}
















