package arvore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class LinkedGenericTree<E> implements Tree<E> {
    private Node<E> root;
    private int size;

    public LinkedGenericTree() {
        this.root = null;
        this.size = 0;
    }

    public LinkedGenericTree(E e) {
        addRoot(e);
    }

    public Position<E> addRoot(E e) {
        if (!isEmpty()) {
            throw new IllegalStateException("Root already exists");
        }
        this.root = new Node<>(e, null);
        this.size = 1;
        return this.root;
    }

    public Position<E> addChild(Position<E> v, E e) {
        Node<E> node = checkPosition(v);
        Node<E> newNode = new Node<>(e, node);
        node.addChild(newNode);
        this.size++;
        return newNode;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
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
        return node.getParent();
    }

    // O(n)
    @Override
    public Iterable<Position<E>> children(Position<E> v) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);

        // Cria uma lista vazia usando a interface pública Position
        List<Position<E>> snapshot = new ArrayList<>(node.childrenNumber());

        // Copia os ponteiros (Não copia os dados, apenas as referências, então é rápido)
        for (Node<E> child : node.getChildren()) {
            snapshot.add(child);
        }

        // Retorna a lista, pois List implementa Iterable
        return snapshot;
    }

    @Override
    public boolean isInternal(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).childrenNumber() > 0;
    }

    @Override
    public boolean isExternal(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v).childrenNumber() == 0;
    }

    @Override
    public boolean isRoot(Position<E> v) throws IllegalArgumentException {
        return checkPosition(v) == root;
    }

    @Override
    public E replace(Position<E> v, E e) throws IllegalArgumentException {
        Node<E> node = checkPosition(v);
        E replaced = node.getElement();
        node.setElement(e);
        return replaced;
    }

    public int depth(Position<E> v) throws IllegalArgumentException {
        return depthRecursive(checkPosition(v));
    }

    private int depthRecursive(Node<E> v) {
        if (v == root) {
            return 0;
        } else {
            return 1 + depth(v.getParent());
        }
    }

    /**
     * Returns the total height of the tree.
     */
    public int height() {
        if (isEmpty()) {
            return 0;
        }
        return heightRecursive(root);
    }

    public int height(Position<E> v) throws IllegalArgumentException {
        return heightRecursive(checkPosition(v));
    }

    private int heightRecursive(Node<E> v) {
        if (v.childrenNumber() == 0) {
            return 0;
        }
        int h = 0;
        for (Node<E> child : v.getChildren()) {
            h = Math.max(h, heightRecursive(child));
        }
        return 1 + h;
    }

    public void preOrder(Position<E> v, Consumer<Position<E>> visitor) {
        Node<E> node = checkPosition(v);
        preOrderConsumer(node, visitor);
    }

    private void preOrderConsumer(Node<E> v, Consumer<Position<E>> visitor) {
        visitor.accept(v);

        for (Node<E> w : v.getChildren()) {
            preOrderConsumer(w, visitor);
        }
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

        for (Node<E> w : v.getChildren()) {
            preOrderSubtree(w, snapshot);
        }
    }

    public void postOrder(Position<E> v, Consumer<Position<E>> visitor) {
        Node<E> node = checkPosition(v);
        postOrderConsumer(node, visitor);
    }

    private void postOrderConsumer(Node<E> v, Consumer<Position<E>> visitor) {
        for (Node<E> w : v.getChildren()) {
            postOrderConsumer(w, visitor);
        }

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
        for (Node<E> w : v.getChildren()) {
            postOrderSubtree(w, snapshot);
        }

        snapshot.add(v);
    }

    /**
     * @param p the position to be validated
     * @return the node corresponding to the given position
     * @throws IllegalArgumentException if the position is invalid
     */
    private Node<E> checkPosition(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) { // Cast de Position para Node
            throw new IllegalArgumentException("Invalid position.");
        }

        if (node.parent == node) {
            throw new IllegalArgumentException("Position doesn't belong to a valid Node.");
        }

        return node;
    }

    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private List<Node<E>> children;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        @Override
        public E getElement() throws IllegalStateException {
            if (parent == this) {
                throw new IllegalStateException("This position is no longer valid.");
            }
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Iterable<Node<E>> getChildren() {
            return children;
        }

        public void addChild(Node<E> v) {
            children.add(v);
        }

        public void removeChild(Node<E> v) {
            children.remove(v);
        }

        public int childrenNumber() {
            return children.size();
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    private class ElementIterator implements Iterator<E> {
        // Usa o positions() que acabamos de criar por baixo dos panos
        private final Iterator<Position<E>> posIterator = positions().iterator();

        @Override
        public boolean hasNext() {
            return posIterator.hasNext();
        }

        @Override
        public E next() {
            return posIterator.next().getElement(); // Extrai apenas o valor (E) da Position
        }
    }

}
