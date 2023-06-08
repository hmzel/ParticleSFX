package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.List;

/**
 * due to how the minecraft systems work, offset is only usable if the color is NoteColor.RANDOM,
 * so I didn't bother to add constructors for that <br>
 * feel free to use setOffset() methods though!
 * <br><br>
 * also, all ParticleNotes with a NoteColor other than NoteColor.RANDOM have a fixed radius that is quite small,
 * due to minecraft, again.
 */
public class ParticleNote extends Particle {

    private final BlockPosition.MutableBlockPosition pos = new BlockPosition.MutableBlockPosition();
    private NoteColor color;

    /**@see ParticleNote*/
    public ParticleNote(NoteColor color, int count) {
        super(EnumParticle.NOTE, 0, 0, 0, count);

        setNoteColor(color);
    }

    /**@see ParticleNote*/
    public ParticleNote(NoteColor color) {
        this(color, 1);
    }

    /**@see ParticleNote*/
    public ParticleNote(int count) {
        this(NoteColor.RANDOM, count);
    }

    /**@see ParticleNote*/
    public ParticleNote() {
        this(NoteColor.RANDOM, 1);
    }

    @Override
    public ParticleNote inherit(Particle particle) {
        super.inherit(particle);

        if (particle instanceof ParticleNote) {
            color = ((ParticleNote) particle).color;
        }

        return this;
    }

    @Override
    public ParticleNote clone() {
        return new ParticleNote().inherit(this);
    }

    @Override
    protected Packet getStrangePacket(Location location) {
        if (color != NoteColor.RANDOM) {
            return new PacketPlayOutBlockAction(
                    pos.c(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                    Blocks.NOTEBLOCK, 0, color.getValue()
            );
        }

        return null;
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
     * are locked to 0.5, 0.5, 0.5 of the block they're spawned at
     */
    public enum NoteColor {
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