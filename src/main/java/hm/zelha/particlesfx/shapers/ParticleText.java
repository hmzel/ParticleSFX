package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
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
    protected double width = 0;
    protected double height = 0;
    protected double startX = 0;
    protected double startZ = 0;

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
        double width = this.width;
        double height = this.height;
        double startX = this.startX;
        double startZ = this.startZ;

        if (inverted) {
            width += borderX;
            height += borderZ;
            startX -= borderX;
            startZ -= borderZ;
        }

        for (int i = overallCount; i < particleFrequency; i++) {
            Particle particle = getCurrentParticle();
            double x, z;

            //i cant figure out how to evenly distribute points across the text without it looking horrible
            //might come back to this later on, but it looks fine with random distribution so :shrug:
            while (true) {
                x = rng.nextDouble(startX, width + this.startX);
                z = rng.nextDouble(startZ, height + this.startZ);

                if (!inverted && Color.black.getRGB() == image.getRGB((int) x, (int) z)) break;
                if (inverted && Color.black.getRGB() != image.getRGB((int) x, (int) z)) break;
            }

            locationHelper.zero().add(getCenter());
            vectorHelper.setX((((x - startX) / width * 2) - 1) * xRadius);
            vectorHelper.setY(0);
            vectorHelper.setZ((((z - startZ) / height * 2) - 1) * -zRadius);
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

    protected void remakeImage() {
        image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = image.getGraphics();
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

        graphics.dispose();

        image = new BufferedImage((int) Math.ceil(width + (borderX * 2)), (int) Math.ceil(height + (borderZ * 2)), BufferedImage.TYPE_4BYTE_ABGR);
        graphics = image.getGraphics();

        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        int currentHeight = graphics.getFontMetrics(font).getAscent() + borderZ;

        for (int i = 0; i < text.size(); i++) {
            Rectangle2D bounds = boxes.get(i);
            int x = borderX;

            if (centered) {
                x += (width / 2) - (bounds.getWidth() / 2);
            }

            graphics.drawString(this.text.get(i), x, currentHeight);

            currentHeight += bounds.getHeight();
        }

        graphics.dispose();

        double highestX = Double.MIN_VALUE;
        double highestZ = Double.MIN_VALUE;
        double lowestX = Double.MAX_VALUE;
        double lowestZ = Double.MAX_VALUE;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int z = 0; z < image.getHeight(); z++) {
                if (Color.black.getRGB() != image.getRGB(x, z)) continue;

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

    public void addLines(String... strings) {
        Validate.isTrue(strings != null && strings.length != 0, "Can't add nothing!");

        this.text.addAll(Arrays.asList(strings));

        remakeImage = true;
    }

    public void removeLine(int line) {
        text.remove(line);

        remakeImage = true;
    }

    public void setFont(Font font) {
        Validate.notNull(font, "Font can't be null!");

        for (String text : text) {
            Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display current text!");
        }

        this.font = font;
        remakeImage = true;
    }

    public void setLine(int line, String text) {
        this.text.set(line, text);

        remakeImage = true;
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

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
        remakeImage = true;
    }

    public void setBorderX(int borderX) {
        this.borderX = borderX;
        remakeImage = true;
    }

    public void setBorderZ(int borderZ) {
        this.borderZ = borderZ;
        remakeImage = true;
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

    public boolean isInverted() {
        return inverted;
    }

    public boolean isCentered() {
        return centered;
    }

    public int getBorderX() {
        return borderX;
    }

    public int getBorderZ() {
        return borderZ;
    }

    public int getLines() {
        return text.size();
    }
}














