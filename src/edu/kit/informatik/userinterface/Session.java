package edu.kit.informatik.userinterface;

import edu.kit.informatik.ReadWrite;
import edu.kit.informatik.game.Game;
import edu.kit.informatik.userinterface.commands.Command;
import edu.kit.informatik.userinterface.commands.CommandFactory;

/**
 * Handling of user input and output. This is also the point where the exception handling happens.
 * A session can be started and stopped.
 *
 * @author ████████████
 * @version 1.0
 */
public class Session {

    private boolean running = true;
    private Game game;

    /**
     * After starting the session this method remains in a loop until the {@link this#terminate()}
     * method is called.
     */
    void run() {
        game = new Game();
        CommandFactory factory = new CommandFactory(this);
        while (running) {
            String input = ReadWrite.readLine();
            try {
                Command command = factory.getCommand(input);
                command.execute();
            } catch (InputException e) {
                ReadWrite.writeError(e.getMessage());
            }
        }
    }

    /**
     * Terminates the session.
     */
    public void terminate() {
        running = false;
    }

    /**
     * Restarts the game, but not the program.
     */
    public void reset() {
        game = new Game();
    }

    /**
     * @return the current game that the user "plays with".
     */
    public Game getGame() {
        return game;
    }
}
