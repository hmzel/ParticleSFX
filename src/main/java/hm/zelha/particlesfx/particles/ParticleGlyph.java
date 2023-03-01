package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleGlyph extends TravellingParticle {
    public ParticleGlyph(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ENCHANTMENT_TABLE, true, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.ENCHANTMENT_TABLE, true, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleGlyph(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleGlyph(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleGlyph(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleGlyph(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleGlyph() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleGlyph inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }
}
