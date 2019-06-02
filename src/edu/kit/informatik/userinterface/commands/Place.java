package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.GameLogicException;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;
import edu.kit.informatik.util.Tuple;

import java.util.regex.Pattern;

/**
 * This class deals with user in- and output.
 * It allows the player mission control to set their tokens on the board.
 * Provide arguments via {@link this#setArguments(String)}.
 *
 * @author ████████████
 * @version 1.0
 */
public class Place extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile(String.format("place%s%s:%s",
            InOutputStrings.COMMAND_SEPARATOR,
            InOutputStrings.NEGATIVE_POINT_PATTERN,
            InOutputStrings.NEGATIVE_POINT_PATTERN));

    private Game game;
    private Tuple<Integer> from;
    private Tuple<Integer> to;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Place() {
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
            game.place(from, to);
            ReadWrite.writeLine(InOutputStrings.POSITIVE);
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
        }
    }

    @Override
    void setArguments(final String argument) {
        String[] stringPoints = argument.split(InOutputStrings.ARGUMENT_SEPARATOR.toString());
        from = Command.splitPoint(stringPoints[0]);
        to = Command.splitPoint(stringPoints[1]);
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
