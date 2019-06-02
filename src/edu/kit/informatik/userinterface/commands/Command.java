package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.userinterface.InOutputStrings;
import edu.kit.informatik.userinterface.Session;
import edu.kit.informatik.util.Tuple;


/**
 * A commands task is to perform an operation on the game depending on the arguments given to the
 * command.
 *
 * Each command should extend this in order to be created and distributed by the
 * {@link CommandFactory}.
 *
 * @author ████████████
 * @version 1.0
 */
// There are multiple reasons that this is an abstract class rather than an interface: One being
// style reasons. I am not a fan of default methods in interfaces, but at the same time I don't
// want to implement setArguments(String argument) if it isn't used. Also splitPoint(String pointText)
// would as a static methods of an interface would be questionable. Another reason, is that an
// interface does not allow package private methods and public methods would be contrary to the
// idea of this package.
public abstract class Command {
    /**
     * Handy frequently used helper method to split a point as defined by the user in a tuple
     * representing a point.
     *
     * @param pointText must be of the form Integer;Integer
     * @return the point as a tuple
     */
    protected static Tuple<Integer> splitPoint(String pointText) {
        String[] spited = pointText.split(InOutputStrings.POINT_SEPARATOR.toString());
        return new Tuple<>(Integer.parseInt(spited[0]), Integer.parseInt(spited[1]));
    }

    /**
     * Runs the command.
     */
    public abstract void execute();

    /**
     * This allows the a command to manipulate the session. E.g. call <code>terminate()</code> on
     * it or most commonly get the current <code>Game</code>.
     * For commands that don't have to interact with the session this will be ignored.
     *
     * @param session the session the creating factory was initiated with.
     */
    abstract void setSession(Session session);

    /**
     * Passes valid arguments to the command. If the command does not expect any arguments this
     * will be ignored.
     *
     * @param argument the arguments the user passed one string.
     */
    void setArguments(String argument) {
    }
}
