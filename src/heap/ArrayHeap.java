package heap;

import filaprioridade.Entry;
import filaprioridade.PriorityQueue;

import java.util.Comparator;

public class ArrayHeap<K, V> implements PriorityQueue<K, V> {
    private Node<K, V>[] elements;
    private int size;
    private int capacity;
    private final Comparator<? super K> comparator;

    @SuppressWarnings("unchecked")
    public ArrayHeap(int capacity, Comparator<? super K> comparator) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        this.elements = (Node<K, V>[]) new Node[capacity + 1]; // index 0 is empty
        this.capacity = capacity;
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public void insert(K key, V value) {
        if (size == capacity) {
            resize();
        }
        Node<K, V> kvNode = new Node<>(key, value);
        elements[++size] = kvNode;
        upheap(size);
    }

    @Override
    public V removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        // The value of the root is stored
        V minValue = elements[1].value();
        // The last element of the Heap is taken and placed in the root
        elements[1] = elements[size];

        elements[size] = null;
        size--;

        // If the heap is not empty, it is sorted by descending the element from the root
        if (size > 0) {
            downheap(1);
        }

        return minValue;
    }

    private void downheap(int i) {
        // The loop iterates as long as the current node has at least one child (the one on the left)
        while (2 * i <= size) {
            int smallerChild = getSmallerChild(i);
            int cmp = comparator.compare(elements[i].key(), elements[smallerChild].key());

            // If actualNode <= smallerChild, the Min-Heap order is correct
            if (cmp <= 0) {
                break;
            }
            swap(i, smallerChild);
            i = smallerChild;
        }
    }

    private int getSmallerChild(int i) {
        int leftChild = 2 * i;
        int rightChild = 2 * i + 1;

        int smallerChild = leftChild;

        if (rightChild <= size) {
            int cmpChildren = comparator.compare(elements[rightChild].key(), elements[leftChild].key());
            if (cmpChildren < 0) {
                smallerChild = rightChild;
            }
        }
        return smallerChild;
    }

    @Override
    public V min() {
        if (isEmpty()) {
            return null;
        }
        // min element -> root value (Min-Heap)
        return elements[1].value();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newElements = (Node<K, V>[]) new Node[newCapacity + 1];

        for (int i = 1; i <= size; i++) {
            newElements[i] = elements[i];
        }

        this.elements = newElements;
        this.capacity = newCapacity;
    }

    private void upheap(int i) {
        while (i > 1) {
            int parent = i / 2;
            int cmp = comparator.compare(elements[i].key(), elements[parent].key());

            // actualNode < parentNode
            if (cmp < 0) {
                swap(i, parent);
                i = parent; // 'i' must be updated for the next iteration
            } else {
                break; // The order of Min-Heap has been restored
            }
        }
    }

    private void swap(int i, int j) {
        Node<K, V> temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    private static class Node<K, V> implements Entry<K, V> {
        private final K key;
        private final V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
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