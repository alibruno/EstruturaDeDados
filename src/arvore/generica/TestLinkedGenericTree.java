package arvore.generica;

import arvore.Position;

public class TestLinkedGenericTree {

    public static void main(String[] args) {
        System.out.println("=== 1. CRIANDO O SISTEMA DE ARQUIVOS ===\n");
        LinkedGenericTree<String> fileSystem = new LinkedGenericTree<>();

        // Adicionando a Raiz
        Position<String> root = fileSystem.addRoot("C:/");

        // Adicionando pastas principais
        Position<String> windows = fileSystem.addChild(root, "Windows");
        Position<String> programFiles = fileSystem.addChild(root, "Program Files");
        Position<String> users = fileSystem.addChild(root, "Users");

        // Adicionando subpastas
        fileSystem.addChild(windows, "System32");

        Position<String> maria = fileSystem.addChild(users, "Maria");
        Position<String> joao = fileSystem.addChild(users, "Joao");

        fileSystem.addChild(maria, "Documentos");
        Position<String> downloads = fileSystem.addChild(maria, "Downloads");

        System.out.println("Sistema criado com sucesso! Tamanho total: " + fileSystem.size() + " pastas.");


        System.out.println("\n=== 2. TESTANDO CONSUMER COM LAMBDA (Árvore Identada) ===");
        // É usado o depth() para calcular quantos espaços dar na identação!
        fileSystem.preOrder(root, p -> {
            int profundidade = fileSystem.depth(p);
            String espacos = "  ".repeat(profundidade); // Cria a identação baseada no nível
            System.out.println(espacos + "|- " + p.element());
        });


        System.out.println("\n=== 3. GEOMETRIA DA ÁRVORE ===");
        System.out.println("Altura total da árvore: " + fileSystem.height());
        System.out.println("Altura da pasta 'Users': " + fileSystem.height(users)); // Esperado: 2 (Users -> Maria -> Documentos)
        System.out.println("Profundidade da pasta 'Downloads': " + fileSystem.depth(downloads)); // Esperado: 3 (C:/ -> Users -> Maria -> Downloads)


        System.out.println("\n=== 4. TESTANDO ITERADORES (Laço For) ===");
        System.out.print("Travessia Padrão (Iterator de Elementos): ");
        for (String pasta : fileSystem) {
            System.out.print(pasta + " -> ");
        }
        System.out.println("FIM");

        System.out.print("\nTravessia Post-Order (De baixo para cima): ");
        for (Position<String> p : fileSystem.postOrderIterable()) {
            System.out.print(p.element() + " -> ");
        }
        System.out.println("FIM");


        System.out.println("\n=== 5. TESTANDO REMOÇÃO E SEGURANÇA ===");

        // 5.1 Tentando remover nó com mais de 1 filho (Deve falhar)
        try {
            System.out.println("Tentando remover a pasta 'Users' (que tem Maria e João)...");
            fileSystem.remove(users);
        } catch (IllegalStateException e) {
            System.out.println("Bloqueado com sucesso: " + e.getMessage());
        }

        // 5.2 Removendo uma folha
        System.out.println("\nRemovendo a pasta 'Joao' (Folha)...");
        String removido = fileSystem.remove(joao);
        System.out.println("Pasta removida: " + removido);
        System.out.println("Novo tamanho da árvore: " + fileSystem.size());

        // 5.3 Testando o 'nó excluido' (A invalidação parent == this)
        try {
            System.out.println("\nTentando acessar a pasta 'Joao' após ser deletada...");
            fileSystem.parent(joao); // Vai rodar o checkPosition
        } catch (IllegalArgumentException e) {
            System.out.println("Nó zumbi bloqueado com sucesso pelo checkPosition: " + e.getMessage());
        }

        System.out.println("\n=== TESTES FINALIZADOS COM SUCESSO! ===");
    }
}