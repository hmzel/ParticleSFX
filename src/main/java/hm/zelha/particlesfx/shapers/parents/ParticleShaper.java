package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.ParticleSFX;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ParticleShaper extends RotationHandler implements Shape {

    protected final List<Pair<Particle, Integer>> secondaryParticles = new ArrayList<>();
    protected final List<Pair<ShapeDisplayMechanic, ShapeDisplayMechanic.Phase>> mechanics = new ArrayList<>();
    /* its actually more efficient to use list<pair<>>s here instead of LinkedHashMaps, because in order to loop through LinkedHashMaps you
     * have to create a new Iterator and a new LinkedEntrySet every time getCurrentParticle() or applyMechanics() is called,
     * which could be thousands of times every tick in normal use cases. whereas with a List<Pair<>> you can just use a for-i loop and
     * the .get(int) method without creating any objects */
    protected final List<UUID> players = new ArrayList<>();
    protected final Location locationHelper = new Location(null, 0, 0, 0);
    protected final Vector vectorHelper = new Vector();
    protected BukkitTask animator = null;
    protected Particle particle;
    protected boolean async = true;
    protected int particleFrequency;
    protected int particlesPerDisplay = 0;
    protected int currentCount = 0;
    protected int overallCount = 0;
    protected int delay = 1;

    public ParticleShaper(Particle particle, int particleFrequency) {
        setParticle(particle);
        setParticleFrequency(particleFrequency);
    }

    public ParticleShaper start() {
        if (animator != null) return this;

        Validate.isTrue(ParticleSFX.getPlugin() != null, "Plugin is null! please put ParticleSFX.setPlugin(this) in your onEnable() method!");

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                display();
            }
        };

        if (async) {
            animator = runnable.runTaskTimerAsynchronously(ParticleSFX.getPlugin(), 1, delay);
        } else {
            animator = runnable.runTaskTimer(ParticleSFX.getPlugin(), 1, delay);
        }

        return this;
    }

    public ParticleShaper stop() {
        if (animator == null) return this;

        animator.cancel();

        animator = null;

        return this;
    }

    public ParticleShaper clone() {
        ParticleShaper clone = cloneConstructor().stop();
        clone.currentCount = currentCount;
        clone.overallCount = overallCount;
        clone.delay = delay;
        clone.async = async;

        clone.rot.inherit(rot);
        clone.rot2.inherit(rot2);
        clone.originalCentroid.zero().add(originalCentroid);
        clone.lastRotatedAround.zero().add(lastRotatedAround);
        clone.secondaryParticles.addAll(secondaryParticles);
        clone.mechanics.addAll(mechanics);
        clone.players.addAll(players);
        clone.setParticlesPerDisplay(particlesPerDisplay);

        for (int i = 0; i < origins.size(); i++) {
            clone.origins.get(i).zero().add(origins.get(i));
        }

        if (animator != null) clone.start();

        return clone;
    }

    public abstract void display();

    protected abstract ParticleShaper cloneConstructor();

    protected Particle getCurrentParticle() {
        Particle particle = this.particle;

        for (int i = 0; i < secondaryParticles.size(); i++) {
            Pair<Particle, Integer> pair = secondaryParticles.get(i);

            if (overallCount < pair.getValue()) break;

            particle = pair.getKey();
        }

        return particle;
    }

    protected void applyMechanics(ShapeDisplayMechanic.Phase phase, Particle particle, Location current, Vector addition) {
        for (int i = 0; i < mechanics.size(); i++) {
            Pair<ShapeDisplayMechanic, ShapeDisplayMechanic.Phase> pair = mechanics.get(i);

            if (pair.getValue() != phase) continue;

            pair.getKey().apply(particle, current, addition, overallCount);
        }
    }

    /**
     * adds a particle for this shape to display after a certain amount of other particles
     *
     * @param particle particle to add
     * @param particlesUntilDisplay how many particles need to be displayed before this one
     */
    public void addParticle(Particle particle, int particlesUntilDisplay) {
        secondaryParticles.add(Pair.of(particle, particlesUntilDisplay));
    }

    public void addMechanic(ShapeDisplayMechanic.Phase phase, ShapeDisplayMechanic mechanic) {
        mechanics.add(Pair.of(mechanic, phase));
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
    }

    public void addPlayer(UUID uuid) {
        players.add(uuid);
    }

    /**
     * @see ParticleShaper#addParticle(Particle, int)
     * @param index index of particle in list (main particle is not in list)
     */
    public void removeParticle(int index) {
        secondaryParticles.remove(index);
    }

    public void removeMechanic(int index) {
        mechanics.remove(index);
    }

    public void removePlayer(int index) {
        players.remove(index);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    /**
     * @param player player to check for
     * @return whether this shape displays to this player
     */
    public boolean hasPlayer(Player player) {
        if (players.isEmpty()) return true;

        return players.contains(player.getUniqueId());
    }

    public void setParticle(Particle particle) {
        Validate.notNull(particle, "Particle cannot be null!");

        this.particle = particle;
    }

    public void setParticleFrequency(int particleFrequency) {
        Validate.isTrue(particleFrequency >= 2, "Frequency cannot be less than 2! if you only want one particle, use Particle.display()");

        this.particleFrequency = particleFrequency;
    }

    public ParticleShaper setParticlesPerDisplay(int particlesPerDisplay) {
        this.particlesPerDisplay = particlesPerDisplay;

        return this;
    }

    public ParticleShaper setAsync(boolean async) {
        boolean running = isRunning();

        if (running) stop();

        this.async = async;

        if (running) return start(); else return stop();
    }

    public ParticleShaper setDelay(int delay) {
        boolean running = isRunning();

        if (running) stop();

        this.delay = delay;

        if (running) return start(); else return stop();
    }

    public void setDisplayPosition(int position) {
        currentCount = 0;
        overallCount = position;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        locationHelper.setWorld(world);
    }

    public Particle getParticle() {
        return particle;
    }

    /**
     * @see ParticleShaper#addParticle(Particle, int) 
     * @param index index of particle to get from the list
     * @return a pair of the particle and the amount of particles before it should be displayed
     */
    public Pair<Particle, Integer> getSecondaryParticle(int index) {
        return secondaryParticles.get(index);
    }

    public int getParticleFrequency() {
        return particleFrequency;
    }

    public int getParticlesPerDisplay() {
        return (particlesPerDisplay == 0) ? particleFrequency : particlesPerDisplay;
    }

    /**
     * @return amount of ticks between {@link ParticleShaper#display()} being called
     */
    public int getDelay() {
        return delay;
    }

    /**
     * @see ParticleShaper#addParticle(Particle, int) 
     * @return the amount of extra particles that this shape uses
     */
    public int getSecondaryParticleAmount() {
        return secondaryParticles.size();
    }

    /**
     * @see ParticleShaper#addMechanic(ShapeDisplayMechanic.Phase, ShapeDisplayMechanic)
     * @return the amount of mechanics used by this shape
     */
    public int getMechanicAmount() {
        return mechanics.size();
    }

    public List<UUID> getPlayers() {
        return new ArrayList<>(players);
    }

    public int getPlayerAmount() {
        if (players.isEmpty()) return locationHelper.getWorld().getPlayers().size();

        return players.size();
    }

    public boolean isAsync() {
        return async;
    }

    public boolean isRunning() {
        return animator != null;
    }
}