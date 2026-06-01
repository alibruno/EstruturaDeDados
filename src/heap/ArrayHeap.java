package heap;

import filaprioridade.Entry;
import filaprioridade.PriorityQueue;

public class ArrayHeap<K, V> implements PriorityQueue<K, V> {
    private Node<K, V>[] elements;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayHeap(int capacity) {
        this.capacity = capacity;
        this.elements = (Node<K, V>[]) new Node[capacity];
        this.size = 0;
    }

    @Override
    public void insert(K key, V value) {

    }

    @Override
    public V removeMin() {
        return null;
    }

    @Override
    public V min() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class Node<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

}
