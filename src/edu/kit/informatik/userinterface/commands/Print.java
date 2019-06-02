package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;

import java.util.regex.Pattern;

/**
 * This class deals with user (in-) and output.
 * It prints the board as a human readable string consisting of multiple lines. It asserts the
 * game logic returns a sting consisting out of numbers from 0 to 3.
 *
 * @author ████████████
 * @version 1.0
 */
public class Print extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile("print");
    private Game game;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Print() {
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
        ReadWrite.writeLine(game.getBoardRepresentation()
                .replace("0", InOutputStrings.EMPTY_FIELD_CHAR.toString())
                .replace("1", InOutputStrings.VESTA_CHAR.toString())
                .replace("2", InOutputStrings.CERES_CHAR.toString())
                .replace("3", InOutputStrings.MISSION_CONTROL_CHAR.toString()));
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
