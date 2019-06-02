package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.GameLogicException;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;
import edu.kit.informatik.util.Tuple;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class deals with user in- and output.
 * Command that player nature uses to change the position of Vesta or Ceres.
 * Provide arguments via {@link this#setArguments(String)}.
 *
 * @author ████████████
 * @version 1.0
 */
public class Move extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile(String.format("move%s%s(:%s)*",
            InOutputStrings.COMMAND_SEPARATOR,
            InOutputStrings.POINT_PATTERN,
            InOutputStrings.POINT_PATTERN));

    private Game game;
    private List<Tuple<Integer>> moves;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Move() {
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
            game.move(moves);
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
            return;
        }
        ReadWrite.writeLine(InOutputStrings.POSITIVE);
    }

    @Override
    void setArguments(final String argument) {
        moves = new LinkedList<>();
        String[] points = argument.split(InOutputStrings.ARGUMENT_SEPARATOR.toString());
        for (String point : points) {
            moves.add(Command.splitPoint(point));
        }
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
