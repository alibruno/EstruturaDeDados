package sequencia;

public class LinkedSequence implements Sequence {

    private Node head;  // Nó sentinela de início
    private Node tail; // Nó sentinela de fim
    private int size;

    public LinkedSequence() {
        head = new Node(null, null, null);
        tail = new Node(null, head, null);
        head.setNext(tail);
        size = 0;
    }

    // MÉTODOS GENÉRICOS

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // MÉTODOS PONTE
    
    public Node atRank(int r) {
        if (r < 0 || r > size) throw new IndexOutOfBoundsException("Invalid rank: " + r);

        Node node;
        if (r <= size() / 2) {
            node = head.getNext();
            for (int i = 0; i < r; i++) node = node.getNext();
        } else {
            node = tail.getPrev();
            for (int i = 0; i < size() - r - 1; i++) node = node.getPrev();
        }
        return node;
    }

    public int rankOf(Node p) {
        if (p == null) throw new IllegalArgumentException("Node cannot be null");

        Node current = head.getNext();
        int r = 0;
        while (current != p && current != tail) {
            current = current.getNext();
            r++;
        }
        if (current == tail) throw new IllegalArgumentException("Node not found in the sequence");
        return r;
    }

    // MÉTODOS DE LISTA (Operam nos Nós)
    public Node first() {
        return head.getNext();
    }

    public Node last() {
        return tail.getPrev();
    }

    public Node before(Node p) {
        return p.getPrev();
    }

    public Node after(Node p) {
        return p.getNext();
    }

    public Node insertBefore(Node p, Object e) {
        if (p == null) throw new IllegalArgumentException("Node cannot be null");

        Node novoNode = new Node(e, p.getPrev(), p);
        p.getPrev().setNext(novoNode);
        p.setPrev(novoNode);
        size++;
        return novoNode;
    }

    public Node insertAfter(Node p, Object e) {
        if (p == null) throw new IllegalArgumentException("Node cannot be null");

        Node novoNode = new Node(e, p, p.getNext());
        p.getNext().setPrev(novoNode);
        p.setNext(novoNode);
        size++;
        return novoNode;
    }

    public Node insertFirst(Object e) {
        return insertAfter(head, e);
    }

    public Node insertLast(Object e) {
        return insertBefore(tail, e);
    }

    public Object remove(Node p) {
        if (p == null) throw new IllegalArgumentException("Node cannot be null");

        Node prev = p.getPrev();
        Node next = p.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size--;

        Object element = p.getElement();
        p.setNext(null);
        p.setPrev(null);
        return element;
    }

    public Object replaceElement(Node p, Object e) {
        if (p == null) throw new IllegalArgumentException("Node cannot be null");

        Object old = p.getElement();
        p.setElement(e);
        return old;
    }

    public void swapElements(Node p, Node q) {
        if (p == null || q == null) throw new IllegalArgumentException("Node cannot be null");

        Object temp = p.getElement();
        p.setElement(q.getElement());
        q.setElement(temp);
    }

    // MÉTODOS DE VETOR (Índices/Ranks)

    public Object elemAtRank(int r) {
        return atRank(r).getElement();
    }

    public Object replaceAtRank(int r, Object e) {
        return replaceElement(atRank(r), e);
    }

    public void insertAtRank(int r, Object e) {
        if (r == size) {
            insertLast(e);
        } else {
            insertBefore(atRank(r), e);
        }
    }

    public Object removeAtRank(int r) {
        return remove(atRank(r));
    }
}