package arvore;

import java.util.Iterator;

public interface Tree<E> extends Iterable<E>, Iterator<E> {

    // ========== MÉTODOS GENÉRICOS ==========

    /** Returns the number of positions stored in the tree. */
    int size();

    /**
     * Returns the height of the tree.
     * <p>The height of a tree is the maximum depth of any position.
     */
    int height();

    /** Checks whether the tree is empty. */
    boolean isEmpty();

    /** Returns an iterator over the elements stored in the tree. */
    Iterator<E> iterator();

    /** Returns an iterable collection of all positions in the tree. */
    Iterable<Position<E>> positions();

    // ========== MÉTODOS DE ACESSO ==========

    /** Returns the root position of the tree. */
    Position<E> root() throws IllegalStateException;

    /** Returns the parent of a given position. */
    Position<E> parent(Position<E> v) throws IllegalArgumentException, IllegalStateException;

    /** Returns an iterable collection containing the children of a given position. */
    Iterable<Position<E>> children(Position<E> v) throws IllegalArgumentException;

    // ========== MÉTODOS DE CONSULTA ==========

    /**
     * Checks whether a position is internal.
     * <p>An internal position has at least one child.
     */
    boolean isInternal(Position<E> v) throws IllegalArgumentException;

    /**
     * Checks whether a position is external (a leaf).
     * <p>An external position has no children.
     */
    boolean isExternal(Position<E> v) throws IllegalArgumentException;

    /** Checks whether a position is the root of the tree. */
    boolean isRoot(Position<E> v) throws IllegalArgumentException;

    // ========== MÉTODOS DE ATUALIZAÇÃO ==========

    /** Replaces the element stored at a given position with a new element. */
    E replace(Position<E> v, E e) throws IllegalArgumentException;
}
