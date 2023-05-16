package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleVibration extends TravellingParticle {

    private int arrivalTime = 20;

    public ParticleVibration(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVibration(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, velocity, null, offsetX, offsetY, offsetZ, count);
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
            this.particle = ((ParticleVibration) particle).particle;
        }

        return this;
    }

    @Override
    public ParticleVibration clone() {
        return new ParticleVibration().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (!(particle instanceof VibrationParticleOption)) {
            particle = new VibrationParticleOption(new BlockPositionSource(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())), arrivalTime);
        }

        VibrationParticleOption vibe = ((VibrationParticleOption) particle);
        Vec3D destination = vibe.c().a(null).get();
        boolean changed = false;

        if (toGo != null && (destination.a() != (int) location.getX() || destination.b() != (int) location.getY() || destination.c() != (int) location.getZ())) {
            destination = new Vec3D(toGo.getBlockX(), toGo.getBlockY(), toGo.getBlockZ());
            changed = true;
        }

        if (velocity != null && (destination.a() != (int) (location.getX() + velocity.getX()) || destination.b() != (int) (location.getY() + velocity.getY()) || destination.c() != (int) (location.getZ() + velocity.getZ()))) {
            destination = new Vec3D((int) (location.getX() + velocity.getX()), (int) (location.getY() + velocity.getY()), (int) (location.getZ() + velocity.getZ()));
            changed = true;
        }

        if (vibe.d() != arrivalTime) {
            changed = true;
        }

        if (changed) {
            particle = new VibrationParticleOption(new BlockPositionSource(new BlockPosition((int) destination.a(), (int) destination.b(), (int) destination.c())), arrivalTime);
        }

        super.display(location, players);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location);
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.zero().setX(offsetX).setY(offsetY).setZ(offsetZ);
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
}
