package sequencia;

public interface Sequence {
    // Métodos Genéricos
    int size();

    boolean isEmpty();

    // Métodos de Lista (Trabalham diretamente com a classe Node)
    Node first();

    Node last();

    Node before(Node p);

    Node after(Node p);

    Object replaceElement(Node p, Object e);

    void swapElements(Node p, Node q);

    Node insertBefore(Node p, Object e);

    Node insertAfter(Node p, Object e);

    Node insertFirst(Object e);

    Node insertLast(Object e);

    Object remove(Node p);

    // Métodos de Vetor (Baseados em Rank/Índice)
    Object elemAtRank(int r);

    Object replaceAtRank(int r, Object e);

    void insertAtRank(int r, Object e);

    Object removeAtRank(int r);

    // Métodos Ponte
    Node atRank(int r);      // Retorna o Node daquela posição

    int rankOf(Node p);      // Recebe o Node e retorna seu índice
}