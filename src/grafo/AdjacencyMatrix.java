package grafo;

import java.util.Collection;
import java.util.Set;

public class AdjacencyMatrix<V, E> implements MultiGraph<V, E> {
    private static class Vertex {
        private Long id; // Para ser possível colocá-lo em um HashMap e tornar as consultas O(logn)
        private String label;

        public Vertex(Long id, String label) {
            this.id = id;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Vertex [id=" + id + ", label=" + label + "]";
        }
    }

    private static class Edge {
        private Long id; // Para ser possível colocá-lo em um HashMap e tornar as consultas O(logn), e
                         // vertíces podem possuir mais de uma aresta.

        // Dois objetos ao invés de ArrayList<> para facilitar quando é direcionado
        private Vertex source;
        private Vertex target;

        private boolean isDirected;

        public Edge(Long id, Vertex source, Vertex target, boolean isDirected) {
            this.id = id;
            this.source = source;
            this.target = target;
            this.isDirected = isDirected;
        }

        @Override
        public String toString() {
            return "Edge [id=" + id + ", source=" + source + ", target=" + target + ", isDirected=" + isDirected + "]";
        }

    }

    @Override
    public boolean addVertex(V vertex) {
        return false;
    }

    @Override
    public boolean removeVertex(V vertex) {
        return false;
    }

    @Override
    public boolean containsVertex(V vertex) {
        return false;
    }

    @Override
    public int vertexCount() {
        return 0;
    }

    @Override
    public Set<V> getVertices() {
        return null;
    }

    @Override
    public boolean addEdge(V source, V target, E edge) {
        return false;
    }

    @Override
    public boolean removeEdge(E edge) {
        return false;
    }

    @Override
    public int removeAllEdgesBetween(V source, V target) {
        return 0;
    }

    @Override
    public boolean containsEdge(E edge) {
        return false;
    }

    @Override
    public int edgeCountBetween(V source, V target) {
        return 0;
    }

    @Override
    public int edgeCount() {
        return 0;
    }

    @Override
    public Set<V> getNeighbors(V vertex) {
        return null;
    }

    @Override
    public Collection<E> getEdgesBetween(V source, V target) {
        return null;
    }

    @Override
    public Collection<E> getIncidentEdges(V vertex) {
        return null;
    }

    @Override
    public V getEdgeSource(E edge) {
        return null;
    }

    @Override
    public V getEdgeTarget(E edge) {
        return null;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

}
