package arvore.test;

import arvore.AVLTree;

public class TestAVLTree {
    public static void main(String[] args) {
        AVLTree<Integer> avl = new AVLTree<>();

        // Usando exemplo de coleguinha
        int[] inicial = {10, 5, 15, 2, 8, 22};
        for (int x : inicial) {
            avl.insert(x);
        }

        System.out.println("\n--- MOSTRAR ---");
        avl.printTree();

        System.out.println("\nINSERIR 25");
        avl.insert(25);

        System.out.println("\n--- Estado Pós-Inserção de 25 e Rotação S.E. ---");
        avl.printTree();

        System.out.println("\nREMOVER 5");
        avl.remove(5);

        System.out.println("\n--- Estado Final Após Remover 5 ---");
        avl.printTree();

        System.out.println("\nBUSCAR NÓ 8");
        System.out.println(avl.search(8));
    }
}
