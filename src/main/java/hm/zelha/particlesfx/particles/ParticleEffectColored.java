package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleEffectColored extends ColorableParticle {

    private final net.minecraft.core.particles.Particle<ColorParticleOption> registryParticle = (net.minecraft.core.particles.Particle<ColorParticleOption>) BuiltInRegistries.i.a(MinecraftKey.a("minecraft", "entity_effect"));
    private int transparency = 255;

    public ParticleEffectColored(@Nullable Color color, int transparency, double offsetX, double offsetY, double offsetZ, int count) {
        super("", color, offsetX, offsetY, offsetZ, count);

        if (color != null) {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | color.getRGB());
        } else {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | Color.WHITE.getRGB());
        }

        setTransparency(transparency);
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

    /**
     * @param transparency the transparency of the particle, from 0 to 255
     * @return this object
     */
    public ParticleEffectColored setTransparency(int transparency) {
        this.transparency = Math.min(Math.max(0, transparency), 255);

        return this;
    }

    /**
     * @return the transparency of the particle, from 0 to 255
     */
    public int getTransparency() {
        return transparency;
    }
}