package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class ParticleImage extends ParticleShaper {

    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private final List<BufferedImage> images = new ArrayList<>();
    private final List<Color> ignoredColors = new ArrayList<>();
    private double xRadius;
    private double zRadius;
    private int fuzz = 0;
    private int delay = 0;
    private int frame = 0;
    private int displaysThisFrame = 0;
    private Thread currentThread = null;

    public ParticleImage(ColorableParticle particle, LocationSafe center, String link, double xRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        addImage(link);
        start();
    }

    public ParticleImage(ColorableParticle particle, LocationSafe center, File path, double xRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        addImage(path);
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

    private ParticleImage(ColorableParticle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency, List<BufferedImage> images, List<Color> ignored) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        this.images.addAll(images);
        this.ignoredColors.addAll(ignored);
        start();
    }

    @Override
    public void display() {
        if (frame >= images.size()) {
            frame = 0;

            return;
        }

        BufferedImage image = images.get(frame);
        double limit = particleFrequency;
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        main:
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

            int red = model.getRed(data);
            int green = model.getGreen(data);
            int blue = model.getBlue(data);

            for (int k = 0; k < ignoredColors.size(); k++) {
                Color ignored = ignoredColors.get(k);

                if (red > ignored.getRed() + fuzz || red < ignored.getRed() - fuzz) continue;
                if (green > ignored.getGreen() + fuzz || green < ignored.getGreen() - fuzz) continue;
                if (blue > ignored.getBlue() + fuzz || blue < ignored.getBlue() - fuzz) continue;

                limit++;

                continue main;
            }

            particle.setColor(Color.fromRGB(red, green, blue));
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
            displaysThisFrame++;

            if (displaysThisFrame >= delay) {
                displaysThisFrame = 0;
                frame++;
            }

            if (frame >= images.size()) {
                frame = 0;
            }
        }
    }

    /**
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     */
    @Override
    public ParticleImage clone() {
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ParticleImage clone = new ParticleImage((ColorableParticle) particle, locations.get(0).clone(), xRadius, zRadius, particleFrequency, images, ignoredColors);

        clone.frame = frame;
        clone.displaysThisFrame = displaysThisFrame;

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
        clone.setFuzz(fuzz);
        clone.setDelay(delay);

        return clone;
    }

    @Override
    public void addParticle(Particle particle, int particlesUntilDisplay) {
        Validate.isInstanceOf(ColorableParticle.class, particle, "ParticleImage particles must be colorable!");

        super.addParticle(particle, particlesUntilDisplay);
    }

    private void addOrRemoveImages(Object toLoad, boolean remove, int index) {
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread thread = new Thread(() -> {
            try {
                ImageInputStream input = ImageIO.createImageInputStream(toLoad);
                ImageReader reader = ImageIO.getImageReaders(input).next();

                reader.setInput(input);

                double imageAmount = reader.getNumImages(true);

                for (int i = 0; i < imageAmount; i++) {
                    BufferedImage image = reader.read(i);

                    //i know this is slightly janky but if i dont do it this way i'd have to make a completely different method
                    //that would be like 95% duplicate code
                    if (remove) {
                        images.remove(image);
                    } else {
                        images.add(index + i, image);

                        if (xRadius == 0 && zRadius == 0) {
                            if (image.getWidth() >= image.getHeight()) {
                                setXRadius(3 * ((double) image.getWidth() / image.getHeight()));
                                setZRadius(3);
                            } else {
                                setXRadius(3);
                                setZRadius(3 * ((double) image.getHeight() / image.getWidth()));
                            }
                        }
                    }
                }
            } catch (Throwable ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Failed to load image from " + toLoad.toString(), ex);
            }
        });

        thread.start();

        currentThread = thread;
    }

    /**
     * adds an image from a URL (gifs supported!)
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index to put the image
     * @param link URL of image
     */
    public void addImage(int index, String link) {
        try {
            addOrRemoveImages(new URL(link).openStream(), false, index);
        }  catch (Throwable ex) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Failed to load image from " + link, ex);
        }
    }

    /**
     * adds an image from a file (gifs supported!)
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index to put the image
     * @param path location of file
     */
    public void addImage(int index, File path) {
        addOrRemoveImages(path, false, index);
    }

    /**
     * adds an image from a URL (gifs supported!)
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param link URL of image
     */
    public void addImage(String link) {
        addImage(getFrameAmount(), link);
    }

    /**
     * adds an image from a file (gifs supported!)
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param path location of file
     */
    public void addImage(File path) {
        addImage(getFrameAmount(), path);
    }

    public void addIgnoredColor(Color color) {
        Validate.notNull(color, "Color can't be null!");

        ignoredColors.add(color);
    }

    /**
     * gets an image from a URL and removes it (gifs supported!)
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param link URL of image
     */
    public void removeImage(String link) {
        try {
            addOrRemoveImages(new URL(link).openStream(), true, 0);
        }  catch (Throwable ex) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Failed to load image from " + link, ex);
        }
    }

    /**
     * gets an image from a file and removes it (gifs supported!) <p></p>
     *
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param path location of file
     */
    public void removeImage(File path) {
        addOrRemoveImages(path, true, 0);
    }

    /**
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index of frame you want to remove from the frame list
     */
    public void removeFrame(int index) {
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        images.remove(index);
    }

    public void removeIgnoredColor(int index) {
        ignoredColors.remove(index);
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

    /**
     * @param fuzz how similar pixel colors have to be to ignored colors in order to be ignored
     */
    public void setFuzz(int fuzz) {
        this.fuzz = fuzz;
    }

    /**
     * @param delay amount of times to display before switching to the next frame
     */
    public void setDelay(int delay) {
        this.delay = delay;
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

    public int getFuzz() {
        return fuzz;
    }

    public int getDelay() {
        return delay;
    }

    public Color getIgnoredColor(int index) {
        return ignoredColors.get(index);
    }

    public int getIgnoredColorAmount() {
        return ignoredColors.size();
    }

    /**
     * NOTE: USING THIS METHOD WILL CAUSE THE CURRENT THREAD TO STALL UNTIL ANY CURRENTLY RUNNING IMAGE PRODUCTION IS FINISHED. <p>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @return amount of images stored
     */
    public int getFrameAmount() {
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return images.size();
    }
}
















