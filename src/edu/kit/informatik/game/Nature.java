package edu.kit.informatik.game;

import edu.kit.informatik.util.Tuple;

import java.util.LinkedList;
import java.util.List;


/**
 * This class performs the moves of the Player Nature. It does not check for game state. E.g if
 * the player is allowed to make a move at this point of the game.
 *
 * @author ████████████
 * @version 1.0
 */
class Nature {

    private final Board board;

    /**
     * Constructs a new player of type nature.
     *
     * @param board the board to operate on.
     */
    Nature(final Board board) {
        this.board = board;
    }

    /**
     * Initially set Vesta or Ceres at a given position.
     *
     * @param position the position to set the <code>token</code> at.
     * @param token    Vesta or Ceres (unchecked)
     * @throws GameLogicException if position is out of bounds or if the field is already
     *                            occupied.
     */
    void set(final Tuple<Integer> position, final Token token) throws GameLogicException {
        if (board.isBlocked(position)) {
            throw new GameLogicException(ErrorMessages.OCCUPIED.toString());
        }
        board.set(position, token);
    }

    /**
     * Move an orb along a predefined path.
     *
     * @param moves  a list of consecutive points on the board.
     * @param placed the length of the last placed token.
     * @param token  right orb depending on the phase.
     * @throws GameLogicException if the list of moves is invalid.
     */
    void move(final List<Tuple<Integer>> moves, final int placed, final Token token) throws GameLogicException {
        if (moves.isEmpty() || moves.size() > placed) {
            throw new GameLogicException(ErrorMessages.MOVES.toString());
        }
        Tuple<Integer> currentPosition = board.getFirst(token);
        if (!checkConsecutive(moves, currentPosition)) {
            throw new GameLogicException(ErrorMessages.CONSECUTIVE.toString());
        }
        LinkedList<Tuple<Integer>> movesWithoutBack = new LinkedList<>(moves);
        movesWithoutBack.remove(currentPosition);
        if (board.isBlocked(movesWithoutBack)) {
            throw new GameLogicException(ErrorMessages.OCCUPIED.toString());
        }
        Tuple<Integer> newPosition = moves.get(moves.size() - 1);
        if (!newPosition.equals(currentPosition)) {
            // set new position
            board.set(moves.get(moves.size() - 1), token);
            // remove last position
            board.set(currentPosition, Token.EMPTY);
        }
    }

    /**
     * Makes sure nature does not "jump" fields
     *
     * @param moves The steps to move.
     * @param first The previous position of Vesta or Ceres.
     * @return If a filed was "jumped".
     */
    private boolean checkConsecutive(final List<Tuple<Integer>> moves, final Tuple<Integer> first) {
        // Points are only allowed to differ in one component by one.
        Tuple<Integer> current = first;
        for (Tuple<Integer> move : moves) {
            if (Math.abs(move.getFistComponent() - current.getFistComponent())
                    + Math.abs(move.getSecondComponent() - current.getSecondComponent()) != 1) {
                return false;
            }
            current = move;
        }
        return true;
    }

}
