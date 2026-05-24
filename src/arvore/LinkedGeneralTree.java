package arvore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedGeneralTree<E> implements Tree<E> {
    private Node<E> root;
    private int size;

    public LinkedGeneralTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return null;
    }

    @Override
    public Position<E> root() throws IllegalStateException {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> v) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> v) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean isExternal(Position<E> v) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) throws IllegalArgumentException {
        return false;
    }

    @Override
    public E replace(Position<E> v, E e) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }

    private static class Node<E> implements Position<E>{
        private E element;
        private Node<E> parent;
        private List<E> children;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public List<E> getChildren() {
            return children;
        }

        public void setChildren(List<E> children) {
            this.children = children;
        }
    }
}
