package arvore.binaria;

import arvore.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
    private Node<E> root;
    private int size;

    public LinkedBinaryTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Position<E> left(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).left; // It can return null if it doesn't exist.
    }

    @Override
    public Position<E> right(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).right; // It can return null if it doesn't exist.
    }

    @Override
    public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
        Node<E> node = checkPosition(p);
        Node<E> parent = node.parent;

        // Root dont have brother
        if (parent == null) {
            return null;
        }

        // It can return null if it doesn't exist.
        if (node == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    @Override
    public boolean hasLeft(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).left != null;
    }

    @Override
    public boolean hasRight(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).right != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        return preOrderIterable();
    }

    @Override
    public Position<E> root() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws IllegalArgumentException, IllegalStateException {
        Node<E> node = checkPosition(v);
        if (node == root) {
            throw new IllegalStateException("Root must not have parent");
        }
        return node.parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) throws IllegalArgumentException {
        List<Position<E>> snapshot = new ArrayList<>(2);

        if (hasLeft(v)) {
            snapshot.add(left(v));
        }
        if (hasRight(v)) {
            snapshot.add(right(v));
        }

        return snapshot;
    }

    @Override
    public boolean isInternal(Position<E> v) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        return node.left != null || node.right != null;
    }

    @Override
    public boolean isExternal(Position<E> v) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        return node.left == null && node.right == null;
    }

    @Override
    public boolean isRoot(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v) == root;
    }

    @Override
    public E replace(Position<E> v, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        E replaced = node.element;
        node.element = e;
        return replaced;
    }

    public Position<E> addRoot(E e) {
        if (!isEmpty()) {
            throw new IllegalStateException("Root already exists");
        }
        this.root = new Node<>(e, null, null, null);
        this.size = 1;
        return this.root;
    }

    public Position<E> addLeft(Position<E> v, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        if (node.left != null) {
            throw new IllegalArgumentException("This position already has a child on the left.");
        }
        Node<E> newNode = new Node<>(e, node, null, null);
        node.left = newNode;
        this.size++;
        return newNode;
    }

    public Position<E> addRight(Position<E> v, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        if (node.right != null) {
            throw new IllegalArgumentException("This position already has a child on the right.");
        }
        Node<E> newNode = new Node<>(e, node, null, null);
        node.right = newNode;
        this.size++;
        return newNode;
    }

    public E remove(Position<E> v) {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Tree");
        }

        Node<E> node = checkPosition(v);
        if (node.left != null && node.right != null) {
            throw new IllegalArgumentException("It is impossible to remove a position with two children.");
        }

        E removed = node.element;

        Node<E> child = (node.left != null) ? node.left : node.right;

        if (node == root) {
            root = child;
            if (child != null) {
                child.parent = null;
            }
        } else {
            Node<E> parent = node.parent;

            if (node == parent.left) {
                parent.left = child;
            } else {
                parent.right = child;
            }

            if (child != null) {
                child.parent = parent;
            }
        }

        size--;

        // It helps the Garbage Collector by deleting old data.
        node.element = null;
        node.left = null;
        node.right = null;
        // important for validating checkPosition()
        node.parent = node;

        return removed;
    }

    public int depth(Position<E> v) throws IllegalArgumentException {
        return depthNode(checkPosition(v));
    }

    private int depthNode(Node<E> v) {
        if (v == root) {
            return 0;
        } else {
            return 1 + depth(v.parent);
        }
    }

    /**
     * Returns the total height of the tree.
     */
    public int height() {
        if (isEmpty()) {
            return 0;
        }
        return heightNode(root);
    }

    public int height(Position<E> v) throws IllegalArgumentException {
        return heightNode(checkPosition(v));
    }

    private int heightNode(Node<E> v) {
        int h = 0;
        if (v.left != null) h = Math.max(h, 1 + heightNode(v.left));
        if (v.right != null) h = Math.max(h, 1 + heightNode(v.right));
        return h;
    }

    public void preOrder(Position<E> v, Consumer<Position<E>> visitor) {
        Node<E> node = checkPosition(v);
        preOrderConsumer(node, visitor);
    }

    private void preOrderConsumer(Node<E> v, Consumer<Position<E>> visitor) {
        visitor.accept(v);
        if (v.left != null) preOrderConsumer(v.left, visitor);
        if (v.right != null) preOrderConsumer(v.right, visitor);
    }

    public Iterable<Position<E>> preOrderIterable() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preOrderSubtree((Node<E>) root(), snapshot);
        }
        return snapshot;
    }

    private void preOrderSubtree(Node<E> v, List<Position<E>> snapshot) {
        snapshot.add(v);
        if (v.left != null) preOrderSubtree(v.left, snapshot);
        if (v.right != null) preOrderSubtree(v.right, snapshot);
    }

    public void postOrder(Position<E> v, Consumer<Position<E>> visitor) {
        Node<E> node = checkPosition(v);
        postOrderConsumer(node, visitor);
    }

    private void postOrderConsumer(Node<E> v, Consumer<Position<E>> visitor) {
        if (v.left != null) postOrderConsumer(v.left, visitor);
        if (v.right != null) postOrderConsumer(v.right, visitor);
        visitor.accept(v);
    }

    public Iterable<Position<E>> postOrderIterable() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            postOrderSubtree((Node<E>) root(), snapshot);
        }
        return snapshot;
    }

    private void postOrderSubtree(Node<E> v, List<Position<E>> snapshot) {
        if (v.left != null) postOrderSubtree(v.left, snapshot);
        if (v.right != null) postOrderSubtree(v.right, snapshot);
        snapshot.add(v);
    }

    public void inOrder(Position<E> v, Consumer<Position<E>> visitor) {
        Node<E> node = checkPosition(v);
        inOrderConsumer(node, visitor);
    }

    private void inOrderConsumer(Node<E> v, Consumer<Position<E>> visitor) {
        if (v.left != null) inOrderConsumer(v.left, visitor);
        visitor.accept(v);
        if (v.right != null) inOrderConsumer(v.right, visitor);
    }


    public Iterable<Position<E>> inOrderIterable() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            inOrderSubtree((Node<E>) root(), snapshot);
        }
        return snapshot;
    }

    private void inOrderSubtree(Node<E> v, List<Position<E>> snapshot) {
        if (v.left != null) inOrderSubtree(v.left, snapshot);
        snapshot.add(v);
        if (v.right != null) inOrderSubtree(v.right, snapshot);
    }


    private Node<E> checkPosition(Position<E> v) throws IllegalArgumentException {
        if (!(v instanceof Node<E> node)) { // Cast Position -> Node
            throw new IllegalArgumentException("Not a valid position.");
        }

        if (node.parent == node) {
            throw new IllegalArgumentException("Position has been deleted.");
        }

        return node;
    }

    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public E element() throws IllegalStateException {
            if (parent == this) throw new IllegalStateException("Position invalid.");
            return element;
        }
    }

    private class ElementIterator implements Iterator<E> {
        // Use the positions() function
        private final Iterator<Position<E>> posIterator = positions().iterator();

        @Override
        public boolean hasNext() {
            return posIterator.hasNext();
        }

        @Override
        public E next() {
            return posIterator.next().element(); // Extract only the value (E) from the Position
        }
    }
}