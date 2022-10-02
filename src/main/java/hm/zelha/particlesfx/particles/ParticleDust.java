package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;

import javax.annotation.Nullable;

public class ParticleDust extends ColorableParticle {
    public ParticleDust(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.REDSTONE, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDust(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleDust(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleDust(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleDust(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleDust() {
        this(null, 100, 0, 0, 0, 1);
    }
}
