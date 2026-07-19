package heap;

import java.util.Comparator;

public class TestBinaryHeap {

    public static void main(String[] args) {
        // Criamos o Heap usando a ordem natural dos números (menor para o maior)
        BinaryLinkedHeap<Integer, String> heap = new BinaryLinkedHeap<>(Comparator.naturalOrder());

        System.out.println("1. O heap começou vazio? " + heap.isEmpty());

        System.out.println("\n2. Inserindo elementos fora de ordem (50, 10, 30, 20, 40)...");
        heap.insert(50, "Cinquenta");
        heap.insert(10, "Dez");
        heap.insert(30, "Trinta");
        heap.insert(20, "Vinte");
        heap.insert(40, "Quarenta");

        System.out.println("Tamanho atual: " + heap.size());
        System.out.println("Menor elemento na raiz: " + heap.min().key() + " (" + heap.min().value() + ")");

        System.out.println("\n3. Esvaziando o Heap (Os números PRECISAM sair em ordem crescente):");
        while (!heap.isEmpty()) {
            System.out.println(" -> Removido: " + heap.removeMin().key());
        }

        System.out.println("\n4. O heap está vazio de novo? " + heap.isEmpty());
    }
}