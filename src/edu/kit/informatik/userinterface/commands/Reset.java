package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;

import java.util.regex.Pattern;

/**
 * This class indirectly deals with user input.
 * Command to restart the game.
 *
 * @author ████████████
 * @version 1.0
 */
public class Reset extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile("reset");

    private Session session;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Reset() {
    }

    /**
     * Obtain a regex pattern for this command.
     *
     * @return a pattern that, can be used to decide if this is the right command for a given
     * user input.
     */
    static Pattern getDefaultPattern() {
        return PATTERN;
    }

    @Override
    public void execute() {
        session.reset();
        ReadWrite.writeLine(InOutputStrings.POSITIVE);
    }

    @Override
    void setSession(final Session session) {
        this.session = session;
    }
}
