package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleEffectColored extends Particle implements ColorableParticle {

    private final net.minecraft.core.particles.Particle<ColorParticleOption> registryParticle = (net.minecraft.core.particles.Particle<ColorParticleOption>) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", "entity_effect"));
    protected Color color;
    private int transparency = 255;

    public ParticleEffectColored(@Nullable Color color, int transparency, double offsetX, double offsetY, double offsetZ, int count) {
        super("", offsetX, offsetY, offsetZ, count);

        particle = ColorParticleOption.a(registryParticle, transparency << 24 | ((color != null) ? color.getRGB() : Color.WHITE.getRGB()));

        setTransparency(transparency);
        setColor(color);
    }

    public ParticleEffectColored(int transparency, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, transparency, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEffectColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 255, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEffectColored(int transparency, double offsetX, double offsetY, double offsetZ) {
        this(null, transparency, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEffectColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 255, offsetX, offsetY, offsetZ, count);
    }

    public ParticleEffectColored(double offsetX, double offsetY, double offsetZ) {
        this(null, 255, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleEffectColored(@Nullable Color color) {
        this(color, 255, 0, 0, 0, 1);
    }

    public ParticleEffectColored(int count) {
        this(null, 255, 0, 0, 0, count);
    }

    public ParticleEffectColored() {
        this(null, 255, 0, 0, 0, 1);
    }

    @Override
    public ParticleEffectColored inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ColorableParticle) {
            color = ((ColorableParticle) particle).getColor();
        }

        if (particle instanceof ParticleEffectColored) {
            transparency = ((ParticleEffectColored) particle).transparency;
        }

        return this;
    }

    @Override
    public ParticleEffectColored clone() {
        return new ParticleEffectColored().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        ColorParticleOption p = (ColorParticleOption) particle;

        if (color == null) {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | rng.nextInt(0xffffff));
        } else if (p.b() * 255 != color.getRed() || p.c() * 255 != color.getGreen() || p.d() * 255 != color.getBlue() || p.e() * 255 != transparency) {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | color.getRGB());
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

    /**
     * @param transparency the transparency of the particle, from 0 to 255
     * @return this object
     */
    public ParticleEffectColored setTransparency(int transparency) {
        this.transparency = Math.min(Math.max(0, transparency), 255);

        return this;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    /**
     * @return the transparency of the particle, from 0 to 255
     */
    public int getTransparency() {
        return transparency;
    }
}