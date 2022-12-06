package net.malevy.mazeserver.domain;

import java.util.EnumSet;

public enum Directions {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    EXIT;

    /**
     * A set of all directions
     */
    public static final EnumSet<Directions> ALL() {
        return EnumSet.of(NORTH, SOUTH, EAST, WEST);
    }

    ;

    /**
     * Factory method for creating an empty set of Directions
     */
    public static final EnumSet<Directions> EMPTY() {
        return EnumSet.noneOf(Directions.class);
    }

    ;

    /**
     * Returns the direction opposite the given direction
     *
     * @param d the given direction
     * @return the opposite direction
     * @throws IndexOutOfBoundsException when the given direction is not mapped for an opposite
     */
    public static Directions oppositeOf(Directions d) {
        if (d == NORTH) return SOUTH;
        else if (d == SOUTH) return NORTH;
        else if (d == EAST) return WEST;
        else if (d == WEST) return EAST;
        else throw new IndexOutOfBoundsException("Unmapped direction: " + d);
    }

}
