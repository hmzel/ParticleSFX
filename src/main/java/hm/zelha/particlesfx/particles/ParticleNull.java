package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import hm.zelha.particlesfx.shapers.parents.ParticleShaper;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Display does nothing in this class. it's meant to be used in {@link ParticleShaper#addParticle(Particle, int)}
 * to allow for certain spots to display nothing
 */
public class ParticleNull extends Particle {
    /**@see ParticleNull*/
    public ParticleNull() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void displayForPlayers(Location location, Player... players) {
    }

    @Override
    public void display(Location location) {
    }

    @Override
    protected void display(Location location, List<CraftPlayer> players) {
    }

    @Override
    public ParticleNull inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleNull clone() {
        return new ParticleNull().inherit(this);
    }
}