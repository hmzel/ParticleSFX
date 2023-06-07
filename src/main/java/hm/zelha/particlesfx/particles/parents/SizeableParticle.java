package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_11_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class SizeableParticle extends Particle {

    protected double size;

    protected SizeableParticle(EnumParticle particle, double size, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, count);

        setSize(size);
    }

    @Override
    public SizeableParticle inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof SizeableParticle) {
            size = ((SizeableParticle) particle).size;
        }

        return this;
    }

    @Override
    public abstract SizeableParticle clone();

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

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}