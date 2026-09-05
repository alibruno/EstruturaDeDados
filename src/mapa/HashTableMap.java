package mapa;

public class HashTableMap<K, V> implements Map<K, V> {

    private Item<K, V>[] elements;
    private int size;
    private int capacity;
    private int lowerPrime;
    // "Ghost" for marking removed items
    private final Item<K, V> AVAILABLE = new Item<>(null, null);

    @SuppressWarnings("unchecked")
    public HashTableMap() {
        this.capacity = 13;
        this.lowerPrime = 7;
        this.elements = (Item<K, V>[]) new Item[capacity];
        this.size = 0;
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
    public V find(K key) {
        return null;
    }

    @Override
    public V insert(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterable<K> keys() {
        return null;
    }

    @Override
    public Iterable<V> values() {
        return null;
    }

    private static class Item<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Item(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}