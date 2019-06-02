package edu.kit.informatik.userinterface;

import edu.kit.informatik.ReadWrite;

/**
 * Thrown if the user makes an illegal input. This should exception should and must be caught and
 * the user should be printed a error message, without terminating the program.
 *
 * @author ████████████
 * @version 1.0
 */
public class InputException extends Exception {
    /**
     * note this exception can be constructed and used throughout the whole program.
     *
     * @param message The cause of the exception, that might be printed to the user
     *                via {@link ReadWrite#writeError(String)}
     */
    public InputException(final String message) {
        super(message);
    }
}
