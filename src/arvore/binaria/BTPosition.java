package arvore.binaria;

import arvore.Position;

public interface BTPosition<E> extends Position<E> {
    BTPosition<E> getLeft();

    BTPosition<E> getParent();

    BTPosition<E> getRight();

    void setLeft(BTPosition<E> v);

    void setParent(BTPosition<E> v);

    void setRight(BTPosition<E> v);

    void setElement(E o);
}
