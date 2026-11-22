package grafo;

public class Aresta<E> {
    private int id;
    private Vertice<E> verticeOrigem;
    private Vertice<E> verticeDestino;
    private E elemento;
    private boolean ehDirecionada;

    public Aresta(int id, Vertice<E> verticeOrigem, Vertice<E> verticeDestino, E elemento, boolean ehDirecionada) {
        this.id = id;
        this.verticeOrigem = verticeOrigem;
        this.verticeDestino = verticeDestino;
        this.elemento = elemento;
        this.ehDirecionada = ehDirecionada;
    }

    public int getId() {
        return id;
    }

    public Vertice<E> getVerticeOrigem() {
        return verticeOrigem;
    }

    public void setVerticeOrigem(Vertice<E> verticeOrigem) {
        this.verticeOrigem = verticeOrigem;
    }

    public Vertice<E> getVerticeDestino() {
        return verticeDestino;
    }

    public void setVerticeDestino(Vertice<E> verticeDestino) {
        this.verticeDestino = verticeDestino;
    }

    public E getElemento() {
        return elemento;
    }

    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    public boolean isEhDirecionada() {
        return ehDirecionada;
    }
    
}
