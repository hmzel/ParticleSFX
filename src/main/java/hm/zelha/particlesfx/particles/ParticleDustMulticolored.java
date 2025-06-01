package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import hm.zelha.particlesfx.util.Color;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.MathHelper;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleDustMulticolored extends ParticleDustColored implements SizeableParticle {

    protected final Color colorHelper2 = new Color(rng.nextInt(0xffffff));
    private Color transition = null;

    public ParticleDustMulticolored(@Nullable Color color, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(color, size, offsetX, offsetY, offsetZ, count);

        particle = new DustColorTransitionOptions((color == null) ? rng.nextInt(0xffffff) : color.getRGB(), (transition == null) ? rng.nextInt(0xffffff) : transition.getRGB(), (float) size);

        if (transition != null) colorHelper2.setRGB(transition.getRGB());
    }

    public ParticleDustMulticolored(double size, double offsetX, double offsetY, double offsetZ, int count) {
        this(null, size, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustMulticolored(@Nullable Color color, double offsetX, double offsetY, double offsetZ, int count) {
        this(color, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustMulticolored(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDustMulticolored(@Nullable Color color, double offsetX, double offsetY, double offsetZ) {
        this(color, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustMulticolored(double size, double offsetX, double offsetY, double offsetZ) {
        this(null, size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustMulticolored(double offsetX, double offsetY, double offsetZ) {
        this(null, 1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDustMulticolored(@Nullable Color color, double size, int count) {
        this(color, size, 0, 0, 0, count);
    }

    public ParticleDustMulticolored(@Nullable Color color, double size) {
        this(color, size, 0, 0, 0, 1);
    }

    public ParticleDustMulticolored(@Nullable Color color) {
        this(color, 1, 0, 0, 0, 1);
    }

    public ParticleDustMulticolored(int count) {
        this(null, 1, 0, 0, 0, count);
    }

    public ParticleDustMulticolored(double size) {
        this(null, size, 0, 0, 0, 1);
    }

    public ParticleDustMulticolored() {
        this(null, 1, 0, 0, 0, 1);
    }

    @Override
    public ParticleDustMulticolored inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleDustMulticolored) {
            this.transition = ((ParticleDustMulticolored) particle).transition;
        }

        return this;
    }

    @Override
    public ParticleDustMulticolored clone() {
        return new ParticleDustMulticolored().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (color == null || transition == null) {
            particle = new DustColorTransitionOptions((color == null) ? rng.nextInt(0xffffff) : color.getRGB(), (transition == null) ? rng.nextInt(0xffffff) : transition.getRGB(), (float) size);
        } else if (!colorHelper.equals(color) || !colorHelper2.equals(transition) || MathHelper.a(size, 0.01F, 4.0F) != ((DustParticleOptionsBase) particle).d()) {
            particle = new DustColorTransitionOptions(color.getRGB(), transition.getRGB(), (float) size);

            colorHelper.setRGB(color.getRGB());
            colorHelper2.setRGB(transition.getRGB());
        }

        super.display(location, players);
    }

    /**
     * @param transition the color this particle will transition to as it fades.
     */
    public ParticleDustMulticolored setTransitionColor(@Nullable Color transition) {
        this.transition = transition;

        return this;
    }

    /**
     * @return the color this particle will transition to as it fades.
     */
    public Color getTransitionColor() {
        return transition;
    }
}