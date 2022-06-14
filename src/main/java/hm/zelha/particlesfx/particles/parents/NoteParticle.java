package hm.zelha.particlesfx.particles.parents;

public interface NoteParticle {

    public void setNoteColor(NoteColor color);

    public NoteColor getNoteColor();

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
