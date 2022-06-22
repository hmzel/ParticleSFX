package hm.zelha.particlesfx.particles;

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
public class ParticleNote extends Particle{

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

    public void setNoteColor(NoteColor color) {
        Validate.notNull(color, "Color cannot be null!");

        this.color = color;
    }

    public NoteColor getNoteColor() {
        return color;
    }

    /**
     * it might look weird if you use NoteColor.RANDOM with other notecolors because all notecolors besides NoteColor.RANDOM
     * are locked to 0.5, 0.5, 0.5 of the block theyre spawned at
     */
    enum NoteColor {
        CYAN(0),
        PURPLE(4),
        PURPLE_TWO(16),
        RED(6),
        YELLOW(7),
        GOLD(8),
        BROWN(9),
        LIGHT_BLUE(12),
        BLUE(14),
        GREENISH_YELLOW(21),
        GREEN(20),
        DESATURATED_CYAN(1),
        DESATURATED_PURPLE(3),
        DESATURATED_PURPLE_TWO(17),
        DESATURATED_LIGHT_BLUE(11),
        DESATURATED_GREEN(19),
        SATURATED_CYAN(23),
        SATURATED_PURPLE(5),
        SATURATED_PURPLE_TWO(15),
        SATURATED_LIGHT_BLUE(13),
        SATURATED_GREEN(22),
        GRAY(10),
        GRAY_TWO(2),
        GRAY_THREE(18),
        RANDOM(-1);

        private final int value;

        NoteColor(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
