package edu.kit.informatik.game;

import edu.kit.informatik.util.Tuple;

import java.util.List;

/**
 * The connection between frontend and backend. Use the methods of this class to control the game.
 * If a method is called at a point or with arguments that would violate the rules of the game,
 * methods of this class will throw a {@link GameLogicException}.
 *
 * @author ████████████
 * @version 1.0
 */
public class Game {
    private static final int MIN_DICE_VALUE = 2;
    // At this point 7=DAWN
    private static final int MAX_DICE_VALUE = 7;

    private final Board board = new Board();
    private final Nature playerNature = new Nature(board);
    private final MissionControl missionControl = new MissionControl(board);
    private final Stage stage = new Stage();
    private int diced;
    private int placedLength;

    /**
     * Sets an orb at the start of a phase.
     *
     * @param position point on the board to set the orb at.
     * @throws GameLogicException if the move was not allowed.
     */
    public void setVC(final Tuple<Integer> position) throws GameLogicException {
        if (stage.isOver() || !stage.isNatureInit()) {
            throw new GameLogicException(ErrorMessages.TIME.toString());
        }
        final Token token = getTokenForPhase();
        playerNature.set(position, token);
        nextStage();
    }

    /**
     * Checks a position.
     *
     * @param position the position to look at.
     * @return the token that is at the specified position.
     * While 0 means empty, 1 Vesta, 2 Ceres and 3 Mission Control.
     * @throws GameLogicException if the position is out of bounds.
     */
    public int state(final Tuple<Integer> position) throws GameLogicException {
        return board.get(position).getNumber();
    }

    /**
     * The board as a String.
     *
     * @return the string representation of the board.
     * While 0 means empty, 1 Vesta, 2 Ceres and 3 Mission Control.
     */
    public String getBoardRepresentation() {
        return board.toString();
    }

    /**
     * Tell the game logic the result of the dice.
     *
     * @param diced the number the user diced (between 2-7)
     * @throws GameLogicException if the argument is not between 2 and 7
     */
    public void setDiced(final int diced) throws GameLogicException {
        if (!stage.isRoll() || stage.isOver()) {
            throw new GameLogicException(ErrorMessages.TIME.toString());
        }
        if (diced < MIN_DICE_VALUE || diced > MAX_DICE_VALUE) {
            throw new GameLogicException(ErrorMessages.DICED.toString());
        }
        this.diced = diced;
        nextStage();
    }

    /**
     * Places a token on the game board.
     *
     * @param from one end of the token to place.
     * @param to   other end of the token to place.
     * @throws GameLogicException if an attempt was made to place the token at an illegal
     *                            position. E.g out of bounds, not allowed at this point or occupied.
     */
    public void place(final Tuple<Integer> from, final Tuple<Integer> to) throws GameLogicException {
        if (!stage.isPlace() || stage.isOver()) {
            throw new GameLogicException(ErrorMessages.TIME.toString());
        }
        missionControl.place(from, to, diced);
        placedLength = missionControl.getUsedLength();
        nextStage();
        // is nature able to move? If not go to next state.
        if (board.getDirectSurrounding(board.getFirst(getTokenForPhase())).isEmpty()) {
            nextStage();
        }
    }

    /**
     * Change the position of Vesta or Ceres.
     *
     * @param moves a list of consecutive fields
     * @throws GameLogicException if the fields were not in bounds, not consecutive or already
     *                            occupied. Check the error message for more details.
     */
    public void move(final List<Tuple<Integer>> moves) throws GameLogicException {
        if (!stage.isNatureMove() || stage.isOver()) {
            throw new GameLogicException(ErrorMessages.TIME.toString());
        }
        playerNature.move(moves, placedLength, getTokenForPhase());
        nextStage();
    }

    /**
     * Calculates the result of the game using the formula in the rules.
     *
     * @return the result of the game.
     * @throws GameLogicException if the method was called at the wrong time.
     */
    public int calculateResult() throws GameLogicException {
        if (!stage.isOver()) {
            throw new GameLogicException(ErrorMessages.TIME.toString());
        }
        // E = max{F(C), F(V)} + (max{F(C), F(V)} − min{F(C), F(V)}) with F meaning free around.
        final int freeAroundCeres = board.getFreeArea(Token.CERES);
        final int freeFromVesta = board.getFreeArea(Token.VESTA);
        return Math.max(freeAroundCeres, freeFromVesta) + Math.max(freeAroundCeres, freeFromVesta)
                - Math.min(freeAroundCeres, freeFromVesta);
    }

    private Token getTokenForPhase() {
        return stage.isPhase1() ? Token.VESTA : Token.CERES;
    }

    /**
     * Goes to the next stage.
     * Checks if the stage has changed and gives a new set of tokens to mission control.
     */
    private void nextStage() {
        final boolean previous = stage.isPhase1();
        stage.next();
        if (previous && !stage.isPhase1()) {
            missionControl.newTokenSet();
        }
    }

}
