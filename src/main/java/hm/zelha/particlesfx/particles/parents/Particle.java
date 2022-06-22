package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.Main;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class Particle {

    private final Vector vectorHelper = new Vector(0, 0, 0);
    private final Random rng = Main.getRng();
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
        this.offsetX = Math.abs(offsetX);
        this.offsetY = Math.abs(offsetY);
        this.offsetZ = Math.abs(offsetZ);
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

        if (this instanceof TravellingParticle) count2 = count;
        if (this instanceof SizeableParticle) count2 = count;
        if (this instanceof ColorableParticle && ((ColorableParticle) this).getColor() != null) count2 = count;
        if (this instanceof InverseTravellingParticle && ((InverseTravellingParticle) this).getLocationToGo() != null) count2 = count;
        if (this instanceof PotionParticle) idValue = ((PotionParticle) this).getPotionType().getDamageValue();

        if (particle.getType() == Effect.Type.VISUAL) {
            packet = new PacketPlayOutWorldEvent(particle.getId(), new BlockPosition(
                    location.getBlockX(), location.getBlockY(), location.getBlockZ()
            ), idValue, false);

            count2 = count;
        } else if (this instanceof NoteParticle && ((NoteParticle) this).getNoteColor() != NoteParticle.NoteColor.RANDOM) {
            packet = new PacketPlayOutBlockAction(new BlockPosition(
                    location.getBlockX(), location.getBlockY(), location.getBlockZ()
            ), Blocks.NOTEBLOCK, 0, ((NoteParticle) this).getNoteColor().getValue());

            count2 = count;
        } else {
            for (EnumParticle p : EnumParticle.values()) {
                if (particle.getName().equals(p.b().replace("_", ""))) {
                    nmsParticle = p;
                    break;
                }
            }

            Validate.notNull(nmsParticle, "Something went wrong determining EnumParticle!");
        }

        for (int i = 0; i != count2; i++) {
            int[] extra = {0};
            int trueCount = count;
            double trueOffsetX = offsetX;
            double trueOffsetY = offsetY;
            double trueOffsetZ = offsetZ;
            double trueSpeed = speed;
            boolean fakeOffset = false;
            Location addition = null;

            if (this instanceof TravellingParticle && ((TravellingParticle) this).getVelocity() != null && ((TravellingParticle) this).getLocationToGo() == null) {
                Vector velocity = ((TravellingParticle) this).getVelocity();
                trueSpeed = 1;
                trueCount = 0;
                trueOffsetX = velocity.getX();
                trueOffsetY = velocity.getY();
                trueOffsetZ = velocity.getZ();
                fakeOffset = true;
            }

            if (this instanceof ColorableParticle) {
                Color color = ((ColorableParticle) this).getColor();

                if (color != null) {
                    trueCount = 0;
                    trueSpeed = ((ColorableParticle) this).getBrightness() * 0.01;
                    trueOffsetX = color.getRed() / 255D;
                    trueOffsetY = color.getGreen() / 255D;
                    trueOffsetZ = color.getBlue() / 255D;
                    fakeOffset = true;
                } else {
                    trueSpeed = 1;
                }
            }

            if (this instanceof SizeableParticle) {
                trueCount = 0;
                trueSpeed = 1;
                trueOffsetX = -(((SizeableParticle) this).getSize()) + 2;
                trueOffsetY = 0;
                trueOffsetZ = 0;
                fakeOffset = true;
            }

            if (this instanceof MaterialParticle) {
                extra = new int[] {((MaterialParticle) this).getMaterialData().getData() << 12 | ((MaterialParticle) this).getMaterialData().getItemTypeId() & 4095};
            }

            if (this instanceof InverseTravellingParticle && ((InverseTravellingParticle) this).getLocationToGo() != null) fakeOffset = true;
            if (this instanceof InverseTravellingParticle && ((InverseTravellingParticle) this).getVelocity() != null) fakeOffset = true;
            if (this instanceof TravellingParticle && ((TravellingParticle) this).getLocationToGo() != null) fakeOffset = true;
            if (this instanceof NoteParticle && ((NoteParticle) this).getNoteColor() == NoteParticle.NoteColor.RANDOM) trueSpeed = 1;

            if (fakeOffset) {
                addition = generateFakeOffset();

                location.add(addition);
            }

            Location trueLocation = location;

            if (this instanceof InverseTravellingParticle && ((InverseTravellingParticle) this).getLocationToGo() != null && ((InverseTravellingParticle) this).getVelocity() == null) {
                Location toGo = ((InverseTravellingParticle) this).getLocationToGo();
                trueSpeed = 1;
                trueCount = 0;
                trueOffsetX = location.getX() - toGo.getX();
                trueOffsetY = location.getY() - toGo.getY();
                trueOffsetZ = location.getZ() - toGo.getZ();
                trueLocation = toGo;
            }

            if (this instanceof TravellingParticle && ((TravellingParticle) this).getLocationToGo() != null && ((TravellingParticle) this).getVelocity() == null) {
                Location toGo = ((TravellingParticle) this).getLocationToGo();
                double control = ((TravellingParticle) this).getVelocityControl();
                trueSpeed = 1;
                trueCount = 0;
                trueOffsetX = (toGo.getX() - location.getX()) * control;
                trueOffsetY = (toGo.getY() - location.getY()) * control;
                trueOffsetZ = (toGo.getZ() - location.getZ()) * control;
            }

            if (this instanceof InverseTravellingParticle && ((InverseTravellingParticle) this).getVelocity() != null && ((InverseTravellingParticle) this).getLocationToGo() == null) {
                Vector velocity = ((InverseTravellingParticle) this).getVelocity();
                trueSpeed = 1;
                trueCount = 0;
                trueOffsetX = 0 - velocity.getX();
                trueOffsetY = 0 - velocity.getY();
                trueOffsetZ = 0 - velocity.getZ();
                trueLocation = location.clone().add(velocity);
            }

            for (Player player : players) {
                if (radius != 0 && (Math.abs(location.getX() - player.getLocation().getX()) + Math.abs(location.getY() - player.getLocation().getY()) + Math.abs(location.getZ() - player.getLocation().getZ())) > radius) {
                    continue;
                }

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket((packet != null) ? packet :
                        new PacketPlayOutWorldParticles(
                                nmsParticle, true, (float) trueLocation.getX(), (float) trueLocation.getY(), (float) trueLocation.getZ(),
                                (float) trueOffsetX, (float) trueOffsetY, (float) trueOffsetZ, (float) trueSpeed, trueCount, extra
                        )
                );
            }

            if (addition != null) location.subtract(addition);
        }
    }

    /**
     * generates a random number between -offset and +offset (exactly) using some scary math <p>
     * this isnt exactly how its done client-side using the actual packet, but honestly i prefer this because its more controllable
     *
     * @return a vector meant to be added to a location to mimic particle offset
     */
    protected Vector generateFakeOffset() {
        return vectorHelper.setX(
                        ((offsetX < 1) ? 0 : rng.nextInt((int) offsetX * 2) - (int) offsetX) + (((offsetX - (int) offsetX <= 0) ? 0 : (rng.nextInt((int) ((offsetX - (int) offsetX) * 200)) - (int) ((offsetX - (int) offsetX) * 100)) / 100D))
                ).setY(
                        ((offsetY < 1) ? 0 : rng.nextInt((int) offsetY * 2) - (int) offsetY) + (((offsetY - (int) offsetY <= 0) ? 0 : (rng.nextInt((int) ((offsetY - (int) offsetY) * 200)) - (int) ((offsetY - (int) offsetY) * 100)) / 100D))
                ).setZ(
                        ((offsetZ < 1) ? 0 : rng.nextInt((int) offsetZ * 2) - (int) offsetZ) + (((offsetZ - (int) offsetZ <= 0) ? 0 : (rng.nextInt((int) ((offsetZ - (int) offsetZ) * 200)) - (int) ((offsetZ - (int) offsetZ) * 100)) / 100D))
                );
    }

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
























