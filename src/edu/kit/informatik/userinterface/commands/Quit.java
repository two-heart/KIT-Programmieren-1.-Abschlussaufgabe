package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.userinterface.Session;

import java.util.regex.Pattern;

/**
 * This class indirectly deals with user input.
 * Command to terminate the program.
 *
 * @author ████████████
 * @version 1.0
 */
class Quit extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile("quit");
    private Session session;


    /**
     * Package private constructor to avoid direct initialisation without using the
     * <code>CommandFactory</code>.
     */
    Quit() {
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
        assert (session != null);
        session.terminate();
    }

    @Override
    void setSession(final Session session) {
        this.session = session;
    }

}
