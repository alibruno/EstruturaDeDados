package grafo;

public class Vertice<E> {
    private int id;
    private E elemento;

    public Vertice(int id, E elemento) {
        this.id = id;
        this.elemento = elemento;
    }

    public int getId() {
        return id;
    }

    public E getElemento() {
        return elemento;
    }

    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

}
