package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.NoteParticle;
import hm.zelha.particlesfx.particles.parents.Particle;
import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;

/**
 * due to how the internal systems work, offset is only useable if the color is NoteColor.RANDOM,
 * so i didnt bother to add constructors for that
 * <p>
 * feel free to use Particle.setOffset(?) though!
 * <p></p>
 * also, all ParticleNotes with a NoteColor other than NoteColor.RANDOM have a fixed radius that is quite small,
 * due to internal systems, again.
 * <p></p>
 * (speed is unused regardless)
 */
public class ParticleNote extends Particle implements NoteParticle {

    private NoteColor color;

    public ParticleNote(NoteColor color, int count) {
        super(Effect.NOTE, 0, 0, 0, 0, count, 0);

        Validate.notNull(color, "Color cannot be null!");

        this.color = color;
    }

    public ParticleNote(NoteColor color) {
        this(color, 1);
    }

    public ParticleNote(int count) {
        this(NoteColor.RANDOM, count);
    }

    public ParticleNote() {
        this(NoteColor.RANDOM, 1);
    }

    @Override
    public void setNoteColor(NoteColor color) {
        Validate.notNull(color, "Color cannot be null!");

        this.color = color;
    }

    @Override
    public NoteColor getNoteColor() {
        return color;
    }
}
