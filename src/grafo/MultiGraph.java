package grafo;

import java.util.Collection;
import java.util.Set;

public interface MultiGraph<V, E> {

    // ==================== Vértices ====================

    boolean addVertex(V vertex);

    /**
     * Remove o vértice e TODAS as arestas incidentes a ele.
     */
    boolean removeVertex(V vertex);

    boolean containsVertex(V vertex);

    int vertexCount();

    Set<V> getVertices();

    // ==================== Arestas ====================

    /**
     * Adiciona uma aresta conectando 'source' a 'target'.
     * pode ser adicionada múltiplas vezes entre o mesmo par de vértices.
     */
    boolean addEdge(V source, V target, E edge);

    /**
     * Remove uma aresta específica
     */
    boolean removeEdge(E edge);

    /**
     * Remove TODAS as arestas paralelas existentes entre dois vértices.
     * 
     * @return Quantidade de arestas removidas.
     */
    int removeAllEdgesBetween(V source, V target);

    boolean containsEdge(E edge);

    int edgeCountBetween(V source, V target);

    int edgeCount();

    // ========== Consultas de Vizinhança e Incidência ==========

    /**
     * Retorna o conjunto de vértices vizinhos do vértice informado.
     * (Que se conectam por meio de uma aresta)
     */
    Set<V> getNeighbors(V vertex);

    Collection<E> getEdgesBetween(V source, V target);

    Collection<E> getIncidentEdges(V vertex);

    // Collection<> permite flexibilidade na coleção que vai ser utilizada. (Ex: aceita ArrayList<> e HashMap<>) 
    // O List<> é menos flexível e garantiria que o resultado pode ser consultado via índice. 

    // ========== Utilitários ==========

    /**
     * Retorna o vértice de origem da aresta especificada.
     */
    V getEdgeSource(E edge);

    /**
     * Retorna o vértice de destino da aresta especificada.
     */
    V getEdgeTarget(E edge);

    boolean isDirected();
}