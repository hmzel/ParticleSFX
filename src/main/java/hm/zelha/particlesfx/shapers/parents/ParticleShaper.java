package hm.zelha.particlesfx.shapers.parents;

import hm.zelha.particlesfx.util.ParticleSFX;
import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.util.ShapeDisplayMechanic;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.World;
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
    protected int particleFrequency;
    protected int particlesPerDisplay = 0;
    protected int currentCount = 0;
    protected int overallCount = 0;
    protected int delay = 1;

    public ParticleShaper(Particle particle, int particleFrequency) {
        setParticle(particle);
        setParticleFrequency(particleFrequency);
    }

    public Shape start() {
        if (animator != null) return this;

        Validate.isTrue(ParticleSFX.getPlugin() != null, "Plugin is null! please put ParticleSFX.setPlugin(this) in your onEnable() method!");

        animator = new BukkitRunnable() {
            @Override
            public void run() {
                display();
            }
        }.runTaskTimerAsynchronously(ParticleSFX.getPlugin(), 1, delay);

        return this;
    }

    public Shape stop() {
        if (animator == null) return this;

        animator.cancel();
        animator = null;

        return this;
    }

    public abstract void display();

    public abstract Shape clone();

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

    /**
     * Similar to {@link java.util.function.Consumer} <br>
     * in the case of phases {@link ShapeDisplayMechanic.Phase#BEFORE_ROTATION} and {@link ShapeDisplayMechanic.Phase#AFTER_ROTATION}
     * the given mechanic will run before the location is modified to display the next particle, allowing you to modify the
     * addition vector however you want, though doing so may be very volatile
     * <br><br>
     * keep in mind that all changes to the given objects will be reflected in the display() method, and considering that
     * the display() method often displays hundreds of particles, try to make sure the mechanic isn't very
     * resource-intensive
     * <br><br>
     * {@link ShapeDisplayMechanic#apply(Particle, Location, Vector, int)}
     *
     * @param phase phase that the mechanic should run at
     * @param mechanic mechanic to run during display
     */
    public void addMechanic(ShapeDisplayMechanic.Phase phase, ShapeDisplayMechanic mechanic) {
        mechanics.add(Pair.of(mechanic, phase));
    }

    /**
     * adds a player for this shape to display to, defaults to all online players in the shape's world if the list is empty
     *
     * @param player player to add
     */
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
    }

    /**
     * adds a player for this shape to display to, defaults to all online players in the shape's world if the list is empty
     *
     * @param uuid ID of player to add
     */
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

    /**
     * @see ParticleShaper#addMechanic(ShapeDisplayMechanic.Phase, ShapeDisplayMechanic)
     * @param index index of mechanic in list
     */
    public void removeMechanic(int index) {
        mechanics.remove(index);
    }

    /**
     * @see ParticleShaper#addPlayer(Player)
     * @param index index of player to remove from list
     */
    public void removePlayer(int index) {
        players.remove(index);
    }

    /**
     * @see ParticleShaper#addPlayer(Player)
     * @param player player to remove from list
     */
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

    /** @param particleFrequency amount of times to display the particle per full animation */
    public void setParticleFrequency(int particleFrequency) {
        Validate.isTrue(particleFrequency >= 2, "Frequency cannot be less than 2! if you only want one particle, use Particle.display()");

        this.particleFrequency = particleFrequency;
    }

    /**
     * 0 means that the entire animation will be played when .display() is called
     *
     * @param particlesPerDisplay amount of particles that will be shown per display
     */
    public void setParticlesPerDisplay(int particlesPerDisplay) {
        this.particlesPerDisplay = particlesPerDisplay;
    }

    public void setDelay(int delay) {
        stop();

        this.delay = delay;

        start();
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

    /**
     * @see ParticleShaper#setParticlesPerDisplay(int) 
     * @return the amount of particles per display
     */
    public int getParticlesPerDisplay() {
        return particlesPerDisplay;
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
     * @return the UUIDs of all the players that this shape displays to. displays to all players if this list is empty
     */
    public List<UUID> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * @return amount of players this shape displays to
     */
    public int getPlayerAmount() {
        if (players.isEmpty()) return locationHelper.getWorld().getPlayers().size();

        return players.size();
    }

    public boolean isRunning() {
        return animator != null;
    }
}
