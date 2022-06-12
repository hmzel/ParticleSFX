package hm.zelha.particlesfx.particles.parents;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
        display(location, location.getWorld());
    }

    public void displayForPlayer(Location location, Player player) {
        display(location, player);
    }

    public void displayForPlayers(Location location, Player... players) {
        for (Player player : players) {
            display(location, player);
        }
    }

    private void display(Location location, Object toPlayOn) {
        int dataValue = 0;

        if (data instanceof Directional.Direction) data = ((Directional.Direction) data).getValue();

        if (toPlayOn instanceof Player) {
            ((Player) toPlayOn).spigot().playEffect(location, particle, particle.getId(), dataValue, (float) offsetX, (float) offsetY, (float) offsetZ, (float) speed, count, radius);
        } else {
            ((World) toPlayOn).spigot().playEffect(location, particle, particle.getId(), dataValue, (float) offsetX, (float) offsetY, (float) offsetZ, (float) speed, count, radius);
        }
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
























