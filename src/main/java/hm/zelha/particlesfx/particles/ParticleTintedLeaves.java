package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleTintedLeaves extends Particle implements ColorableParticle {

    private final net.minecraft.core.particles.Particle<ColorParticleOption> registryParticle = (net.minecraft.core.particles.Particle<ColorParticleOption>) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", "tinted_leaves"));
    protected Color color;

    public ParticleTintedLeaves(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, count);

        particle = ColorParticleOption.a(registryParticle, 255 << 24 | ((color != null) ? color.getRGB() : Color.WHITE.getRGB()));

        setColor(color);
    }

    public ParticleTintedLeaves(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTintedLeaves(@Nullable Color color, int count) {
        this(color, 0, 0, 0, count);
    }

    public ParticleTintedLeaves(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleTintedLeaves(double offsetX, double offsetY, double offsetZ) {
        this(null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleTintedLeaves(@Nullable Color color) {
        this(color, 0, 0, 0, 1);
    }

    public ParticleTintedLeaves(int count) {
        this(null, 0, 0, 0, count);
    }

    public ParticleTintedLeaves() {
        this(null, 0, 0, 0, 1);
    }

    @Override
    public ParticleTintedLeaves inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ColorableParticle) {
            color = ((ColorableParticle) particle).getColor();
        }

        return this;
    }

    @Override
    public ParticleTintedLeaves clone() {
        return new ParticleTintedLeaves().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        ColorParticleOption p = (ColorParticleOption) particle;

        if (color == null) {
            particle = ColorParticleOption.a(registryParticle, 255 << 24 | rng.nextInt(0xffffff));
        } else if (p.b() * 255 != color.getRed() || p.c() * 255 != color.getGreen() || p.d() * 255 != color.getBlue()) {
            particle = ColorParticleOption.a(registryParticle, 255 << 24 | color.getRGB());
        }

        super.display(location, players);
    }

    /**
     * @param color color to set, null if you want random coloring
     */
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

    /**
     * nullable to allow for randomly colored particles without being complicated
     *
     * @return color this particle is using
     */
    @Nullable
    public Color getColor() {
        return color;
    }
}
