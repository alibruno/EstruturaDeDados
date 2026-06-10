package lista;

public class TestList {

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("===== TESTANDO LISTA COM ARRAY CIRCULAR =====");
        System.out.println("=============================================");
        // Supondo que a sua interface se chame List e a classe ArrayList
        testArrayList(new ArrayList<>(3));

        System.out.println("\n\n=============================================");
        System.out.println("===== TESTANDO LISTA DUPLAMENTE LIGADA  =====");
        System.out.println("=============================================");
        testPositionList(new LinkedPositionList<>());
    }

    // =========================================================================
    // TESTE PARA A LISTA BASEADA EM ÍNDICES (ARRAY)
    // =========================================================================
    private static void testArrayList(List<String> list) {
        System.out.println("Lista vazia? " + list.isEmpty());

        System.out.println("\n--- insertFirst / insertLast ---");
        list.insertFirst("B");
        list.insertLast("D");
        list.insertFirst("A");
        list.insertLast("E"); // A lista vai fazer o grow() aqui (capacidade era 3)
        // Esperado: [A, B, D, E]

        System.out.println("Size: " + list.size());
        System.out.println(list);

        System.out.println("\n--- insertBefore / insertAfter ---");
        list.insertAfter(1, "C");  // Insere depois do B (índice 1) -> Vira índice 2
        list.insertBefore(0, "Z"); // Insere antes do A (índice 0)
        // Esperado: [Z, A, B, C, D, E]

        System.out.println("Size: " + list.size());
        System.out.println(list);

        System.out.println("\n--- before / after ---");
        // O 'C' está no índice 3 [Z(0), A(1), B(2), C(3), D(4), E(5)]
        System.out.println("Antes do índice 3 ('C'): " + list.before(3));
        System.out.println("Depois do índice 3 ('C'): " + list.after(3));
        System.out.println("Primeiro: " + list.first() + " | Último: " + list.last());

        System.out.println("\n--- swapElements ---");
        list.swapElements(1, 4); // Troca 'A' e 'D'
        System.out.print("Após swap (índices 1 e 4): ");
        System.out.println(list);

        System.out.println("\n--- remove ---");
        String removedMid = list.remove(3); // Remove o 'C'
        System.out.println("Meio - removido índice 3 -> '" + removedMid + "'");

        String removed0 = list.remove(0); // Remove o 'Z'
        System.out.println("Início - removido índice 0 -> '" + removed0 + "'");

        String removedEnd = list.remove(list.size() - 1); // Remove o último ('E')
        System.out.println("Final - removido último índice -> '" + removedEnd + "'");

        System.out.println(list);
        System.out.println("Size: " + list.size());

        System.out.println("\n--- Exceções (Array) ---");
        try {
            list.remove(50);
            System.out.println("ERRO: Deveria ter lançado exceção!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Acesso inválido bloqueado com sucesso: " + e.getMessage());
        }
    }

    // =========================================================================
    // TESTE PARA A LISTA BASEADA EM POSIÇÕES (NÓS)
    // =========================================================================
    private static void testPositionList(PositionList<String> list) {
        System.out.println("Lista vazia? " + list.isEmpty());

        System.out.println("\n--- insertFirst / insertLast ---");
        Position<String> posB = list.insertFirst("B");
        Position<String> posD = list.insertLast("D");
        Position<String> posA = list.insertFirst("A");
        Position<String> posE = list.insertLast("E");
        // Esperado logico: [A, B, D, E]

        System.out.println("Size: " + list.size());

        System.out.println("\n--- insertBefore / insertAfter ---");
        Position<String> posC = list.insertAfter(posB, "C");
        Position<String> posZ = list.insertBefore(posA, "Z");
        // Esperado logico: [Z, A, B, C, D, E]

        System.out.println("Size: " + list.size());

        System.out.println("\n--- before / after ---");
        System.out.println("Antes de 'C': " + list.before(posC).element());
        System.out.println("Depois de 'C': " + list.after(posC).element());
        System.out.println("Primeiro: " + list.first().element() + " | Último: " + list.last().element());
        System.out.println("isFirst('Z')? " + list.isFirst(posZ) + " | isLast('E')? " + list.isLast(posE));

        System.out.println("\n--- replaceElement ---");
        list.replaceElement(posC, "X"); // Novo elemento no nó onde estava o 'C'
        System.out.println("Após replace, o nó que era 'C' agora é: " + posC.element());

        System.out.println("\n--- swapElements ---");
        list.swapElements(posA, posD);
        System.out.println("Após swap, posA tem: '" + posA.element() + "' e posD tem: '" + posD.element() + "'");

        System.out.println("\n--- remove ---");
        String removedMid = list.remove(posA); // posA agora guarda o 'D' devido ao swap
        System.out.println("Meio - removido nó posA -> '" + removedMid + "'");

        String removed0 = list.remove(list.first());
        System.out.println("Início - removido primeiro -> '" + removed0 + "'");

        String removedEnd = list.remove(list.last());
        System.out.println("Final - removido último -> '" + removedEnd + "'");

        System.out.println("Size final: " + list.size());

        System.out.println("\n--- Exceções (Position) ---");

        System.out.print("Remover nó já removido: ");
        try {
            list.remove(posA); // posA já foi removido acima!
            System.out.println("ERRO: Deveria ter lançado exceção!");
        } catch (IllegalArgumentException e) {
            System.out.println("Bloqueado (" + e.getMessage() + ")");
        }

        System.out.print("Acesso inválido (before do primeiro): ");
        try {
            Position<String> beforeFirst = list.before(list.first());
            if (beforeFirst == null) {
                System.out.println("Retornou null (Comportamento esperado!)");
            } else {
                System.out.println("ERRO: Deveria ser null ou lançar exceção.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Bloqueado (" + e.getMessage() + ")");
        }
    }
}