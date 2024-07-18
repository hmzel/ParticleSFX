package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ParticleShapeCompound;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleText extends ParticleShaper {

    protected final ThreadLocalRandom rng = ThreadLocalRandom.current();
    protected final List<String> text = new ArrayList<>();
    protected Font font = new Font("Arial", Font.PLAIN, 50);
    protected double xRadius;
    protected double zRadius;
    protected boolean inverted = false;
    protected boolean centered = true;
    protected int borderX = 10;
    protected int borderZ = 10;
    protected BufferedImage image = null;
    protected boolean remakeImage = true;
    protected int width = 0;
    protected int height = 0;
    protected int startX = 0;
    protected int startZ = 0;

    public ParticleText(Particle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency, String... text) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        addLines(text);
        start();
    }

    public ParticleText(Particle particle, LocationSafe center, double xRadius, double zRadius, String... text) {
        this(particle, center, xRadius, zRadius, 150, text);
    }

    public ParticleText(Particle particle, LocationSafe center, double radius, int particleFrequency, String... text) {
        this(particle, center, radius, radius, particleFrequency, text);
    }

    public ParticleText(Particle particle, LocationSafe center, double radius, String... text) {
        this(particle, center, radius, radius, 150, text);
    }

    @Override
    public void display() {
        if (remakeImage) {
            remakeImage();
        }

        boolean hasRan = false;
        boolean trackCount = particlesPerDisplay > 0;
        int width = this.width;
        int height = this.height;
        int startX = this.startX;
        int startZ = this.startZ;

        if (inverted) {
            width += borderX;
            height += borderZ;
            startX -= borderX;
            startZ -= borderZ;
        }

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            int x, z;

            //i cant figure out how to evenly distribute points across the text without it looking horrible
            //might come back to this later on, but it looks fine with random distribution so :shrug:
            while (true) {
                x = rng.nextInt(startX, width + this.startX);
                z = rng.nextInt(startZ, height + this.startZ);

                //its literally just lying when it throws this error and idk how to fix it
                //really annoying
                try {
                    if (!inverted && Color.black.getRGB() == image.getRGB(x, z)) break;
                    if (inverted && Color.black.getRGB() != image.getRGB(x, z)) break;
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }

            locationHelper.zero().add(getCenter());
            vectorHelper.setX((((x - startX) / (double) width * 2) - 1) * xRadius);
            vectorHelper.setY(0);
            vectorHelper.setZ((((z - startZ) / (double) height * 2) - 1) * -zRadius);
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
        }
    }

    @Override
    public ParticleText clone() {
        ParticleText clone = new ParticleText(particle, locations.get(0), xRadius, zRadius, particleFrequency, text.toArray(new String[0]));

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);
        clone.setFont(font);
        clone.setInverted(inverted);
        clone.setCentered(centered);
        clone.setBorderX(borderX);
        clone.setBorderZ(borderZ);

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

    protected void remakeImage() {
        Graphics graphics = new BufferedImage(1, 1, 6).getGraphics();
        List<Rectangle2D> boxes = new ArrayList<>();
        double width = 0;
        double height = 0;

        for (String text : text) {
            Rectangle2D bounds = font.getStringBounds(text, graphics.getFontMetrics(font).getFontRenderContext());

            boxes.add(bounds);

            height += bounds.getHeight();

            if (bounds.getWidth() > width) {
                width = bounds.getWidth();
            }
        }

        image = new BufferedImage((int) Math.ceil(width + (borderX * 2)), (int) Math.ceil(height + (borderZ * 2)), 6);
        graphics = image.getGraphics();
        int currentHeight = graphics.getFontMetrics(font).getAscent() + borderZ;

        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < text.size(); i++) {
            int x = borderX;

            if (centered) {
                x += (width / 2) - (boxes.get(i).getWidth() / 2);
            }

            graphics.drawString(this.text.get(i), x, currentHeight);

            currentHeight += boxes.get(i).getHeight();
        }

        int highestX = Integer.MIN_VALUE;
        int highestZ = Integer.MIN_VALUE;
        int lowestX = Integer.MAX_VALUE;
        int lowestZ = Integer.MAX_VALUE;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int z = 0; z < image.getHeight(); z++) {
                //its literally just lying when it throws this error and idk how to fix it
                //really annoying
                try {
                    if (Color.black.getRGB() != image.getRGB(x, z)) continue;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    continue;
                }

                highestX = Math.max(highestX, x);
                highestZ = Math.max(highestZ, z);
                lowestX = Math.min(lowestX, x);
                lowestZ = Math.min(lowestZ, z);
            }
        }

        this.width = highestX - lowestX;
        this.height = highestZ - lowestZ;
        startX = lowestX;
        startZ = lowestZ;
        remakeImage = false;
    }

    public void addLines(int index, String... strings) {
        Validate.isTrue(strings != null && strings.length != 0, "Can't add nothing!");

        for (String text : strings) {
            Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display given strings!");
        }

        this.text.addAll(index, Arrays.asList(strings));

        remakeImage = true;
    }

    public void addLines(String... strings) {
        addLines(text.size(), strings);
    }

    public void removeLine(int line) {
        text.remove(line);

        remakeImage = true;
    }

    public ParticleText setFont(Font font) {
        Validate.notNull(font, "Font can't be null!");

        for (String text : text) {
            Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display current text!");
        }

        this.font = font;
        remakeImage = true;

        return this;
    }

    public void setLine(int line, String text) {
        Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display given text!");

        this.text.set(line, text);

        remakeImage = true;
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

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setZRadius(double zRadius) {
        this.zRadius = zRadius;
    }

    /**
     * @param inverted whether particles should appear within the text or outlining the text, default false
     */
    public ParticleText setInverted(boolean inverted) {
        this.inverted = inverted;

        return this;
    }

    /**
     * @param centered whether the text should be centered or not, default true
     */
    public ParticleText setCentered(boolean centered) {
        this.centered = centered;
        remakeImage = true;

        return this;
    }

    /**
     * @param borderX how wide the border around the text should be, only used if {@link ParticleText#isInverted()} is true.
     */
    public ParticleText setBorderX(int borderX) {
        this.borderX = borderX;
        remakeImage = true;

        return this;
    }

    /**
     * @param borderZ how tall the border around the text should be, only used if {@link ParticleText#isInverted()} is true.
     */
    public ParticleText setBorderZ(int borderZ) {
        this.borderZ = borderZ;
        remakeImage = true;

        return this;
    }

    public Font getFont() {
        return font;
    }

    public String getLine(int line) {
        return text.get(line);
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
     * @return whether particles should appear within the text or outlining the text, default false
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * @return whether the text should be centered or not, default true
     */
    public boolean isCentered() {
        return centered;
    }

    /**
     * @return how wide the border around the text should be, only used if {@link ParticleText#isInverted()} is true.
     */
    public int getBorderX() {
        return borderX;
    }

    /**
     * @return how tall the border around the text should be, only used if {@link ParticleText#isInverted()} is true.
     */
    public int getBorderZ() {
        return borderZ;
    }

    public String[] getText() {
        return text.toArray(new String[0]);
    }

    public int getLineAmount() {
        return text.size();
    }
}