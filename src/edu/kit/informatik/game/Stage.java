package edu.kit.informatik.game;

/**
 * Saves the point of the game, which consists of the phase and the expected next action.
 * This class provides a method to proceed to the next point of the game.
 *
 * @author ████████████
 * @version 1.0
 */
class Stage {
    private static final int MOVES_PER_PHASE = 6;

    private boolean phase1 = true;
    private boolean over = false;
    private boolean natureInit = true;
    private boolean natureMove = false;
    private boolean roll = false;
    private boolean place = false;
    private int moves = 0;

    /**
     * Call this method after an successful move, in order to go further in the game.
     */
    // This is basically a state machine. Since there is only one "input symbol" the state
    // machine pattern would be to complex for this simple task.
    void next() {
        if (natureInit) {
            reset();
            roll = true;
            return;
        }
        if (roll) {
            reset();
            place = true;
            return;
        }
        if (place) {
            reset();
            natureMove = true;
            return;
        }
        if (natureMove) {
            reset();
            roll = true;
            moves++;
        }
        if (moves == MOVES_PER_PHASE && phase1) {
            reset();
            phase1 = false;
            natureInit = true;
            moves = 0;
            return;
        }
        if (moves == MOVES_PER_PHASE) {
            over = true;
        }
    }

    /**
     * Sets attributes of a phase to false
     */
    private void reset() {
        natureInit = false;
        natureMove = false;
        roll = false;
        place = false;
    }

    /**
     * @return if the game is in the 1st phase.
     */
    boolean isPhase1() {
        return phase1;
    }

    /**
     * @return if the game has ended.
     */
    boolean isOver() {
        return over;
    }

    /**
     * @return if player nature is expected to set one of their orbs.
     */
    boolean isNatureInit() {
        return natureInit;
    }

    /**
     * @return if player nature is expected to move one of their orbs.
     */
    boolean isNatureMove() {
        return natureMove;
    }

    /**
     * @return if the game expects an result of the dice.
     */
    boolean isRoll() {
        return roll;
    }

    /**
     * @return if mission control is expected to place one of their tokens.
     */
    boolean isPlace() {
        return place;
    }
}
