package arvore.busca;

import arvore.Position;

public class TestBST {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        System.out.println("=== Construindo a árvore inicial ===");
        int[] valoresIniciais = {10, 5, 15, 2, 8, 22};
        for (int val : valoresIniciais) {
            bst.insert(val);
        }
        bst.printTree();

        System.out.println("\n=== Inserindo o 25 ===");
        bst.insert(25);
        bst.printTree();

        System.out.println("\n=== Removendo o 5 (Nó com 2 filhos) ===");
        System.out.println("O sucessor do 5 na subárvore direita é o 8. O 8 tomará o lugar do 5.");
        bst.remove(5);
        bst.printTree();

    }

//    // Metodo auxiliar para print da árvore - inline
//    private static void imprimirInOrder(BinarySearchTree<?> bst) {
//        System.out.print("In-Order (Ordem Crescente): ");
//        for (Position<?> p : bst.inOrder()) {
//            System.out.print(p.element() + " ");
//        }
//        System.out.println();
//    }
}
