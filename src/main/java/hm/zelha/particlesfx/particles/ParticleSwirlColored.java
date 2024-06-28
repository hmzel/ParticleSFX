package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R4.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleSwirlColored extends ColorableParticle {

    private final net.minecraft.core.particles.Particle<ColorParticleOption> registryParticle = (net.minecraft.core.particles.Particle<ColorParticleOption>) BuiltInRegistries.j.a(new MinecraftKey("entity_effect"));
    private int transparency = 255;

    public ParticleSwirlColored(@Nullable Color color, int transparency, double offsetX, double offsetY, double offsetZ, int count) {
        super("", color, offsetX, offsetY, offsetZ, count);

        if (color != null) {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | color.getRGB());
        } else {
            particle = ColorParticleOption.a(registryParticle, transparency << 24 | Color.WHITE.getRGB());
        }

        setTransparency(transparency);
    }

    public ParticleSwirlColored(int transparency, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, transparency, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSwirlColored(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 255, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirlColored(int transparency, double offsetX, double offsetY, double offsetZ) {
        this(null, transparency, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirlColored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 255, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSwirlColored(double offsetX, double offsetY, double offsetZ) {
        this(null, 255, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSwirlColored(@Nullable Color color) {
        this(color, 255, 0, 0, 0, 1);
    }

    public ParticleSwirlColored(int count) {
        this(null, 255, 0, 0, 0, count);
    }

    public ParticleSwirlColored() {
        this(null, 255, 0, 0, 0, 1);
    }

    @Override
    public ParticleSwirlColored inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleSwirlColored clone() {
        return new ParticleSwirlColored().inherit(this);
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
    public ParticleSwirlColored setTransparency(int transparency) {
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