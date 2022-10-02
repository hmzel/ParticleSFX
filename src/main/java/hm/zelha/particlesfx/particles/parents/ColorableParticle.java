package hm.zelha.particlesfx.particles.parents;

import hm.zelha.particlesfx.particles.ParticleDust;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ColorableParticle extends Particle {

    private Color color;
    private int brightness;

    protected ColorableParticle(EnumParticle particle, @Nullable Color color, int brightness, double offsetX, double offsetY, double offsetZ, int count) {
        super(particle, offsetX, offsetY, offsetZ, 1, count, 0);

        this.color = color;
        setBrightness(brightness);
    }

    @Override
    protected void display(Location location, Player... players) {
        Validate.notNull(location, "Location cannot be null!");
        Validate.notNull(location.getWorld(), "World cannot be null!");

        int count2 = (color != null) ? count : 1;

        for (int i = 0; i != count2; i++) {
            int trueCount = count;
            double trueOffsetX = offsetX;
            double trueOffsetY = offsetY;
            double trueOffsetZ = offsetZ;
            double trueSpeed;
            Vector addition = null;

            if (color != null) {
                trueCount = 0;
                trueSpeed = brightness * 0.01;
                trueOffsetX = color.getRed() / 255D;
                trueOffsetY = color.getGreen() / 255D;
                trueOffsetZ = color.getBlue() / 255D;

                //dont judge me
                if (this instanceof ParticleDust) trueOffsetX -= 1;

                addition = generateFakeOffset();
            } else {
                trueSpeed = 1;
            }

            if (addition != null) location.add(addition);

            for (Player player : players) {
                EntityPlayer p = ((CraftPlayer) player).getHandle();

                if (!location.getWorld().getName().equals(p.world.getWorld().getName())) continue;

                if (radius != 0 && (Math.abs(location.getX() - p.locX) + Math.abs(location.getY() - p.locY) + Math.abs(location.getZ() - p.locZ)) > radius) {
                    continue;
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
     * @param color color to set, null if you want random coloring
     */
    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public void setColor(int red, int green, int blue) {
        this.color = Color.fromRGB(red, green, blue);
    }

    public void setBrightness(int brightness) {
        Validate.isTrue(brightness >= 0 && brightness <= 100, "Brightness must be between 0 and 100!");

        this.brightness = brightness;
    }

    /**
     * nullable to allow for randomly colored particles without use of boolean constructors
     */
    @Nullable
    public Color getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }
}
