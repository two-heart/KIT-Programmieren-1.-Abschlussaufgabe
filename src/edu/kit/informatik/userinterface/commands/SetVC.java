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
 * Command that player nature uses to initially set the position of Vesta or Ceres.
 * Provide arguments via {@link this#setArguments(String)}.
 *
 * @author ████████████
 * @version 1.0
 */
public class SetVC extends Command {

    /**
     * The syntax of the command as a regex.
     */
    private static final Pattern PATTERN = Pattern.compile("set-vc"
            + InOutputStrings.COMMAND_SEPARATOR + InOutputStrings.POINT_PATTERN.toString());

    private Tuple<Integer> point;
    private Game game;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    SetVC() {
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
            game.setVC(point);
            ReadWrite.writeLine(InOutputStrings.POSITIVE);
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
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
