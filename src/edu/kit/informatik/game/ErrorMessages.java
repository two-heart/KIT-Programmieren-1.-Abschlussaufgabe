package edu.kit.informatik.game;

/**
 * Error messages in one single place. Remember to use call {@link this#toString()} on them.
 *
 * @author ████████████
 * @version 1.0
 */
// One could argue that this strings do not belong in the game logic, but an alternative solution
// would be way to much for this little program and it is still easy to simply replace this
// strings in the ui.
enum ErrorMessages {
    /**
     * An attempt is made to place a token outside of the field. With this wording in can be
     * used for DAWN as well.
     */
    BOUNDS("the token must be placed on the field."),
    /**
     * An attempt is made to place a token on an already occupied field.
     */
    OCCUPIED("this field is already occupied."),
    /**
     * Mission control specifies a wrong length of their token.
     */
    LENGTH("not allowed to use this length."),
    /**
     * The dice value is not between 2-7.
     */
    DICED("illegal dice value."),
    /**
     * Nature skips one or more fields.
     */
    CONSECUTIVE("fields must be consecutive."),
    /**
     * A mission control token was neither vertical nor horizontal.
     */
    SINGLE_LINE("fields must be in one line."),
    /**
     * The moves are less or equal to the token mission control placed.
     */
    MOVES("illegal number of moves."),
    /**
     * A game method is called at a point of the game where this method is not supposed to be
     * called.
     */
    TIME("command not allowed at this point.");

    private final String text;

    /**
     * @param text the error message in human readable form.
     */
    ErrorMessages(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
