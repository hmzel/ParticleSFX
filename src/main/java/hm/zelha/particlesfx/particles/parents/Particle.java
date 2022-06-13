package hm.zelha.particlesfx.particles.parents;

import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

public class Particle {

    protected Object data;
    private final Effect particle;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double speed;
    private int count;
    private int radius;

    protected Particle(Effect particle, double offsetX, double offsetY, double offsetZ, double speed, int count, int radius) {
        Validate.notNull(particle, "Particle cannot be null!");
        Validate.isTrue(particle.getType() != Effect.Type.SOUND, "Effect must be of Type.PARTICLE or Type.VISUAL!");

        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.count = count;
        this.radius = radius;
    }

    public void display(Location location) {
        display(location, Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public void displayForPlayer(Location location, Player player) {
        display(location, player);
    }

    public void displayForPlayers(Location location, Player... players) {
        display(location, players);
    }

    private void display(Location location, Player... players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        int count2 = 1;
        int idValue = particle.getId();
        EnumParticle nmsParticle = null;
        Packet packet = null;

        if (particle.getType() == Effect.Type.VISUAL) count2 = count;
        if (data instanceof BlockDirectional.BlockDirection) idValue = ((BlockDirectional.BlockDirection) data).getValue();
        if (data instanceof PotionType) idValue = ((PotionType) data).getDamageValue();

        if (particle.getType() == Effect.Type.VISUAL) {
            packet = new PacketPlayOutWorldEvent(particle.getId(), new BlockPosition(
                    location.getBlockX(), location.getBlockY(), location.getBlockZ()
            ), idValue, false);
        } else {
            for (EnumParticle p : EnumParticle.values()) {
                if (particle.getName().startsWith(p.b().replace("_", ""))) {
                    nmsParticle = p;
                    break;
                }
            }

            Validate.notNull(nmsParticle, "Something went wrong determining EnumParticle!");
        }

        for (int i = 0; i != count2; i++) {
            for (Player player : players) {
                if (radius != 0 && (Math.abs(location.getX() - player.getLocation().getX()) + Math.abs(location.getY() - player.getLocation().getY()) + Math.abs(location.getZ() - player.getLocation().getZ())) > radius) {
                    continue;
                }

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket((packet != null) ? packet :
                        new PacketPlayOutWorldParticles(
                                nmsParticle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                (float) offsetX, (float) offsetY, (float) offsetZ, (float) speed, count
                        )
                );
            }
        }
    }

    //        public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
    //            Validate.notNull(location, "Location cannot be null");
    //            Validate.notNull(effect, "Effect cannot be null");
    //            Validate.notNull(location.getWorld(), "World cannot be null");
    //            int distance;
    //            Object packet;
    //            if (effect.getType() != Type.PARTICLE) {
    //                distance = effect.getId();
    //                packet = new PacketPlayOutWorldEvent(distance, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), id, false);
    //            } else {
    //                EnumParticle particle = null;
    //                int[] extra = null;
    //                EnumParticle[] var14;
    //                int var15 = (var14 = EnumParticle.values()).length;
    //
    //                for(int var16 = 0; var16 < var15; ++var16) {
    //                    EnumParticle p = var14[var16];
    //                    if (effect.getName().startsWith(p.b().replace("_", ""))) {
    //                        particle = p;
    //                        if (effect.getData() != null) {
    //                            if (effect.getData().equals(Material.class)) {
    //                                extra = new int[]{id};
    //                            } else {
    //                                extra = new int[]{data << 12 | id & 4095};
    //                            }
    //                        }
    //                        break;
    //                    }
    //                }
    //
    //                if (extra == null) {
    //                    extra = new int[0];
    //                }
    //
    //                packet = new PacketPlayOutWorldParticles(particle, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount, extra);
    //            }
    //
    //            radius *= radius;
    //            if (CraftPlayer.this.getHandle().playerConnection != null) {
    //                if (location.getWorld().equals(CraftPlayer.this.getWorld())) {
    //                    distance = (int)CraftPlayer.this.getLocation().distanceSquared(location);
    //                    if (distance <= radius) {
    //                        CraftPlayer.this.getHandle().playerConnection.sendPacket((Packet)packet);
    //                    }
    //
    //                }
    //            }
    //        }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }

    public int getRadius() {
        return radius;
    }
}
























