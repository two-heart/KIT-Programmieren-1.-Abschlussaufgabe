package edu.kit.informatik.util;

import java.util.Objects;

/**
 * A imputable tuple in which both values are of the same type.
 *
 * @param <T> Tuple type
 * @author ████████████
 * @version 1.0
 */
// while planing for this task I thought I would need a tuple of more types…
public class Tuple<T> {

    private final T fistComponent;
    private final T secondComponent;

    /**
     * Constructs a new imputable tuple.
     *
     * @param firstComponent  x1 if the tuple is of the form (x1,x2)
     * @param secondComponent x2 if the tuple is of the form (x1,x2)
     */
    public Tuple(T firstComponent, T secondComponent) {
        this.fistComponent = firstComponent;
        this.secondComponent = secondComponent;
    }

    /**
     * @param toCheck another object of this class.
     * @return true if both components are equal
     */
    @Override
    public boolean equals(final Object toCheck) {
        if (!toCheck.getClass().equals(Tuple.class)) {
            return false;
        }
        Tuple other = (Tuple) toCheck;
        return (getFistComponent().equals(other.getFistComponent())
                && getSecondComponent().equals(other.getSecondComponent()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fistComponent, secondComponent);
    }

    /**
     * @return String of the format (firstComponent,secondComponent)
     */
    @Override
    public String toString() {
        return String.format("(%s,%s)", fistComponent.toString(), secondComponent.toString());
    }

    /**
     * x_1 if the tuple is of the form (x_1,x_2)
     *
     * @return the value in this component.
     */
    public T getFistComponent() {
        return fistComponent;
    }

    /**
     * x_2 if the tuple is of the form (x_1,x_2)
     *
     * @return the value in this component.
     */
    public T getSecondComponent() {
        return secondComponent;
    }
}
