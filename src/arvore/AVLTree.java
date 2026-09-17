package arvore;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class AVLTree<E extends Comparable<E>> {
    Node<E> root;
    int size;

    public AVLTree() {
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

        // Returns where the search stopped (parent for the new node on insertion)
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
            checkBalanceFactor(addLeft(p, key));
        } else {
            checkBalanceFactor(addRight(p, key));
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

        // 2 children -> search successor
        if (hasLeft(p) && hasRight(p)) {
            Node<E> successor = p.right;
            while (hasLeft(successor)) {
                successor = successor.left;
            }

            // Replace | node e -> successor e
            replace(p, successor.element);

            p = successor;
        }

        // p -> 1/0 child
        Node<E> child = hasLeft(p) ? p.left : p.right;
        Node<E> parent = p.parent;
        boolean wasLeftChild = (parent != null && parent.left == p);

        if (child != null) {
            child.parent = parent;
        }

        // root removed
        if (parent == null) {
            this.root = child;
        } else {
            if (wasLeftChild) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }

        this.size--;

        checkBalanceFactorAfterRemoval(parent, wasLeftChild);

        return removedElement;
    }

    public Node<E> search(E key) {
        if (isEmpty())
            return null;
        Node<E> p = treeSearch(root, key);
        return key.compareTo(p.element) == 0 ? p : null;
    }


    /**
     * Print da árvore no console. Adaptação para nós avl com fator de balanceamento ao lado.
     * @author Robinson Luis (aka coleguinha)
     */
    public void printTree() {
        Node[][] mat = new Node[height(root) + 1][size];

        inOrderMat(root, mat, 0);

        for (int l = 0; l < height(root) + 1; l++) {
            for (int c = 0; c < size; c++) {
                Node node = mat[l][c];

                if (node == null) {
                    System.out.print("    "); // 4 spaces
                } else {
                    System.out.print(node.element + "[" + node.balanceFactor + "]");
                }
            }

            System.out.println();
        }
    }

    private int inOrderMat(Node<E> v, Node[][] mat, int i) {
        if (v.left != null) {
            i = inOrderMat(v.left, mat, i);
        }

        mat[depth(v)][i] = v;
        i++;

        if (v.right != null) {
            i = inOrderMat(v.right, mat, i);
        }

        return i; // retorno para atualizar o i durante a chamada recursiva. i++ não é refletida nas chamadas recursivas anteriores/posteriores.
    }

    private static class Node<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private int balanceFactor;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right, int balanceFactor) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.balanceFactor = balanceFactor;
        }
    }

    // AUXILIARY METHODS: AVL

    private void checkBalanceFactor(Node<E> node) {
        Node<E> current = node;

        while (current.parent != null) {
            Node<E> parent = current.parent;

            // Insertion | left -> BF (+1) | right -> BF (-1)
            if (current == parent.left) {
                parent.balanceFactor += 1;
            } else {
                parent.balanceFactor -= 1;
            }

            // BF = 0 -> node is now balanced
            if (parent.balanceFactor == 0) {
                break;
            }

            // Unbalanced node
            else if (parent.balanceFactor == 2 || parent.balanceFactor == -2) {
                rebalance(parent);
                break;
            }

            // BF -> +1 or -1
            current = parent;
        }
    }

    private void checkBalanceFactorAfterRemoval(Node<E> parent, boolean wasLeftChild) {
        Node<E> node = parent;
        boolean childWasLeft = wasLeftChild;

        while (node != null) {
            Node<E> nextParent = node.parent;
            boolean nextIsLeft = (nextParent != null && nextParent.left == node);

            // Remove | left -> BF (+1) | right -> BF (-1)
            if (childWasLeft) {
                node.balanceFactor -= 1;
            } else {
                node.balanceFactor += 1;
            }

            // BF = +1 or -1 -> (before) node BF = 0 -> balanced
            if (node.balanceFactor == 1 || node.balanceFactor == -1) {
                break;
            }

            // Unbalanced node
            else if (node.balanceFactor == 2 || node.balanceFactor == -2) {
                Node<E> newSubtreeRoot = rebalance(node);
                // new root -> BF != 0 | balenced
                if (newSubtreeRoot.balanceFactor != 0) {
                    break;
                }
                node = newSubtreeRoot;
            }

            childWasLeft = nextIsLeft;
            node = nextParent;
        }
    }

    private Node<E> rebalance(Node<E> p) {
        if (p.balanceFactor == 2) { // Left-heavy
            if (p.left.balanceFactor >= 0) {
                return rotateRight(p);
            } else {
                return rotateDoubleRight(p);
            }
        } else if (p.balanceFactor == -2) { // Right-heavy
            if (p.right.balanceFactor <= 0) {
                return rotateLeft(p);
            } else {
                return rotateDoubleLeft(p);
            }
        }
        return p;
    }

    /*
    10 (p) -> BF = -2
      \
       20 (q) -> BF = -1
        \
         30 -> BF = 0
    */
    private Node<E> rotateLeft(Node<E> p) {
        Node<E> q = p.right;
        Node<E> parent = p.parent;

        p.right = q.left;
        if (q.left != null) {
            q.left.parent = p;
        }

        q.left = p;
        p.parent = q;

        q.parent = parent;
        if (parent == null) {
            this.root = q;
        } else if (parent.left == p) {
            parent.left = q;
        } else {
            parent.right = q;
        }

        int pBF = p.balanceFactor;
        int qBF = q.balanceFactor;

        p.balanceFactor = pBF + 1 - Math.min(qBF, 0);
        q.balanceFactor = qBF + 1 + Math.max(p.balanceFactor, 0);

        return q;
    }

    /*
          10 (p) -> BF = +2
         /
       20 (q) -> BF = +1
      /
    30 -> BF = 0
    */
    private Node<E> rotateRight(Node<E> p) {
        Node<E> q = p.left;
        Node<E> parent = p.parent;

        p.left = q.right;
        if (q.right != null) {
            q.right.parent = p;
        }

        q.right = p;
        p.parent = q;

        q.parent = parent;
        if (parent == null) {
            this.root = q;
        } else if (parent.left == p) {
            parent.left = q;
        } else {
            parent.right = q;
        }

        int pBF = p.balanceFactor;
        int qBF = q.balanceFactor;

        p.balanceFactor = pBF - 1 - Math.max(qBF, 0);
        q.balanceFactor = qBF - 1 + Math.min(p.balanceFactor, 0);

        return q;
    }

    /*
    10 (p, BF=-2)             10 (p, BF=-2)                      20 (BF=0)
      \                         \                               /  \
      30 (q, BF=+1) ==========>  20 (r, BF=-1)  ============> 10    30
      /                            \                          (BF=0)(BF=0)
    20 (r, BF=0)                   30 (q, BF=0)
    */
    private Node<E> rotateDoubleLeft(Node<E> p) {
        rotateRight(p.right);
        return rotateLeft(p);
    }

    /*
      10 (p, BF=+2)               10 (p, BF=+2)                  20 (BF=0)
     /                           /                              /  \
    30 (q, BF=-1)  ==========> 20 (r, BF=+1)  ===========>    30    10
     \                         /                             (BF=0)(BF=0)
      20 (r, BF=0)            30 (q, BF=0)
     */
    private Node<E> rotateDoubleRight(Node<E> p) {
        rotateLeft(p.left);
        return rotateRight(p);
    }

    // AUXILIARY METHODS: BINARY TREE

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
            throw new IllegalArgumentException("This node already has a child on the left.");
        }
        Node<E> newNode = new Node<>(e, v, null, null, 0);
        v.left = newNode;
        this.size++;
        return newNode;
    }

    private Node<E> addRight(Node<E> v, E e) throws IllegalArgumentException {
        if (v.right != null) {
            throw new IllegalArgumentException("This node already has a child on the right.");
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

    private E replace(Node<E> v, E e) throws IllegalArgumentException {
        E replaced = v.element;
        v.element = e;
        return replaced;
    }

    private int depth(Node<E> v) {
        if (v == root) {
            return 0;
        } else {
            return 1 + depth(v.parent);
        }
    }

    private int height(Node<E> v) {
        int h = 0;
        if (v.left != null)
            h = Math.max(h, 1 + height(v.left));
        if (v.right != null)
            h = Math.max(h, 1 + height(v.right));
        return h;
    }

    private void inOrderConsumer(Node<E> v, Consumer<Node<E>> visitor) {
        if (v.left != null) {
            inOrderConsumer(v.left, visitor);
        }
        visitor.accept(v);
        if (v.right != null) {
            inOrderConsumer(v.right, visitor);
        }
    }
}