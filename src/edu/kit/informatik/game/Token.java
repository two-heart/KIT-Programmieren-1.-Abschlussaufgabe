package edu.kit.informatik.game;

/**
 * Representation of the tokens on the board.
 *
 * @author ████████████
 * @version 1.0
 */
public enum Token {
    /**
     * A field is not occupied.
     */
    EMPTY(0),
    /**
     * A field is occupied by the orb Vesta controlled by Nature.
     */
    VESTA(1),
    /**
     * A field is occupied by the orb Ceres controlled by Nature.
     */
    CERES(2),
    /**
     * A field is occupied by a mission control token.
     */
    MISSION_CONTROL(3);

    private final int representation;

    /**
     * @param representation the number on the board.
     */
    Token(final int representation) {
        this.representation = representation;
    }

    /**
     * Get the representation of the tokens on the board.
     *
     * @return an integer between 0 and 3.
     */
    int getNumber() {
        return representation;
    }
}
