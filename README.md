# IFRN - Estruturas de Dados (EDL e EDNL)

Este repositório contém a implementação de diversas Estruturas de Dados Lineares (EDL) e Não Lineares (EDNL).

## Sobre o Projeto e Histórico

O projeto iniciou como uma atividade em grupo sobre pilhas. Posteriormente, assumi a expansão do repositório individualmente para construir um catálogo completo de estruturas de dados utilizando boas práticas de engenharia de software.

## Boas Práticas e Arquitetura

Para garantir que o código seja flexível, seguro e reutilizável, as implementações incorporam:

*   **Generics (`<T>`):** Todas as estruturas foram refatoradas para utilizar *Generics*, garantindo segurança de tipos (Type Safety) e permitindo que as coleções armazenem qualquer tipo de objeto sem a necessidade de *casting* manual.
*   **Design Baseado em Interfaces:** O acoplamento do código foi reduzido através do uso de interfaces padronizadas.
    *   `Position<E>`: Utilizada para abstrair e encapsular o conceito de um "nó" dentro de estruturas como Árvores e Listas Posicionais, impedindo que o usuário manipule diretamente os ponteiros internos.
    *   `Entry<K, V>`: Implementada para o encapsulamento de pares Chave-Valor, essencial em abstrações de Mapas, Dicionários e Filas de Prioridade.

---

## Projetos

### 1. Pilha Rubro-Negra em Array Compartilhado (Trabalho Colaborativo)
Implementação de uma classe contendo duas pilhas independentes ("Vermelha" e "Preta") alocadas em um único array (`PilhaRubroNegraArray`). A pilha "vermelha" inicia no começo do array e a "preta" no final, crescendo em direções opostas.
*   **Gestão de Memória:** Estratégia de duplicação de array com tempo amortizado de $O(1)$ quando cheio, e redução pela metade quando atinge 1/3 de utilização.

### 2. Fila com Reversão em $O(1)$ (Trabalho Individual)
Desenvolvimento de uma Fila estruturada sobre um array circular (`QueueReverseArray`) que suporta a operação `reverse()` em tempo $O(1)$, sem necessitar de laços de repetição ou cópias de arrays. 
*   **Encadeamento Lógico:** Após a reversão, os métodos tradicionais de `enqueue` e `dequeue` continuam funcionando perfeitamente, adaptando-se instantaneamente ao novo início e fim da fila.

---

## Catálogo de Estruturas Implementadas

O repositório está organizado modularmente e conta com implementações utilizando dupla abordagem de alocação de memória: **sequencial (Arrays)** e **encadeada (Nós dinâmicos)**.

- [x] **Pilhas (`pilha`) e Filas (`fila`):** Estruturas LIFO e FIFO contemplando implementações com Arrays (`ArrayStack`, `ArrayQueue`) e Encadeamento (`LinkedQueue`).
- [x] **Vetores (`vetor`):** Implementados via array (`ArrayVector`) e nós (`LinkedVector`). 
    * O TAD Vetor extende a noção de arranjo (array) armazenando sequências de objetos arbitrários. 
    * Um elemento pode ser acessado, inserido ou removido através da especificação de sua colocação (rank).
- [x] **Listas (`lista`):** Implementadas via array (`ArrayList`) e nós posicionais (`LinkedPositionList`). 
    * O TAD Lista modela um sequência de posições armazenando objetos quaisquer. 
    * Ele estabelece uma relação antes/depois entre posções.
- [x] **Sequências (`sequencia`):** Implementada via nós (`LinkedSequence`). 
    * O TAD Sequencia é a união de Vetor e Lista.
- [x] **Árvores (`arvore`):** Hierarquias genéricas (`LinkedGenericTree`) e binárias (`LinkedBinaryTree`, `BinarySearchTree`).
- [x] **Filas de Prioridade e Heaps (`filaprioridade`, `heap`):** Implementações em array (`BinaryArrayHeap`) e nós (`BinaryLinkedHeap`).
- [ ] **Mapas e Tabelas Hash (`mapa`):** Implementação de dicionário/mapa através de `HashTableMap`. 
    * O TAD dicionário modela uma coleção "buscável" de itens chave-elemento. 
    * Uma tabela de dispersão para um dado tipo de chave consiste de função de dispersão h e arranjo (chamado tabela) de tamanho N.

## Exemplo de Uso (Generics & Interfaces)

Abaixo um breve exemplo de como a utilização de interfaces e Generics torna o consumo das estruturas mais limpo e seguro:

```java
// Instanciação utilizando Generics para garantir a tipagem
PositionList<String> list = new LinkedPositionList<>();

// O uso da interface Position oculta os ponteiros (next/prev) do nó real
Position<String> p1 = list.insertFirst("Estrutura");
Position<String> p2 = list.insertAfter(p1, "de");
list.insertAfter(p2, "Dados");

System.out.println(p1.element()); // Saída: Estrutura

```

## Como Executar

1. Clone este repositório:
```bash
git clone [https://github.com/alibruno/ifrn-EstruturaDeDados.git](https://github.com/alibruno/ifrn-EstruturaDeDados.git)

```

2. Abra o projeto na sua IDE de preferência (IntelliJ, Eclipse, VSCode).
3. Certifique-se de ter o **Java JDK 17+** instalado.
4. Execute as classes `Test...` localizadas dentro de cada pacote (ex: `TestList`, `TestBST`, `TestStack`) para visualizar o comportamento estrutural e os casos de teste.

---

## Referência Acadêmica

Toda a base teórica e de implementação destas estruturas foi fundamentada na literatura clássica da área:

> GOODRICH, M. T.; TAMASSIA, R. **Estruturas de dados e algoritmos em Java**. 4.ed. Porto Alegre: Bookman, 2007.