package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
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

        return this;
    }

    @Override
    public ParticleVibration clone() {
        return new ParticleVibration().inherit(this);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        if (!(particle instanceof VibrationParticleOption)) {
            particle = new VibrationParticleOption(new VibrationPath(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), new BlockPositionSource(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())), arrivalTime));
        }

        VibrationPath vibe = ((VibrationParticleOption) particle).c();
        BlockPosition origin = vibe.b();
        BlockPosition destination = vibe.c().a(null).get();
        boolean changed = false;

        if (origin.u() != (int) location.getX() || origin.v() != (int) location.getY() || origin.w() != (int) location.getZ()) {
            origin = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            changed = true;
        }

        if (toGo != null && (destination.u() != (int) location.getX() || destination.v() != (int) location.getY() || destination.w() != (int) location.getZ())) {
            destination = new BlockPosition(toGo.getBlockX(), toGo.getBlockY(), toGo.getBlockZ());
            changed = true;
        }

        if (velocity != null && (destination.u() != (int) (location.getX() + velocity.getX()) || destination.v() != (int) (location.getY() + velocity.getY()) || destination.w() != (int) (location.getZ() + velocity.getZ()))) {
            destination = new BlockPosition((int) (location.getX() + velocity.getX()), (int) (location.getY() + velocity.getY()), (int) (location.getZ() + velocity.getZ()));
            changed = true;
        }

        if (vibe.a() != arrivalTime) {
            changed = true;
        }

        if (changed) {
            particle = new VibrationParticleOption(new VibrationPath(origin, new BlockPositionSource(destination), arrivalTime));
        }

        super.display(location, players);
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
