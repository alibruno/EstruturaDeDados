package sequencia;

public class TestSequence {
    public static void main(String[] args) {
        Sequence seq = new LinkedSequence();

        System.out.println("=== TESTE TAD SEQUÊNCIA ===");
        System.out.println("A sequência está vazia? " + seq.isEmpty());
        System.out.println("Tamanho inicial: " + seq.size());
        System.out.println();

        // 1. Testando métodos de Lista
        System.out.println("--- 1. Inserções via métodos de Lista ---");
        seq.insertFirst("B");
        seq.insertFirst("A");
        seq.insertLast("D");

        // Pegando o nó do "B" para inserir "C" depois dele
        Node noB = seq.atRank(1);
        seq.insertAfter(noB, "C");

        imprimir(seq); // Esperado: [A, B, C, D]

        // 2. Testando métodos de Vetor (Rank)
        System.out.println("\n--- 2. Inserções via métodos de Vetor (Rank) ---");
        seq.insertAtRank(0, "Z"); // Insere no início
        seq.insertAtRank(3, "X"); // Insere no meio
        seq.insertAtRank(6, "Y"); // Insere no fim (tamanho era 6)

        imprimir(seq); // Esperado: [Z, A, B, X, C, D, Y]

        // 3. Testando métodos Ponte
        System.out.println("\n--- 3. Métodos Ponte ---");
        Node noX = seq.atRank(3);
        System.out.println("Elemento no rank 3: " + noX.getElement());
        System.out.println("Qual é o rank do elemento 'X'? " + seq.rankOf(noX));

        // 4. Testando Remoções
        System.out.println("\n--- 4. Remoções ---");
        Object removido1 = seq.removeAtRank(0); // Remove "Z"
        System.out.println("Removido do rank 0: " + removido1);

        Object removido2 = seq.remove(noX); // Remove "X" passando o Nó diretamente
        System.out.println("Removido por Nó: " + removido2);

        imprimir(seq); // Esperado: [A, B, C, D, Y]

        // 5. Testando Substituição (Replace)
        System.out.println("\n--- 5. Substituição ---");
        seq.replaceAtRank(4, "E"); // Troca "Y" por "E"
        imprimir(seq); // Esperado: [A, B, C, D, E]
    }

    // Método auxiliar para imprimir a sequência e ver o que está acontecendo
    private static void imprimir(Sequence seq) {
        System.out.print("Estado atual: [");
        for (int i = 0; i < seq.size(); i++) {
            System.out.print(seq.elemAtRank(i));
            if (i < seq.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] (Tamanho: " + seq.size() + ")");
    }
}
