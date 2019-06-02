package edu.kit.informatik.game;

import edu.kit.informatik.util.Tuple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the playing field and provides methods to perform operations on it.
 *
 * @author ████████████
 * @version 1.0
 */
class Board {

    /**
     * Rows
     */
    private static final int WIDTH = 15;
    /**
     * Columns
     */
    private static final int HEIGHT = 11;
    private final int[][] board = new int[HEIGHT][WIDTH];

    /**
     * Handy method to check if multiple points are unoccupied.
     *
     * @param points the positions to check
     * @return true if all are unoccupied if one or more are unoccupied
     * @throws GameLogicException if the points are out of bounds
     * @see Board#isBlocked(Tuple)
     */
    boolean isBlocked(final List<Tuple<Integer>> points) throws GameLogicException {
        for (Tuple<Integer> point : points) {
            if (isBlocked(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a point is unoccupied.
     *
     * @param point the position to check
     * @return true if occupied false if not
     * @throws GameLogicException if the position is out of bounds
     */
    boolean isBlocked(final Tuple<Integer> point) throws GameLogicException {
        if (!inBounds(point)) {
            throw new GameLogicException(ErrorMessages.BOUNDS.toString());
        }
        return uncheckedIsBlocked(point);
    }

    /**
     * Make sure you check if the position is in bounds e.g with {@link this#inBounds(Tuple)} before
     * using this method.
     * It exists to avoid having to check for exceptions in algorithms that them self use
     * {@link this#inBounds(Tuple)}.
     *
     * @param point the position to check for.
     * @return true if field at point is empty false otherwise
     * @see this#inBounds(Tuple)
     */
    private boolean uncheckedIsBlocked(final Tuple<Integer> point) {
        return board[point.getFistComponent()][point.getSecondComponent()] != Token.EMPTY.getNumber();
    }

    private boolean inBounds(final Tuple<Integer> toCheck) {
        return toCheck.getFistComponent() >= 0 && toCheck.getSecondComponent() >= 0
                && toCheck.getFistComponent() < HEIGHT && toCheck.getSecondComponent() < WIDTH;
    }

    /**
     * Sets a token at a position.
     *
     * @param point position to set the token at
     * @param token the kind of token
     * @throws GameLogicException if the specified position is out of bounds.
     */
    void set(final Tuple<Integer> point, final Token token) throws GameLogicException {
        // in practice, in the current state of the program this should never evaluate as true,
        // since other methods already check for in bounds,
        // but a set method without such a check would be unintuitive.
        if (!inBounds(point)) {
            throw new GameLogicException(ErrorMessages.BOUNDS.toString());
        }
        board[point.getFistComponent()][point.getSecondComponent()] = token.getNumber();
    }

    /**
     * Get the state of a field.
     *
     * @param point the position you are interested in.
     * @return the token at the specified position.
     * @throws GameLogicException the point was not in bounds.
     */
    Token get(final Tuple<Integer> point) throws GameLogicException {
        if (!inBounds(point)) {
            throw new GameLogicException(ErrorMessages.BOUNDS.toString());
        }
        final int tokenNumber = board[point.getFistComponent()][point.getSecondComponent()];
        for (Token token : Token.values()) {
            if (token.getNumber() == tokenNumber) {
                return token;
            }
        }
        throw new AssertionError("This is a bug and should never happen.");
    }

    /**
     * Get the first position of a token. Only call this method if you know the token is present.
     *
     * @param token Token to search for.
     * @return position of the first occurrence.
     */
    Tuple<Integer> getFirst(final Token token) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == token.getNumber()) {
                    return new Tuple<>(row, column);
                }
            }
        }
        throw new AssertionError("This method shouldn't be called, if the token isn't present.");
    }

    /**
     * Calculates the empty fields in the "reachable" area around an orb.
     *
     * @param token an orb
     * @return number of free fields around this token
     */
    int getFreeArea(final Token token) {
        final Tuple<Integer> start = getFirst(token);
        final HashMap<Tuple<Integer>, Integer> all = new HashMap<>();
        all.put(start, 0);
        int level = 0;
        int oldSize;
        do {
            List<Tuple<Integer>> toAdd = new LinkedList<>();
            oldSize = all.size();
            for (Tuple<Integer> middle : all.keySet()) {
                // To don't look at the point already looked at.
                if (all.get(middle).equals(level)) {
                    toAdd.addAll(getDirectSurrounding(middle));
                }
            }
            for (Tuple<Integer> add : toAdd) {
                all.put(add, level + 1);
            }
            level++;
        } while (oldSize != all.size());
        // -1 because "start" is in this map and should not be counted.
        return all.size() - 1;
    }

    /**
     * Get the free number of free fields in the direct neighbourhood of the middle field.
     * With "direct neighbourhood" meaning the cardinal directions.
     *
     * @param middle the position to look at
     * @return the number of free fields around the middle
     */
    List<Tuple<Integer>> getDirectSurrounding(final Tuple<Integer> middle) {
        final List<Tuple<Integer>> directions = new LinkedList<>();
        final List<Tuple<Integer>> validDirections = new LinkedList<>();
        // up, down, left, right
        directions.add(new Tuple<>(middle.getFistComponent() - 1, middle.getSecondComponent()));
        directions.add(new Tuple<>(middle.getFistComponent() + 1, middle.getSecondComponent()));
        directions.add(new Tuple<>(middle.getFistComponent(), middle.getSecondComponent() - 1));
        directions.add(new Tuple<>(middle.getFistComponent(), middle.getSecondComponent() + 1));
        for (Tuple<Integer> direction : directions) {
            // short circuit evaluation makes sure uncheckIsBlocked is never called with an
            // invalid value.
            if (inBounds(direction) && !uncheckedIsBlocked(direction)) {
                validDirections.add(direction);
            }
        }
        return validDirections;
    }

    /**
     * @return Lines representing rows. With the numbers for the tokens defined in {@link Token}.
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int[] rows : board) {
            // I know that the ReadWrite class accepts all kinds of line breaks, but I think it's
            // generally better style to use System.lineSeparator().
            stringBuilder.append(System.lineSeparator());
            for (int field : rows) {
                stringBuilder.append(field);
            }
        }
        // Remove extra new Line at the end.
        return stringBuilder.toString().replaceFirst(System.lineSeparator(), "");
    }
}
