package hm.zelha.particlesfx.particles.parents;

public interface DirectionalParticle {

    public void setDirection(Direction direction);

    public Direction getDirection();

    /** while it is possible to get more directions than this, it's only possible to get variants of north west. pretty weird. */
    enum Direction {
        NORTH(1),
        SOUTH(7),
        EAST(5),
        WEST(3),
        NORTH_EAST(2),
        NORTH_WEST(0),
        SOUTH_EAST(8),
        SOUTH_WEST(6),
        NONE(4);

        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
