package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.util.MathHelper;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleDustColored extends Particle implements SizeableParticle, ColorableParticle {

    protected final Color colorHelper = new Color(rng.nextInt(0xffffff));
    protected Color color;
    protected double size;

    public ParticleDustColored(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, count);

        particle = new ParticleParamRedstone((color == null) ? rng.nextInt(0xffffff) : color.getRGB(), (float) size);

        if (color != null) colorHelper.setRGB(color.getRGB());

        setColor(color);
        setSize(size);
    }

    public ParticleDustColored(double size, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, size, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        this(color, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustColored(double size, double offsetX, double offsetY, double offsetZ) {
        this(null, size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustColored(double offsetX, double offsetY, double offsetZ) {
        this(null, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustColored(@Nullable Color color, double size, int count) {
        this(color, size, 0, 0, 0, count);
    }

    public ParticleDustColored(@Nullable Color color, double size) {
        this(color, size, 0, 0, 0, 1);
    }

    public ParticleDustColored(@Nullable Color color) {
        this(color, 1, 0, 0, 0, 1);
    }

    public ParticleDustColored(int count) {
        this(null, 1, 0, 0, 0, count);
    }

    public ParticleDustColored(double size) {
        this(null, size, 0, 0, 0, 1);
    }

    public ParticleDustColored() {
        this(null, 1, 0, 0, 0, 1);
    }

    @Override
    public ParticleDustColored inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ColorableParticle) {
            setColor(((ColorableParticle) particle).getColor());
        }

        if (particle instanceof SizeableParticle) {
            setSize(((SizeableParticle) particle).getSize());
        }

        return this;
    }

    @Override
    public ParticleDustColored clone() {
        return new ParticleDustColored().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (particle instanceof ParticleParamRedstone) {
            if (color == null) {
                particle = new ParticleParamRedstone(rng.nextInt(0xffffff), (float) size);
            } else if (!colorHelper.equals(color) || MathHelper.a(size, 0.01F, 4.0F) != ((ParticleParamRedstone) particle).d()) {
                particle = new ParticleParamRedstone(color.getRGB(), (float) size);

                colorHelper.setRGB(color.getRGB());
            }
        }

        super.display(location, players);
    }

    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public void setColor(int red, int green, int blue) {
        if (color != null && !color.isLocked()) {
            color.setRed(red);
            color.setGreen(green);
            color.setBlue(blue);
        } else {
            this.color = new Color(red, green, blue);
        }
    }

    /** only changes between 0 and 4. */
    @Override
    public void setSize(double size) {
        this.size = size;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    /** only changes between 0 and 4. */
    @Override
    public double getSize() {
        return size;
    }
}