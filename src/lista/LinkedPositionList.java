package lista;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionList<E> implements PositionList<E> {

    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E e, Node<E> next) {
            this.prev = prev;
            element = e;
            this.next = next;
        }

        @Override
        public E element() throws IllegalStateException {
            if ((prev == null) || (next == null)) {
                throw new IllegalStateException("Esta posição não é mais válida.");
            }
            return element;
        }
    }

    // Nós sentinelas
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedPositionList() {
        this.head = new Node<>(null, null, null);
        this.tail = new Node<>(head, null, null);
        this.head.next = tail;
        this.size = 0;
    }

    /**
     * @param p the position to be validated
     * @return the node corresponding to the given position
     * @throws IllegalArgumentException if the position is invalid
     */
    private Node<E> checkPosition(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) { // Cast de Position para Node
            throw new IllegalArgumentException("Posição inválida.");
        }

        if (node == head || node == tail) {
            throw new IllegalArgumentException("The head/tail node isn't a valid position.");
        }
        if ((node.prev == null) || (node.next == null)) {
            throw new IllegalArgumentException("Position doesn't belong t a valid NodeList.");
        }

        return node;
    }

    @Override
    public boolean isFirst(Position<E> p) {
        return checkPosition(p) == head.next;
    }

    @Override
    public boolean isLast(Position<E> p) {
        return checkPosition(p) == tail.prev;
    }

    @Override
    public Position<E> first() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista is empty.");
        }
        return head.next;
    }

    @Override
    public Position<E> last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista is empty.");
        }
        return tail.prev;
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        Node<E> prevNode = node.prev;
        if (prevNode == head) {
            return null; // Initial
        }
        return prevNode;
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        Node<E> nextNode = node.next;
        if (nextNode == tail) {
            return null; // End
        }
        return nextNode;
    }

    @Override
    public Position<E> insertFirst(E e) {
        Node<E> firstNode = head.next;
        Node<E> newNode = new Node<>(head, e, firstNode);
        head.next = newNode;
        firstNode.prev = newNode;
        size++;
        return newNode;
    }

    @Override
    public Position<E> insertLast(E e) {
        Node<E> lastNode = tail.prev;
        Node<E> newNode = new Node<>(lastNode, e, tail);
        tail.prev = newNode;
        lastNode.next = newNode;
        size++;
        return newNode;
    }

    @Override
    public Position<E> insertAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        Node<E> nextNode = node.next;
        Node<E> newNode = new Node<>(node, e, nextNode);
        node.next = newNode;
        nextNode.prev = newNode;
        size++;
        return newNode;
    }

    @Override
    public Position<E> insertBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        Node<E> prevNode = node.prev;
        Node<E> newNode = new Node<>(prevNode, e, node);
        node.prev = newNode;
        prevNode.next = newNode;
        size++;
        return newNode;
    }

    @Override
    public E replaceElement(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        E oldElement = node.element;
        node.element = e;
        return oldElement;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) throws IllegalArgumentException {
        Node<E> node1 = checkPosition(p1);
        Node<E> node2 = checkPosition(p2);
        E temp = node1.element;
        node1.element = node2.element;
        node2.element = temp;
    }

    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        E removed = node.element;

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = null;
        node.next = null;
        node.element = null;

        size--;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // ITERATORS

    /**
     * Returns an iterator over the elements stored in this list.
     *
     * <p>This allows the list to be traversed using the enhanced
     * {@code for-each} loop.
     *
     * @return an iterator over the list elements
     */
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator(); // Allows: for(E element : lista)
    }

    @Override
    public Iterable<Position<E>> positions() {
        return PositionIterator::new; // Allows: for(Position<E> p : lista.positions())
    }

    // --- ITERATOR CLASSES ---

    /**
     * Iterator implementation for traversing the positions of the list.
     *
     * <p>The iterator starts at the first position and advances sequentially
     * through the list until no more positions are available.
     */
    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = isEmpty() ? null : first(); // Starts with the first

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Position<E> next() {
            if (cursor == null) throw new NoSuchElementException();
            Position<E> current = cursor;
            cursor = after(cursor); // Advance the cursor
            return current;
        }
    }


    /**
     * Iterator implementation for traversing the elements of the list.
     *
     * <p>This iterator internally uses a {@code PositionIterator}
     * to iterate through the positions and retrieve their elements.
     */
    private class ElementIterator implements Iterator<E> {
        private final Iterator<Position<E>> posIterator = new PositionIterator();

        @Override
        public boolean hasNext() {
            return posIterator.hasNext();
        }

        @Override
        public E next() {
            return posIterator.next().element();
        }
    }
}