package hm.zelha.particlesfx.shapers;

import com.sun.imageio.plugins.gif.GIFImageReader;
import hm.zelha.particlesfx.particles.ParticleDustColored;
import hm.zelha.particlesfx.particles.ParticleEffectColored;
import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

/**
 * This class is generally meant to be used with particles that extend ColorableParticle to properly display a colored image <br>
 * ({@link ParticleDustColored}, {@link ParticleEffectColored}) <br>
 * however it allows you to use normal particles to draw detailed objects by reading transparent images <br>
 * (or by using {@link ParticleImage#addIgnoredColor(Color)} and {@link ParticleImage#setFuzz(int)})
 */
public class ParticleImage extends ParticleShaper {

    protected final ThreadLocalRandom rng = ThreadLocalRandom.current();
    protected final List<BufferedImage> images = new ArrayList<>();
    protected final List<Color> ignoredColors = new ArrayList<>();
    protected double xRadius;
    protected double zRadius;
    protected int fuzz = 0;
    protected int frameDelay = 0;
    protected int frame = 0;
    protected int displaysThisFrame = 0;
    protected Thread currentThread = null;

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, String link, double xRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, zRadius, particleFrequency);

        addImage(link);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, File path, double xRadius, double zRadius, int particleFrequency) {
        this(particle, center, xRadius, zRadius, particleFrequency);

        addImage(path);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, String link, double radius, int particleFrequency) {
        this(particle, center, link, radius, radius, particleFrequency);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, File path, double radius, int particleFrequency) {
        this(particle, center, path, radius, radius, particleFrequency);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, String link, int particleFrequency) {
        this(particle, center, link, 0, 0, particleFrequency);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, File path, int particleFrequency) {
        this(particle, center, path, 0, 0, particleFrequency);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, String link) {
        this(particle, center, link, 0, 0, 2000);
    }

    /**@see ParticleImage */
    public ParticleImage(Particle particle, LocationSafe center, File path) {
        this(particle, center, path, 0, 0, 2000);
    }

    protected ParticleImage(Particle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        start();
    }

    @Override
    public void display() {
        if (images.isEmpty()) return;

        BufferedImage image = images.get(frame);
        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;

        main:
        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            double x = rng.nextDouble(image.getWidth());
            double z = rng.nextDouble(image.getHeight());
            Object data = image.getRaster().getDataElements((int) x, (int) z, null);
            ColorModel model = image.getColorModel();

            if (model.hasAlpha() && model.getAlpha(data) == 0) {
                i--;

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

                i--;

                continue main;
            }

            if (particle instanceof ColorableParticle) {
                ((ColorableParticle) particle).setColor(red, green, blue);
            }

            locationHelper.zero().add(getCenter());
            vectorHelper.setX(((x / image.getWidth() * 2) - 1) * xRadius);
            vectorHelper.setY(0);
            vectorHelper.setZ(((z / image.getHeight() * 2) - 1) * -zRadius);
            applyMechanics(ShapeDisplayMechanic.Phase.BEFORE_ROTATION, particle, locationHelper, vectorHelper);
            rot.apply(vectorHelper);

            for (ParticleShapeCompound compound : compounds) {
                rotHelper.inherit(compound).apply(vectorHelper);
            }

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
            applyMechanics(ShapeDisplayMechanic.Phase.AFTER_DISPLAY_FULL, particle, locationHelper, vectorHelper);

            overallCount = 0;
            displaysThisFrame++;

            if (displaysThisFrame >= frameDelay) {
                displaysThisFrame = 0;
                frame++;
            }
        }

        if (frame >= images.size()) {
            frame = 0;
        }
    }

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     */
    @Override
    public ParticleImage clone() {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ParticleImage clone = new ParticleImage(particle, locations.get(0).clone(), xRadius, zRadius, particleFrequency);

        clone.frame = frame;
        clone.displaysThisFrame = displaysThisFrame;

        for (Color color : ignoredColors) {
            clone.addIgnoredColor(color.clone());
        }

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.images.addAll(images);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setFuzz(fuzz);
        clone.setFrameDelay(frameDelay);

        if (animator == null) {
            clone.stop();
        }

        return clone;
    }

    @Override
    public void scale(double x, double y, double z) {
        setXRadius(getXRadius() * x);
        setZRadius(getZRadius() * z);
    }

    protected void addOrRemoveImages(Object toLoad, boolean remove, int index) {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread thread = new Thread(() -> {
            try (ImageInputStream input = ImageIO.createImageInputStream(toLoad)) {
                ImageReader reader = ImageIO.getImageReaders(input).next();

                if (reader instanceof GIFImageReader) {
                    reader = new PatchedGIFImageReader(null);
                }

                reader.setInput(input);

                int imageAmount = reader.getNumImages(true);

                for (int i = 0; i < imageAmount; i++) {
                    BufferedImage image = reader.read(i);

                    //i know this is slightly janky but if i dont do it this way i'd have to make a completely different method
                    //that would be like 95% duplicate code
                    if (remove) {
                        images.remove(image);
                    } else {
                        images.add(index + i, image);

                        if (xRadius == 0 && zRadius == 0) {
                            setRadius(3);
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
     * adds an image from a URL (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
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
     * adds an image from a file (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index to put the image
     * @param path location of file
     */
    public void addImage(int index, File path) {
        addOrRemoveImages(path, false, index);
    }

    /**
     * adds an image from a URL (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param link URL of image
     */
    public void addImage(String link) {
        addImage(getFrameAmount(), link);
    }

    /**
     * adds an image from a file (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param path location of file
     */
    public void addImage(File path) {
        addImage(getFrameAmount(), path);
    }

    public ParticleImage addIgnoredColor(Color color) {
        Validate.notNull(color, "Color can't be null!");

        ignoredColors.add(color);

        return this;
    }

    /**
     * gets an image from a URL and removes it (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
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
     * gets an image from a file and removes it (gifs supported!) <br><br>
     *
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param path location of file
     */
    public void removeImage(File path) {
        addOrRemoveImages(path, true, 0);
    }

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index of frame you want to remove from the frame list
     */
    public void removeFrame(int index) {
        if (currentThread != null && currentThread != Thread.currentThread()) {
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

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index index of frame you want to be displaying
     */
    public void setCurrentFrame(int index) {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Validate.isTrue(index >= 0 && index < images.size(), "Can't display frames that don't exist!");

        frame = index;
    }

    public void setCenter(LocationSafe center) {
        Validate.notNull(center, "Location cannot be null!");
        Validate.notNull(center.getWorld(), "Location's world cannot be null!");

        locations.add(center);
        origins.add(center.clone());
        setWorld(center.getWorld());
        center.setChanged(true);

        if (locations.size() > 1) {
            locations.remove(0);
            origins.remove(0);
        }
    }

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     * <br><br>
     * This method uses the current image's aspect ratio to set the X/Z radius, such that the largest side is set to the given radius
     * and the smallest side is set to (smallest / largest) * radius.
     *
     * @param radius max radius
     */
    public ParticleImage setRadius(double radius) {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        BufferedImage image = images.get(frame);

        if (image.getWidth() <= image.getHeight()) {
            setXRadius(radius * ((double) image.getWidth() / image.getHeight()));
            setZRadius(radius);
        } else {
            setXRadius(radius);
            setZRadius(radius * ((double) image.getHeight() / image.getWidth()));
        }

        return this;
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
    public ParticleImage setFuzz(int fuzz) {
        this.fuzz = fuzz;

        return this;
    }

    /**
     * @param frameDelay amount of times to display before switching to the next frame
     */
    public ParticleImage setFrameDelay(int frameDelay) {
        this.frameDelay = frameDelay;

        return this;
    }

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @param index frame to get the color from
     * @param x horizontal position of pixel
     * @param z vertical position of pixel
     * @return color at pixel
     */
    public Color getPixelColor(int index, int x, int z) {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Validate.isTrue(index >= 0 && index < images.size(), "Can't get color from frames that don't exist!");
        Validate.isTrue(x >= 0 && x < images.get(index).getWidth(), "Can't get color from a pixel that doesn't exist!");
        Validate.isTrue(z >= 0 && z < images.get(index).getHeight(), "Can't get color from a pixel that doesn't exist!");

        return new Color(images.get(index).getRGB(x, z));
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

    /**
     * @return how similar pixel colors have to be to ignored colors in order to be ignored
     */
    public int getFuzz() {
        return fuzz;
    }

    /**
     * @return amount of times to display before switching to the next frame
     */
    public int getFrameDelay() {
        return frameDelay;
    }

    public Color getIgnoredColor(int index) {
        return ignoredColors.get(index);
    }

    public int getIgnoredColorAmount() {
        return ignoredColors.size();
    }

    /**
     * <strong> using this method will cause the current thread to stall until any currently running image production is finished. <br></strong>
     * If you don't want to cause lag, use an asynchronous BukkitRunnable!
     *
     * @return amount of images stored
     */
    public int getFrameAmount() {
        if (currentThread != null && currentThread != Thread.currentThread()) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return images.size();
    }
}