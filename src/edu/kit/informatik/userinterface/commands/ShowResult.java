package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.GameLogicException;
import edu.kit.informatik.userinterface.Session;

import java.util.regex.Pattern;

/**
 * This class deals with user (in-) and output.
 * This command prints the result of the game to the user. The result is an integer value.
 *
 * @author ████████████
 * @version 1.0
 */
public class ShowResult extends Command {
    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile("show-result");
    private Game game;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    ShowResult() {
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
        try {
            ReadWrite.writeLine(game.calculateResult());
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
        }
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
