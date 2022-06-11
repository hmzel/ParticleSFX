package hm.zelha.particlesfx.particles.parents;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Particle {

    private final Effect particle;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float speed;
    private int count;
    private int radius;

    protected Particle(Effect particle, float offsetX, float offsetY, float offsetZ, float speed, int count, int radius) {
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
        location.getWorld().spigot().playEffect(location, particle, particle.getId(), 0, offsetX, offsetY, offsetZ, speed, count, radius);
    }

    public void displayForPlayer(Location location, Player player) {
        player.spigot().playEffect(location, particle, particle.getId(), 0, offsetX, offsetY, offsetZ, speed, count, radius);
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }

    public int getRadius() {
        return radius;
    }
}
























