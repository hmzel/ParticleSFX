package hm.zelha.particlesfx.shapers;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import hm.zelha.particlesfx.util.LocationSafe;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleText extends ParticleShaper {

    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private Font font = new Font("Arial", Font.PLAIN, 50);
    private String text = "";
    private double xRadius;
    private double zRadius;
    private boolean invert = false;
    private int borderX = 10;
    private int borderZ = 10;
    private BufferedImage image = null;
    private boolean remakeImage = true;
    private double width = 0;
    private double height = 0;
    private double startX = 0;
    private double startZ = 0;

    public ParticleText(Particle particle, LocationSafe center, double xRadius, double zRadius, int particleFrequency, String... text) {
        super(particle, particleFrequency);

        setCenter(center);
        setXRadius(xRadius);
        setZRadius(zRadius);
        addText(text);
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

        if (invert) {
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

                if (!invert && Color.black.getRGB() == image.getRGB((int) x, (int) z)) break;
                if (invert && Color.black.getRGB() != image.getRGB((int) x, (int) z)) break;
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
        ParticleText clone = new ParticleText(particle, locations.get(0), xRadius, zRadius, particleFrequency, text);

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
        clone.setFont(font);
        clone.setInverted(invert);
        clone.setBorderX(borderX);
        clone.setBorderZ(borderZ);

        return clone;
    }

    private void remakeImage() {
        image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = image.getGraphics();
        Rectangle2D bounds = font.getStringBounds(text, graphics.getFontMetrics(font).getFontRenderContext());

        graphics.dispose();

        image = new BufferedImage((int) Math.ceil(bounds.getWidth() + (borderX * 2)), (int) Math.ceil(bounds.getHeight() + (borderZ * 2)), BufferedImage.TYPE_4BYTE_ABGR);
        graphics = image.getGraphics();

        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, borderX, graphics.getFontMetrics(font).getAscent() + borderZ);
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

        width = highestX - lowestX;
        height = highestZ - lowestZ;
        startX = lowestX;
        startZ = lowestZ;
    }

    public void addText(boolean newLine, String... text) {
        if (text.length == 0) return;

        StringBuilder string = new StringBuilder(this.text);

        if (newLine) {
            string.append("\n");
        }

        for (int i = 0; i < text.length; i++) {
            string.append(text[i]);

            if (i < text.length - 1) {
                string.append("\n");
            }
        }

        setText(this.text + string);
    }

    public void addText(String... text) {
        addText(false, text);
    }

    public void setFont(Font font) {
        Validate.notNull(font, "Font can't be null!");
        Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display current text!");

        this.font = font;
        remakeImage = true;
    }

    public void setText(String text) {
        Validate.notNull(text, "Text can't be null!");
        Validate.isTrue(font.canDisplayUpTo(text) == -1, "Font can't display given text!");

        this.text = text;
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
        this.invert = inverted;
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

    public String getText() {
        return text;
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
        return invert;
    }

    public int getBorderX() {
        return borderX;
    }

    public int getBorderZ() {
        return borderZ;
    }
}














