package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.ColorableParticle;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.List;

public class ParticleDust extends ColorableParticle {

    private boolean pureColor = false;

    public ParticleDust(@Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.REDSTONE, color, brightness, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ, int count) {
        this(null, 100, offsetX, offsetY, offsetZ, count);
    }

    public ParticleDust(double offsetX, double offsetY, double offsetZ) {
        this(null, 100, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleDust(@Nullable Color color, int brightness, int count) {
        this(color, brightness, 0, 0, 0, count);
    }

    public ParticleDust(@Nullable Color color, int brightness) {
        this(color, brightness, 0, 0, 0, 1);
    }

    public ParticleDust(@Nullable Color color) {
        this(color, 100, 0, 0, 0, 1);
    }

    public ParticleDust(int count) {
        this(null, 100, 0, 0, 0, count);
    }

    public ParticleDust() {
        this(null, 100, 0, 0, 0, 1);
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        for (int i = 0; i != ((color != null) ? count : 1); i++) {
            int trueCount = count;
            double trueOffsetX = offsetX;
            double trueOffsetY = offsetY;
            double trueOffsetZ = offsetZ;
            double trueSpeed;
            Vector addition = null;

            if (color != null) {
                trueCount = 0;

                if (pureColor) {
                    trueSpeed = 1;
                    trueOffsetX = Float.MAX_VALUE * (color.getRed() / 255D);
                    trueOffsetY = Float.MAX_VALUE * (color.getGreen() / 255D);
                    trueOffsetZ = Float.MAX_VALUE * (color.getBlue() / 255D);

                    if (trueOffsetX == 0) trueOffsetX = Float.MIN_VALUE;
                    if (trueOffsetY == 0) trueOffsetY = Float.MIN_VALUE;
                    if (trueOffsetZ == 0) trueOffsetZ = Float.MIN_VALUE;
                } else {
                    trueSpeed = brightness * 0.01;
                    trueOffsetX = color.getRed() / 255D;
                    trueOffsetY = color.getGreen() / 255D;
                    trueOffsetZ = color.getBlue() / 255D;

                    if (color.getRed() == 0) {
                        trueOffsetX = 0.0001;
                    }
                }

                addition = generateFakeOffset();

                location.add(addition);
            } else {
                trueSpeed = 1;
            }

            for (int i2 = 0; i2 < players.size(); i2++) {
                EntityPlayer p = players.get(i2).getHandle();

                if (p == null) continue;
                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0) {
                    double distance = Math.sqrt(Math.pow(location.getX() - p.locX, 2) + Math.pow(location.getY() - p.locY, 2) + Math.pow(location.getZ() - p.locZ, 2));

                    if (distance > radius) continue;
                }

                p.playerConnection.sendPacket(
                        new PacketPlayOutWorldParticles(
                                particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                (float) trueOffsetX, (float) trueOffsetY, (float) trueOffsetZ, (float) trueSpeed, trueCount
                        )
                );
            }

            if (addition != null) location.subtract(addition);
        }
    }

    /**
     * if this is set to true, the display method will use derivatives of Float.MAX_VALUE to try and eliminate the variations
     * in color when normally using reddust.
     * <p></p>
     * however, if this is the case brightness is unused and will always be 100 internally.
     * <p></p>
     * and you're kinda locked into specific colors because lowering the brightness will make it not pure
     * aka no purple or brown or other colors like that
     *
     * @param pureColor whether or not the color should be pure
     */
    public ParticleDust setPureColor(boolean pureColor) {
        this.pureColor = pureColor;

        return this;
    }

    public boolean isPureColor() {
        return pureColor;
    }
}
