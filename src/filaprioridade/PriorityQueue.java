package filaprioridade;

public interface PriorityQueue<K, V> {
    // Main methods
    void insert(K key, V value);

    V removeMin();

    // Additional methods
    V min();

    int size();

    boolean isEmpty();

}
