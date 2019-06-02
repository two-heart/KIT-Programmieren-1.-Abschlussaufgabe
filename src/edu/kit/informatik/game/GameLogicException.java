package edu.kit.informatik.game;

/**
 * Exception that is thrown when a {@link Game} method was called incorrectly.
 *
 * @author ████████████
 * @version 1.0
 */
// I am kind of unsure if one single exception is sufficient. But at the same time there is not
// that much extra information that those exceptions could provide while a great number of
// exceptions would make the program more complex. The discussion in the forum also suggests that
// the details provided by the static error messages are sufficient.
public class GameLogicException extends Exception {
    /**
     * The provided message can be obtained like in any <code>Exception</code> with the
     * <code>getMessage()</code> method.
     *
     * @param message the cause of the exception in human readable from.
     */
    GameLogicException(final String message) {
        super(message);
    }
}
