package lista;

import base.Position;

import java.util.NoSuchElementException;

public interface PositionList<E> extends Iterable<E> {
    boolean isFirst(Position<E> p);

    boolean isLast(Position<E> p);

    Position<E> first() throws NoSuchElementException;

    Position<E> last() throws NoSuchElementException;

    Position<E> before(Position<E> p) throws IllegalArgumentException;

    Position<E> after(Position<E> p) throws IllegalArgumentException;

    Position<E> insertFirst(E e);

    Position<E> insertLast(E e);

    Position<E> insertAfter(Position<E> p, E e) throws IllegalArgumentException;

    Position<E> insertBefore(Position<E> p, E e) throws IllegalArgumentException;

    E replaceElement(Position<E> p, E e) throws IllegalArgumentException;

    void swapElements(Position<E> p1, Position<E> p2) throws IllegalArgumentException;

    E remove(Position<E> p) throws IllegalArgumentException;

    int size();

    boolean isEmpty();

    // Como estendemos Iterable, o método iterator() já é exigido implicitamente.
    // Mas, como estamos em uma Lista Posicional, é uma ótima prática
    // EXIGIR também o iterador de posições na interface:

    /**
     * Returns an iterable collection of all positions in the list.
     *
     * @return an iterable of positions
     */
    Iterable<Position<E>> positions();
}