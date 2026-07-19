package heap;

import filaprioridade.PriorityQueue;

import java.util.Comparator;

public class BinaryLinkedHeap<K, V> implements PriorityQueue<K, V> {
    private Node<K, V> root;
    private Node<K, V> lastNode;
    private int size;
    private final Comparator<? super K> comparator;

    public BinaryLinkedHeap(Comparator<? super K> comparator) {
        this.root = new Node<>(null, null, null, null, null);
        this.lastNode = root;
        this.size = 0;
        this.comparator = comparator;
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
    public Entry<K, V> min() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return root;
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
        if (isEmpty()) {
            root = new Node<>(key, value, null, null, null);
            lastNode = root;
            size++;
            return root;
        }

        // Navigation logic determines the parent of the new node
        Node<K, V> parent = getNextInsertionParent();
        Node<K, V> newNode = new Node<>(key, value, parent, null, null);

        // Connects the new node to its parent
        if (parent.left == null) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        lastNode = newNode; // Updates the state for the next operation
        size++;

        upHeap(newNode);
        return newNode;
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        Node<K, V> entryToReturn = new Node<>(root.key, root.value, null, null, null);

        if (size == 1) {
            root = null;
            lastNode = null;
            size = 0;
            return entryToReturn;
        }

        // Identifies the new lastNode before the current one is disconnected
        Node<K, V> previousLast = getPreviousLastNode();

        // Copies the data from the last node to the root
        root.key = lastNode.key;
        root.value = lastNode.value;

        // Disconnects the current lastNode (cuts connection from its parent)
        if (lastNode == lastNode.parent.left) {
            lastNode.parent.left = null;
        } else {
            lastNode.parent.right = null;
        }

        // Updates the state variable to point to the correct node
        lastNode = previousLast;
        size--;

        // Restores the mathematical order
        downHeap(root);

        return entryToReturn;
    }

    // Determines the next insertion position based on the current state
    private Node<K, V> getNextInsertionParent() {
        if (root == null) return null;

        // If the last node was the root itself, the next one is inserted as a child of the root
        if (lastNode == root) {
            return root;
        }

        // 1. If the last node is a left child, the next one goes to the right of the same parent
        if (lastNode == lastNode.parent.left) {
            return lastNode.parent;
        }

        // 2. The last node is a right child. Traverses up the right side until a "turn" to the left is found.
        Node<K, V> current = lastNode;
        while (current != root && current == current.parent.right) {
            current = current.parent;
        }

        // 3. If the root is not reached, navigation takes one step to the right
        if (current != root) {
            current = current.parent.right;
        }

        // 4. Traverses all the way down to the left
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    // Identifies the node that will become the new lastNode after a removal
    private Node<K, V> getPreviousLastNode() {
        if (lastNode == root) return null;

        // 1. If the last node is a right child, the previous one is its left brother
        if (lastNode == lastNode.parent.right) {
            return lastNode.parent.left;
        }

        // 2. The last node is a left child. Traverses up the left side until a "turn" to the right is found.
        Node<K, V> current = lastNode;
        while (current != root && current == current.parent.left) {
            current = current.parent;
        }

        // 3. If the root is not reached, navigation takes one step to the left
        if (current != root) {
            current = current.parent.left;
        }

        // 4. Traverses all the way down to the right
        while (current.right != null) {
            current = current.right;
        }

        return current;
    }

    private void upHeap(Node<K, V> node) {
        while (node != root) {
            Node<K, V> parent = node.parent;
            int cmp = comparator.compare(node.key, parent.key);

            // actualNode < parentNode
            if (cmp < 0) {
                swap(node, parent);
                node = parent; // 'current' must be updated for the next iteration
            } else {
                break; // The order of Min-Heap has been restored
            }
        }
    }

    private void swap(Node<K, V> a, Node<K, V> b) {
        K keyTemp = a.key;
        V valueTemp = a.value;
        a.key = b.key;
        a.value = b.value;
        b.key = keyTemp;
        b.value = valueTemp;
    }

    private void downHeap(Node<K, V> node) {
        while (node != null) {
            Node<K, V> smallerChild = getSmallerChild(node);

            // Of the node is a leaf
            if (smallerChild == null) {
                break;
            }

            int cmp = comparator.compare(node.key, smallerChild.key);

            // If actualNode <= smallerChild, the Min-Heap order is correct
            if (cmp <= 0) {
                break;
            }
            swap(node, smallerChild);
            node = smallerChild;
        }
    }

    private Node<K, V> getSmallerChild(Node<K, V> node) {
        Node<K, V> leftChild = node.left;
        Node<K, V> rightChild = node.right;

        if (leftChild == null) {
            return null; // Node is a leaf
        }

        Node<K, V> smallerChild = leftChild;

        if (rightChild != null) {
            int cmpChildren = comparator.compare(rightChild.key, leftChild.key);
            if (cmpChildren < 0) {
                smallerChild = rightChild;
            }
        }

        return smallerChild;
    }

    private static class Node<K, V> implements PriorityQueue.Entry<K, V> {
        private K key;
        private V value;
        private Node<K, V> parent;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value, Node<K, V> parent, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }
    }
}