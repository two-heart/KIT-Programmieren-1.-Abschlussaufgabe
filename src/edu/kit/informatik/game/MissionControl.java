package edu.kit.informatik.game;

import edu.kit.informatik.util.Tuple;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The player Mission control with their operation {@link this#place(Tuple, Tuple, int)}.
 *
 * @author ████████████
 * @version 1.0
 */
class MissionControl {
    private static final int DAWN = 7;
    private static final int SHORTEST_TOKEN = 2;
    private final Board board;
    /**
     * This boolean array has length 8 since the player has tokens with length 2-7 so 0,1 are
     * are always false and this class then saves the remaining tokens at the index that is
     * equals the length of the token.
     */
    private final boolean[] tokens = new boolean[DAWN + 1];
    private int usedLength;

    /**
     * Constructs a new mission control player with a full set of tokens.
     *
     * @param board the board to set the tokens on.
     */
    MissionControl(final Board board) {
        this.board = board;
        newTokenSet();
    }

    /**
     * Gives this player a new set of tokens. I.e. tokens with the length 2 to 7. One each.
     */
    void newTokenSet() {
        for (int tokenLength = SHORTEST_TOKEN; tokenLength < tokens.length; tokenLength++) {
            tokens[tokenLength] = true;
        }
    }

    /**
     * Places a token on the board.
     *
     * @param from  one end of the token.
     * @param to    other end of the token.
     * @param diced number between 2-7 that was diced.
     * @throws GameLogicException if an attempt was made to place the token at an illegal
     *                            position. E.g out of bounds or occupied.
     */
    void place(final Tuple<Integer> from, final Tuple<Integer> to, final int diced) throws GameLogicException {
        final int length = length(from, to);
        if (!isLengthValid(length, diced)) {
            throw new GameLogicException(ErrorMessages.LENGTH.toString());
        }
        final List<Tuple<Integer>> points = pointsBetween(from, to);
        if (length != DAWN) {
            if (board.isBlocked(points)) {
                throw new GameLogicException(ErrorMessages.OCCUPIED.toString());
            } else {
                for (Tuple<Integer> point : points) {
                    board.set(point, Token.MISSION_CONTROL);
                }
            }
        } else {
            // Iterator to avoid ConcurrentModificationException.
            final Iterator<Tuple<Integer>> iterator = points.iterator();
            // Remove all tokens that are not on the field.
            while (iterator.hasNext()) {
                final Tuple<Integer> point = iterator.next();
                final boolean isBlocked;
                try {
                    isBlocked = !board.isBlocked(point);
                } catch (GameLogicException e) {
                    iterator.remove();
                    continue;
                }
                if (!isBlocked) {
                    throw new GameLogicException(ErrorMessages.OCCUPIED.toString());
                }
            }
            // Check if every point is out of bounds.
            if (points.isEmpty()) {
                throw new GameLogicException(ErrorMessages.BOUNDS.toString());
            }
            for (Tuple<Integer> point : points) {
                board.set(point, Token.MISSION_CONTROL);
            }
        }
        // the token was successfully placed so it can be taken away from the player
        tokens[length] = false;
        usedLength = length;
    }

    /**
     * Calculates the length of a token given the its start and end position.
     * One may swap <code>from</code> and <code>to</code> and the result will be the same.
     *
     * @param from start point.
     * @param to   end point.
     * @return the length of a token.
     * @throws GameLogicException if from and to are not on one line.
     */
    private int length(final Tuple<Integer> from, final Tuple<Integer> to) throws GameLogicException {
        if (from.getFistComponent().equals(to.getFistComponent())) {
            return Math.abs(from.getSecondComponent() - to.getSecondComponent()) + 1;
        }
        if (from.getSecondComponent().equals(to.getSecondComponent())) {
            return Math.abs(from.getFistComponent() - to.getFistComponent()) + 1;
        }
        throw new GameLogicException(ErrorMessages.SINGLE_LINE.toString());
    }

    /**
     * Get the points horizontally or vertically between two given points including the given points.
     *
     * @param from start point
     * @param to   end point
     * @return straight path between <code>from</code> and <code>to</code>. (Including
     * <code>from</code> and <code>to</code>.
     */
    private List<Tuple<Integer>> pointsBetween(final Tuple<Integer> from, final Tuple<Integer> to) {
        LinkedList<Tuple<Integer>> toReturn = new LinkedList<>();
        final int start;
        final int stop;
        if (from.getFistComponent().equals(to.getFistComponent())) {
            // row is the same, so column will be altered
            start = Math.min(from.getSecondComponent(), to.getSecondComponent());
            stop = Math.max(from.getSecondComponent(), to.getSecondComponent());
            for (int columun = start; columun <= stop; columun++) {
                toReturn.add(new Tuple<>(from.getFistComponent(), columun));
            }
        } else {
            // column is the same, so row will be altered
            start = Math.min(from.getFistComponent(), to.getFistComponent());
            stop = Math.max(from.getFistComponent(), to.getFistComponent());
            for (int row = start; row <= stop; row++) {
                toReturn.add(new Tuple<>(row, from.getSecondComponent()));
            }
        }
        return toReturn;
    }

    private boolean isLengthValid(final int length, final int diced) {
        // check if length exists
        if (length >= tokens.length || !tokens[length]) {
            return false;
        }
        // decide whether to decrement or increment in the loop.
        int direction = diced < length ? 1 : -1;
        // the index i of this loop is the token length that is going to be checked
        for (int i = diced; i < tokens.length && i >= SHORTEST_TOKEN; i = i + direction) {
            if (i == length && tokens[i]) {
                return true;
            }
            if (tokens[i]) {
                return false;
            }
        }
        return false;
    }

    /**
     * Obtain the length of the most recently placed token.
     * Only call this method after a token was placed.
     *
     * @return the length of the last placed token.
     */
    int getUsedLength() {
        return usedLength;
    }
}
