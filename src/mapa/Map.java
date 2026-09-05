package mapa;

public interface Map<K, V> {
    int size();

    boolean isEmpty();

    V find(K key);

    V insert(K key, V value);

    V remove(K key);

    Iterable<K> keys();

    Iterable<V> values();

    interface Entry<K, V> {
        K getKey();

        V getValue();
    }
}
