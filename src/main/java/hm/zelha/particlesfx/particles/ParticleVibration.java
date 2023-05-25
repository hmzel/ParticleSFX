package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleVibration extends TravellingParticle {

    private int arrivalTime = 20;

    public ParticleVibration(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, null, toGo, offsetX, offsetY, offsetZ, count);

        particle = new NMSVibrationParticle();
    }

    public ParticleVibration(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, velocity, null, offsetX, offsetY, offsetZ, count);

        particle = new NMSVibrationParticle();
    }

    public ParticleVibration(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVibration(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVibration(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVibration(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVibration(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleVibration(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleVibration(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleVibration() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleVibration inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleVibration) {
            this.arrivalTime = ((ParticleVibration) particle).arrivalTime;
        }

        return this;
    }

    @Override
    public ParticleVibration clone() {
        return new ParticleVibration().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        ((NMSVibrationParticle) particle).location = location;
        super.display(location, players);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location);
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.setX(offsetX).setY(offsetY).setZ(offsetZ);
    }

    @Override
    public int getPacketCount() {
        return count;
    }

    /**
     * @param arrivalTime the amount of ticks it takes this particle to go from its origin to its destination, default 20 ticks or 1 second.
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the amount of ticks it takes this particle to go from its origin to its destination, default 20 ticks or 1 second.
     */
    public int getArrivalTime() {
        return arrivalTime;
    }


    private class NMSVibrationParticle extends VibrationParticleOption {

        private final BlockPosition.MutableBlockPosition pos = new BlockPosition.MutableBlockPosition();
        private Location location = null;

        public NMSVibrationParticle() {
            super(null, 0);
        }

        @Override
        public void a(PacketDataSerializer data) {
            if (toGo != null) {
                pos.d(toGo.getBlockX(), toGo.getBlockY(), toGo.getBlockZ());
            } else if (velocity != null) {
                pos.d((int) (location.getX() + velocity.getX()), (int) (location.getY() + velocity.getY()), (int) (location.getZ() + velocity.getZ()));
            } else {
                pos.d(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }

            data.a(new MinecraftKey("block"));
            data.a(pos);
            data.d(arrivalTime);
        }
    }
}
