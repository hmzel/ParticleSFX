package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import hm.zelha.particlesfx.util.LVMath;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ParticleVibration extends TravellingParticle {

    private Entity entity = null;
    private int arrivalTime = 20;

    public ParticleVibration(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVibration(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleVibration(Entity entity, double offsetX, double offsetY, double offsetZ, int count) {
        super("", false, 0, null, null, offsetX, offsetY, offsetZ, count);

        this.entity = entity;
    }

    public ParticleVibration(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVibration(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleVibration(Entity entity, double offsetX, double offsetY, double offsetZ) {
        this(entity, offsetX, offsetY, offsetZ, 1);
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

    public ParticleVibration(Entity entity) {
        this(entity, 0, 0, 0, 1);
    }

    public ParticleVibration() {
        this((Location) null, 0, 0, 0, 1);
    }

    @Override
    public ParticleVibration inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleVibration) {
            this.entity = ((ParticleVibration) particle).entity;
            this.arrivalTime = ((ParticleVibration) particle).arrivalTime;
        }

        if (particle instanceof ParticleTrail) {
            this.arrivalTime = ((ParticleTrail) particle).getArrivalTime();
        }

        return this;
    }

    @Override
    public ParticleVibration clone() {
        return new ParticleVibration().inherit(this);
    }

    @Override
    protected Vector getXYZ(Location location) {
        return LVMath.toVector(xyzHelper, location).add(generateFakeOffset());
    }

    @Override
    protected Vector getOffsets(Location location) {
        return offsetHelper.zero();
    }

    @Override
    protected int getPacketCount() {
        return 0;
    }

    @Override
    protected Packet getStrangePacket(Location location) {
        PositionSource source;
        Vector xyz = getXYZ(location);

        if (entity != null) {
            source = new EntityPositionSource(((CraftEntity) entity).getHandle(), (float) (entity.getHeight() / 2));
        } else {
            BlockPosition.MutableBlockPosition destination = new BlockPosition.MutableBlockPosition();

            destination.d(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            destination.e(fakeOffsetHelper.getBlockX(), fakeOffsetHelper.getBlockY(), fakeOffsetHelper.getBlockZ());

            if (toGo != null) {
                destination.d(toGo.getBlockX(), toGo.getBlockY(), toGo.getBlockZ());
            } else if (velocity != null) {
                destination.e((int) velocity.getX(), (int) velocity.getY(), (int) velocity.getZ());
            }

            source = new BlockPositionSource(destination);
        }

        return new PacketPlayOutWorldParticles(
                new VibrationParticleOption(source, arrivalTime), true, false, (float) xyz.getX(), (float) xyz.getY(), (float) xyz.getZ(), 0f,
                0f, 0f, 1, 1
        );
    }

    /**
     * This particle can track entities client-side, which can make for some really neat effects.
     *
     * @param entity entity for this particle to track
     */
    public ParticleVibration setEntity(Entity entity) {
        this.entity = entity;

        return this;
    }

    /**
     * @param arrivalTime the amount of ticks it takes this particle to go from its origin to its destination, default 20 ticks or 1 second.
     */
    public ParticleVibration setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;

        return this;
    }

    /**
     * This particle can track entities client-side, which can make for some really neat effects.
     *
     * @return entity for this particle to track
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @return the amount of ticks it takes this particle to go from its origin to its destination, default 20 ticks or 1 second.
     */
    public int getArrivalTime() {
        return arrivalTime;
    }
}