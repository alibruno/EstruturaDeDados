package arvore;

public interface Position<E> {
    /**
     * Returns the element stored at this position.
     *
     * @return the element stored at this position
     * @throws IllegalStateException if the position is no longer valid
     */
    E getElement() throws IllegalStateException;
}
