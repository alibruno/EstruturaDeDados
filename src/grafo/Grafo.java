package grafo;

import java.util.ArrayList;

public interface Grafo<V, E> {

    // ==================== Métodos de Acesso ====================

    /**
     * Retorna um array contendo os dois vértices extremos da aresta e.
     */
    ArrayList<Vertice<V>> verticesFinais(Aresta<E> e);

    /**
     * Retorna o vértice oposto a v na aresta e.
     * Ocorre um erro se a aresta não for incidente a v.
     */
    Vertice<V> oposto(Vertice<V> v, Aresta<E> e);

    /**
     * Retorna true se os vértices v e w forem adjacentes.
     */
    boolean eAdjacente(Vertice<V> v, Vertice<V> w);

    /**
     * Substitui o elemento armazenado no vértice v por x.
     * Retorna o elemento anteriormente armazenado.
     */
    V substituir(Vertice<V> v, V x);

    /**
     * Substitui o elemento armazenado na aresta e por x.
     * Retorna o elemento anteriormente armazenado.
     */
    E substituir(Aresta<E> e, E x);

    // ==================== Métodos de Atualização ====================

    /**
     * Insere um novo vértice armazenando o elemento informado.
     * Retorna o vértice criado.
     */
    Vertice<V> inserirVertice(V elemento);

    /**
     * Insere uma nova aresta não direcionada entre os vértices v e w,
     * armazenando o elemento informado.
     * Retorna a aresta criada.
     */
    Aresta<E> inserirAresta(Vertice<V> v, Vertice<V> w, E elemento);

    /**
     * Remove o vértice v e todas as arestas incidentes a ele.
     * Retorna o elemento armazenado no vértice removido.
     */
    V removerVertice(Vertice<V> v);

    /**
     * Remove a aresta e do grafo.
     * Retorna o elemento armazenado na aresta removida.
     */
    E removerAresta(Aresta<E> e);

    // ==================== Métodos Iteradores ====================

    /**
     * Retorna uma coleção de todas as arestas incidentes ao vértice v.
     */
    Iterable<Aresta<E>> arestasIncidentes(Vertice<V> v);

    /**
     * Retorna uma coleção contendo todos os vértices do grafo.
     */
    Iterable<Vertice<V>> vertices();

    /**
     * Retorna uma coleção contendo todas as arestas do grafo.
     */
    Iterable<Aresta<E>> arestas();

    // ==================== Métodos para Grafos Direcionados ====================

    /**
     * Verifica se a aresta e é direcionada.
     */
    boolean eDirecionada(Aresta<E> e);

    /**
     * Insere uma nova aresta direcionada com origem em v e destino em w,
     * armazenando o elemento informado.
     * Retorna a aresta criada.
     */
    Aresta<E> inserirArestaDirecionada(Vertice<V> v, Vertice<V> w, E elemento);
}
