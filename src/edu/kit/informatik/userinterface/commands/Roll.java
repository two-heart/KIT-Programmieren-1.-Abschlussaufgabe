package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.GameLogicException;
import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;

import java.util.regex.Pattern;

/**
 * This class deals with user in- and output.
 * It reports the result of the dice to the game logic.
 * Provide arguments via {@link this#setArguments(String)}.
 *
 * @author ████████████
 * @version 1.0
 */
public class Roll extends Command {

    /**
     * The syntax of the command as a regex.
     */
    // I think it is okay to hardcode that a dice has 6 sides.
    private static final Pattern PATTERN = Pattern.compile(String.format("roll%s([2-6]|%s)",
            InOutputStrings.COMMAND_SEPARATOR,
            InOutputStrings.DICE_SEVEN));
    private static final int DAWN = 7;
    private int diced;
    private Game game;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Roll() {
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
            game.setDiced(diced);
            ReadWrite.writeLine(InOutputStrings.POSITIVE);
        } catch (GameLogicException e) {
            ReadWrite.writeError(e.getMessage());
        }
    }

    @Override
    void setArguments(final String argument) {
        if (argument.equals(InOutputStrings.DICE_SEVEN.toString())) {
            diced = DAWN;
        } else {
            diced = Integer.parseInt(argument);
        }
    }

    @Override
    void setSession(final Session session) {
        game = session.getGame();
    }
}
