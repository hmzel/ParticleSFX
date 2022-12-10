package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.TravellingParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * EPILEPSY WARNING <br>
 * this particle almost instantly disappears when displayed out of water, which can be displeasing to the eyes
 */
public class ParticleBubble extends TravellingParticle {
    public ParticleBubble(Location toGo, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.WATER_BUBBLE, false, 0.755, null, toGo, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubble(Vector velocity, double offsetX, double offsetY, double offsetZ, int count) {
        super(EnumParticle.WATER_BUBBLE, false, 0.755, velocity, null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubble(Location toGo, double offsetX, double offsetY, double offsetZ) {
        this(toGo, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubble(Vector velocity, double offsetX, double offsetY, double offsetZ) {
        this(velocity, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubble(double offsetX, double offsetY, double offsetZ, int count) {
        this((Location) null, offsetX, offsetY, offsetZ, count);
    }

    public ParticleBubble(double offsetX, double offsetY, double offsetZ) {
        this((Location) null, offsetX, offsetY, offsetZ, 1);
    }

    public ParticleBubble(int count) {
        this((Location) null, 0, 0, 0, count);
    }

    public ParticleBubble(Location toGo) {
        this(toGo, 0, 0, 0, 1);
    }

    public ParticleBubble(Vector velocity) {
        this(velocity, 0, 0, 0, 1);
    }

    public ParticleBubble() {
        this((Location) null, 0, 0, 0, 1);
    }
}
