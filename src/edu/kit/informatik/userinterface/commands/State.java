package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.GameLogicException;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;
import edu.kit.informatik.util.Tuple;

import java.util.regex.Pattern;

/**
 * This class deals with user (in-) and output.
 * It asserts the game logic returns a sting consisting out of numbers from 0..3.
 * Provide arguments via {@link this#setArguments(String)}.
 *
 * @author ████████████
 * @version 1.0
 */
public class State extends Command {

    /**
     * The syntax of the command as a regex.
     */
    // If one would ever consider changing the board size this has to be changed as well. But I
    // think having such a huge field would be kind of unusable in general.
    private static final Pattern PATTERN = Pattern.compile("state"
            + InOutputStrings.COMMAND_SEPARATOR + InOutputStrings.POINT_PATTERN);
    private static final int SYMBOL_EMPTY = 0;
    private static final int SYMBOL_VESTA = 1;
    private static final int SYMBOL_CERES = 2;
    private static final int SYMBOL_MISSION_CONTROL = 3;

    private Tuple<Integer> point;
    private Game game;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    State() {
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
        int tokenNumber;
        try {
            tokenNumber = game.state(point);
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
            return;
        }
        ReadWrite.writeLine(fieldNumberToString(tokenNumber));
    }

    private String fieldNumberToString(final int number) {
        switch (number) {
            case SYMBOL_EMPTY:
                return InOutputStrings.EMPTY_FIELD_CHAR.toString();
            case SYMBOL_VESTA:
                return InOutputStrings.VESTA_CHAR.toString();
            case SYMBOL_CERES:
                return InOutputStrings.CERES_CHAR.toString();
            case SYMBOL_MISSION_CONTROL:
                return InOutputStrings.MISSION_CONTROL_CHAR.toString();
            default:
                throw new AssertionError("This is a bug.");
        }
    }

    @Override
    void setArguments(final String argument) {
        point = Command.splitPoint(argument);
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
