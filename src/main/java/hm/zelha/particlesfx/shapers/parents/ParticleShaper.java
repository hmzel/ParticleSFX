package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.Main;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticleShaper extends RotationHandler implements Shape {

    protected final List<Pair<Particle, Integer>> secondaryParticles = new ArrayList<>();
    /* its actually more efficient to use a list<pair<>> here instead of a LinkedHashMap, because in order to determine the current particle using
     * that, you have to create a new Iterator and a new LinkedEntrySet every time getCurrentParticle() is called, which could be hundreds of times
     * every tick in normal use cases. whereas with a List<Pair<>> you can just use a for-i loop and the .get(int) method without creating any objects */
    protected final Location locationHelper = new Location(null, 0, 0, 0);
    protected final Vector vectorHelper = new Vector(0, 0, 0);
    protected BukkitTask animator = null;
    protected ShapeDisplayMechanic mechanic = null;
    protected Particle particle;
    protected double particleFrequency;
    protected int particlesPerDisplay = 0;
    protected int currentCount = 0;
    protected int overallCount = 0;

    public ParticleShaper(Particle particle, double particleFrequency) {
        Validate.notNull(particle, "Particle cannot be null!");
        Validate.isTrue(particleFrequency >= 2.0D, "Frequency cannot be less than 2! if you only want one particle, use Particle.display().");

        this.particle = particle;
        this.particleFrequency = particleFrequency;
    }

    public void start() {
        if (animator != null) return;

        animator = new BukkitRunnable() {
            @Override
            public void run() {
                display();
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 1, 1);
    }

    public void stop() {
        if (animator == null) return;

        animator.cancel();
        animator = null;
    }

    public abstract void display();

    protected Particle getCurrentParticle() {
        Particle particle = this.particle;

        for (int i = 0; i < secondaryParticles.size(); i++) {
            Pair<Particle, Integer> pair = secondaryParticles.get(i);

            if (overallCount >= pair.getValue()) particle = pair.getKey(); else break;
        }

        return particle;
    }

    public void addParticle(Particle particle, int particlesUntilDisplay) {
        secondaryParticles.add(Pair.of(particle, particlesUntilDisplay));
    }

    public void removeParticle(int index) {
        secondaryParticles.remove(index);
    }

    public boolean isRunning() {
        return animator != null;
    }

    public void setParticle(Particle particle) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;
    }

    /** @param particleFrequency amount of times to display the particle per full animation */
    public void setParticleFrequency(double particleFrequency) {
        Validate.isTrue(particleFrequency > 2.0D, "Frequency cannot be less than 2! if you only want one particle, use Particle.display()");

        this.particleFrequency = particleFrequency;
    }

    /**
     * Similar to {@link java.util.function.Consumer} <p>
     * the given mechanic will run before the location is modified to display the next particle, allowing you to modify
     * the addition vector however you want, though doing so may be very volatile
     * <p></p>
     * keep in mind that all changes to the given objects will be reflected in the display() method <p>
     * and, considering that the display() method is often called hundreds of times per tick, try to make sure the mechanic isnt very
     * resource-intensive
     * <p></p>
     * given Particle - particle to be displayed
     * <p></p>
     * given Location - the location the vector will be added to, in some cases this is the last position the particle was displayed,
     * in others it is the center of the shape
     * <p></p>
     * given Vector - the vector that will be added to the location before the particle is displayed
     *
     * @param mechanic mechanic to run during display
     */
    public void setMechanic(ShapeDisplayMechanic mechanic) {
        this.mechanic = mechanic;
    }

    /** 0 means that the entire animation will be played when .display() is called */
    public void setParticlesPerDisplay(int particlesPerDisplay) {
        this.particlesPerDisplay = particlesPerDisplay;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        locationHelper.setWorld(world);
    }

    public Particle getParticle() {
        return particle;
    }

    public double getParticleFrequency() {
        return particleFrequency;
    }

    public int getParticlesPerDisplay() {
        return particlesPerDisplay;
    }

    public ShapeDisplayMechanic getMechanic() {
        return mechanic;
    }
}
