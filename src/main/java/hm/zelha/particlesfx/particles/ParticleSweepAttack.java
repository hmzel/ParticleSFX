package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.SizeableParticle;
import net.minecraft.server.v1_16_R1.Particles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleSweepAttack extends Particle implements SizeableParticle {

    protected double size;

    public ParticleSweepAttack(double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(Particles.SWEEP_ATTACK, offsetX, offsetY, offsetZ, 0, count);

        setSize(size);
    }

    public ParticleSweepAttack(double size, double offsetX, double offsetY, double offsetZ) {
        this(size, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSweepAttack(double offsetX, double offsetY, double offsetZ, int count) {
        this(1, offsetX, offsetY, offsetZ, count);
    }

    public ParticleSweepAttack(double offsetX, double offsetY, double offsetZ) {
        this(1, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleSweepAttack(double size, int count) {
        this(size, 0, 0, 0, count);
    }

    public ParticleSweepAttack(double size) {
        this(size, 0, 0, 0, 1);
    }

    public ParticleSweepAttack(int count) {
        this(1, 0, 0, 0, count);
    }

    public ParticleSweepAttack() {
        this(1, 0, 0, 0, 1);
    }

    @Override
    public ParticleSweepAttack inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof SizeableParticle) {
            setSize(((SizeableParticle) particle).getSize());
        }

        return this;
    }

    @Override
    public ParticleSweepAttack clone() {
        return new ParticleSweepAttack().inherit(this);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return super.getXYZ(location).add(generateFakeOffset());
    }

    @Override
    protected Vector getOffsets(Location location) {
        return super.getOffsets(location).zero().setX(-(size) + 2);
    }

    @Override
    protected float getPacketSpeed() {
        return 1;
    }

    @Override
    protected int getPacketCount() {
        return 0;
    }

    @Override
    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public double getSize() {
        return size;
    }
}