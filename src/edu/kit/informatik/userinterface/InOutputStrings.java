package edu.kit.informatik.userinterface;

/**
 * String constants for the command line interface. The user output should not be
 * obtained via the {@link Enum#name()} method but via the {@link Enum#toString()} method. This
 * class contains all <b>text</b> that during the normal is printed to the user and some
 * frequently used input patterns.
 *
 * @author ████████████
 * @version 1.0
 */
// This class is not meant for variables like numbers or so. I made this class in order to make
// it easy to change the wording or even translate this program in the future.
public enum InOutputStrings {
    /**
     * A point regex for positive value coordinates with one or two digits.
     * Beware when combining this contains a negative lookahead.
     */
    POINT_PATTERN("(?!(0[0-9]))[0-9]{1,2};(?!(0[0-9]))[0-9]{1,2}"),
    /**
     * A point regex for integer value coordinates with one or two digits.
     * Beware when combining this contains a negative lookahead.
     */
    NEGATIVE_POINT_PATTERN("(?!((0[0-9])|-0.*))-?[0-9]{1,2};(?!((0[0-9])|-0.*))-?[0-9]{1,2}"),
    /**
     * The symbol for 7 on the dice.
     */
    DICE_SEVEN("DAWN"),
    /**
     * Separates the command from its potential arguments.
     */
    COMMAND_SEPARATOR(" "),
    /**
     * Separates the two components of a coordinate.
     */
    POINT_SEPARATOR(";"),
    /**
     * Separates the different coordinates (coordinates).
     */
    ARGUMENT_SEPARATOR(":"),
    /**
     * An empty field on the board.
     */
    EMPTY_FIELD_CHAR("-"),
    /**
     * An claimed field on the board. Claimed by player nature with their token VESTA.
     */
    VESTA_CHAR("V"),
    /**
     * An claimed field on the board. Claimed by player nature with their token CERES.
     */
    CERES_CHAR("C"),
    /**
     * An claimed field on the board. Claimed by player mission control.
     */
    MISSION_CONTROL_CHAR("+"),
    /**
     * To indicate a operation went well.
     */
    POSITIVE("OK"),
    /**
     * To tell the user a command exists, but expects different arguments.
     */
    WRONG_ARGUMENTS("the supplied argument is invalid."),
    /**
     * To tell the user is no such command.
     */
    NO_MATCHING_COMMAND("no matching command found.");

    private final String text;

    /**
     * @param text The string that might be used to output it to the user
     */
    InOutputStrings(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
