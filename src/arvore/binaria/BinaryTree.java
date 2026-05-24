package arvore.binaria;

import arvore.Position;
import arvore.Tree;

public interface BinaryTree<E> extends Tree<E> {

    /** Returns the left child of a given position. */
    Position<E> left(Position<E> v) throws IllegalArgumentException, IllegalStateException;

    /** Returns the right child of a given position. */
    Position<E> right(Position<E> v) throws IllegalArgumentException, IllegalStateException;

    /** Checks whether the tree has a left child. */
    boolean hasLeft(Position<E> v) throws IllegalArgumentException;

    /** Checks whether the tree has a right child. */
    boolean hasRight(Position<E> v) throws IllegalArgumentException;
}
