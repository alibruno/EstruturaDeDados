package arvore;

import base.Position;

public class BinarySearchTree<E extends Comparable<E>> extends LinkedBinaryTree<E>{

    public Iterable<Position<E>> inOrder() {
        return inOrderIterable();
    }

    private Position<E> treeSearch(Position<E> p, E key) {
        int comp = key.compareTo(p.element());

        if (comp == 0) {
            return p; // Found it
        } else if (comp < 0 && hasLeft(p)) {
            return treeSearch(left(p), key);
        } else if (comp > 0 && hasRight(p)) {
            return treeSearch(right(p), key);
        }

        // Returns where the search stopped (will be the parent of the new node on insertion)
        return p;
    }

    public void insert(E key) {
        if (isEmpty()) {
            addRoot(key);
            return;
        }

        Position<E> p = treeSearch(root(), key);
        int comp = key.compareTo(p.element());

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

        Position<E> p = treeSearch(root(), element);

        // Element does not exist in the tree
        if (element.compareTo(p.element()) != 0) {
            return null;
        }

        E removedElement = p.element();

        // The node has 2 children -> Successor
        if (hasLeft(p) && hasRight(p)) {
            // Find the successor
            Position<E> successor = right(p);
            while (hasLeft(successor)) {
                successor = left(successor);
            }

            // Replace the target node's element with the successor's element
            replace(p, successor.element());

            // Move the pointer 'p' to point to the physical successor (this node will be removed)
            p = successor;
        }

        remove(p);

        return removedElement;
    }

    public Position<E> search(E key) {
        if (isEmpty()) return null;
        Position<E> p = treeSearch(root(), key);
        return key.compareTo(p.element()) == 0 ? p : null;
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
        printTreeRecursive(root(), 0);
        System.out.println("---------------------------");
    }

    // Reverse In-Order
    private void printTreeRecursive(Position<E> p, int depth) {
        // Traverse the right subtree first (displayed at the top)
        if (hasRight(p)) {
            printTreeRecursive(right(p), depth + 1);
        }

        // Print the current node with indentation based on its depth
        String spaces = "      ".repeat(depth);
        System.out.println(spaces + p.element());

        // Traverse the left subtree (displayed at the bottom)
        if (hasLeft(p)) {
            printTreeRecursive(left(p), depth + 1);
        }
    }
}