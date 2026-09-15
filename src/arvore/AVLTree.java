package arvore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AVLTree<E extends Comparable<E>> {
    Node<E> root;
    int size;

    public AVLTree(){
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public Iterable<Node<E>> inOrder() {
        List<Node<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            inOrderConsumer(root, snapshot::add);
        }
        return snapshot;
    }

    private Node<E> treeSearch(Node<E> p, E key) {
        int comp = key.compareTo(p.element);

        if (comp == 0) {
            return p; // Found it
        } else if (comp < 0 && hasLeft(p)) {
            return treeSearch(p.left, key);
        } else if (comp > 0 && hasRight(p)) {
            return treeSearch(p.right, key);
        }

        // Returns where the search stopped (will be the parent of the new node on insertion)
        return p;
    }

    public void insert(E key) {
        if (isEmpty()) {
            addRoot(key);
            return;
        }

        Node<E> p = treeSearch(root, key);
        int comp = key.compareTo(p.element);

        if (comp == 0) {
            return;
        } else if (comp < 0) {
            addLeft(p, key);
        } else {
            addRight(p, key);
        }
    }

    public E remove(E element) {
        if (isEmpty()) {
            return null;
        }

        Node<E> p = treeSearch(root, element);

        // Element does not exist in the tree
        if (element.compareTo(p.element) != 0) {
            return null;
        }

        E removedElement = p.element;

        // The node has 2 children -> Successor
        if (hasLeft(p) && hasRight(p)) {
            // Find the successor
            Node<E> successor = p.right;
            while (hasLeft(successor)) {
                successor = successor.left;
            }

            // Replace the target node's element with the successor's element
            replace(p, successor.element);

            // Move the pointer 'p' to point to the physical successor (this node will be removed)
            p = successor;
        }

        //tree.remove(p);

        return removedElement;
    }

    public Node<E> search(E key) {
        if (isEmpty()) return null;
        Node<E> p = treeSearch(root, key);
        return key.compareTo(p.element) == 0 ? p : null;
    }

    /**
     * Prints the binary search tree structure sideways in the console.
     * The root is displayed on the left, right subtrees above,
     * and left subtrees below.
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Tree is empty");
            return;
        }

        System.out.println("--- Tree structure ---");
        printTreeRecursive(root, 0);
        System.out.println("---------------------------");
    }

    // Reverse In-Order
    private void printTreeRecursive(Node<E> p, int depth) {
        // Traverse the right subtree first (displayed at the top)
        if (hasRight(p)) {
            printTreeRecursive(p.right, depth + 1);
        }

        // Print the current node with indentation based on its depth
        String spaces = "      ".repeat(depth);
        System.out.println(spaces + p.element);

        // Traverse the left subtree (displayed at the bottom)
        if (hasLeft(p)) {
            printTreeRecursive(p.left, depth + 1);
        }
    }

    private static class Node<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private int height;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right, int height) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    // AUXILIAR METHODS

    /*
    
    BINARY TREE

    */

    private Node<E> addRoot(E e) {
        if (!isEmpty()) {
            throw new IllegalStateException("Root already exists");
        }
        this.root = new Node<>(e, null, null, null, 0);
        this.size = 1;
        return this.root;
    }

    private Node<E> addLeft(Node<E> v, E e) throws IllegalArgumentException {
        if (v.left != null) {
            throw new IllegalArgumentException("This position already has a child on the left.");
        }
        Node<E> newNode = new Node<>(e, v, null, null, 0);
        v.left = newNode;
        this.size++;
        return newNode;
    }

    private Node<E> addRight(Node<E> v, E e) throws IllegalArgumentException {
        if (v.right != null) {
            throw new IllegalArgumentException("This position already has a child on the right.");
        }
        Node<E> newNode = new Node<>(e, v, null, null, 0);
        v.right = newNode;
        this.size++;
        return newNode;
    }
    
    private boolean hasLeft(Node<E> v) {
        return v.left != null;
    }

    private boolean hasRight(Node<E> v) {
        return v.right != null;
    }

    private Node<E> sibling(Node<E> p) {
        Node<E> parent = p.parent;

        // Root dont have brother
        if (parent == null) {
            return null;
        }

        // It can return null if it doesn't exist.
        if (p == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    private boolean hasSibling(Node<E> v) {
        return sibling(v) != null;
    }

    private E replace(Node<E> v, E e) throws IllegalArgumentException {
        E replaced = v.element;
        v.element = e;
        return replaced;
    }

    private void inOrderConsumer(Node<E> v, Consumer<Node<E>> visitor) {
        if (v.left != null) inOrderConsumer(v.left, visitor);
        visitor.accept(v);
        if (v.right != null) inOrderConsumer(v.right, visitor);
    }

}