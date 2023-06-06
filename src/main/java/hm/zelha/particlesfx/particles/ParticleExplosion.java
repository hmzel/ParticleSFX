package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleExplosion extends Particle {

    private double size;

    public ParticleExplosion(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.EXPLOSION_LARGE, offsetX, offsetY, offsetZ, 0, count);

        this.size = size;
    }

    public ParticleExplosion(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosion(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleExplosion(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleExplosion(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleExplosion(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleExplosion(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleExplosion() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    public ParticleExplosion inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleExplosion) {
            size = ((ParticleExplosion) particle).size;
        }

        return this;
    }

    @Override
    public ParticleExplosion clone() {
        return new ParticleExplosion().inherit(this);
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.zero().setX(-(size) + 2);
    }

    @Override
    protected float getPacketSpeed() {
        return 1;
    }

    @Override
    protected int getPacketCount() {
        return 0;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}