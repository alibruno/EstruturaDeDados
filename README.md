# IFRN - EDL e EDNL

---

## Livro base 

GOODRICH, M. T.; TAMASSIA, R. **Estruturas de dados e algoritmos em Java**. 4.ed. Porto Alegre: Bookman, 2007.

## Sumário

### 0. [TAD](#tad)

### 1. [Pilha](#pilha)

### 2. [Fila](#fila)

### 3. [Vetor](#vetor)

### 4. [Lista](#lista)

### 5. [Sequência](#sequência)

### 6. [Iteradores](#iteradores)

### 7. [Árvore](#árvore)

### 8. [Árvore Binária](#árvore-binária)

---

## TAD

### Tipos abstratos de dados (TADs)

Um TAD é uma abstração de uma estrutura de dados;

Um TAD especifica:

- Dados armazenados;
- Operações sobre os dados;
- Condições de erros associadas à operações.

**Exemplo**: TAD que modela um sistema de controle de estoque:

- Os dados são os pedidos de compra/venda;

- As operações suportadas são:
    - `comprar(produto, preço)`;
    - `vender(produto, preço)`;
    - `cancelar(pedido)`.

- Condições de erro:
    - Comprar/vender um produto não existente;
    - Cancelar um pedido não existente.

---

## Pilha

### Introdução

O TAD **Pilha** armazena objetos arbitrários;

Inserções e remoções seguem o esquema LIFO (Last In, First Out);

- Principais operações:
    - `push(Object o)`: insere um elemento;
    - `Object pop()`: remove e returna o último elemento inserido.

- Operações auxiliares:
    - `object top()`: retorna o último elemento inserido sem removê-lo;
    - `int size()`: retorna o número de elementos armazenados;
    - `boolean isEmpty()`: indica se há ou não elementos na Pilha.

### Implementações

#### [Array](src/pilha/PilhaArray.java)

### Atividades

#### [Pilha Rubro Negra](src/pilha/rubronegra/README.md)

---

## Fila

### Introdução

O TAD **Fila** armazena objetos arbitrários;

Inserções e remoções seguem o esquema FIFO (First In, First Out);

- Operações principais:
    - `enqueue(Object o)`: insere um elemento no fim da fila;
    - `Object dequeue()`: remove e retorna o elemento do início da fila.

- Operações auxiliares:
    - `Object first()`: retorna o elemento do início sem removê-lo;
    - `int size()`: retorna o número de elementos armazenados;
    - `boolean isEmpty()`: indica se há elementos na fila.

### Implementações

#### [Array circular](src/fila/FilaArray.java)

#### [Lista simplesmente encadeada](src/fila/FilaEncadeada.java)

### Atividades

#### [Reversão em O(1)](src/fila/reverse/README.md)

---

## Vetor

### Introdução

O TAD **Vetor** extende a noção de arranjo(array) armazenando sequências de objetos arbitrários;

Um elemento pode ser acessado, inserido ou removido através da especificação de sua colocação (rank).

Uma exceção é disparada se uma colocação incorreta é especificada.

- Principais operações:
    - `Object elemAtRank(int r)`: retorna o elemento na colocação r, sem removê-lo
    - `Object replaceAtRank(int r, Object o)`: substitui o elemento na colocação r por o e retorna o antigo elemento
    - `insertAtRank(int r, Object o)`: insere um novo elemento o na colocação r
    - `Object removeAtRank(int r)`: remove e retorna o elemento na colocação r

- Operações adicionais `size()` e `isEmpty()`

### Implementações

#### [Array circular](src/vetor/VectorArray.java)

#### [Lista duplamente encadeada](src/vetor/VectorLinked.java)

---

## Lista

### Introdução

O TAD **Lista** modela um sequência de posições armazenando objetos quaisquer;

Ele estabelece uma relação antes/depois entre posições.

- Métodos genéricos
    - `size()`;
    - `isEmpty()`.

- Métodos de fila:
    - `isFirst(n)`;
    - `isLast(n)`.

- Métodos para acessar:
    - `first()`;
    - `last()`;
    - `before(p)`;
    - `after(p)`.

- Métodos para atualizar:
    - `replaceElement(n, o)`;
    - `swapElements(n, q)`;
    - `insertBefore(n, o)`;
    - `insertAfter(n, o)`;
    - `insertFirst(o)`;
    - `insertLast(o)`;
    - `remove(n)`.

### Interfaces:

#### [List](src/lista/List.java)

#### [Position](src/lista/Position.java)

A interface `Position<E>` atua como uma abstração segura de um nó (`Node`) dentro de uma estrutura encadeada (como uma
Lista Duplamente Encadeada).

Em vez de usar um número inteiro (índice) para localizar um elemento, você usa uma "Posição". Essa posição sabe
exatamente onde ela está na memória e quem são seus vizinhos, mas ela esconde os ponteiros internos (next e prev) do
usuário da classe.

#### [PositionList](src/lista/PositionList.java)

É uma interface que define uma coleção de elementos onde o acesso e a navegação não são feitos por índices (como 0, 1,
2...), mas sim através das instâncias de `Position`.

Permite operações em `O(1)`.

### Implementações

#### [Array circular](src/lista/ArrayList.java)

#### [Lista duplamente encadeada](src/lista/LinkedPositionList.java)

---

## Sequência

### Introdução

O TAD Sequencia é a união de `Vetor` e `Lista`;

Elementos podem ser acessados por colocação ou posição;

- Métodos genéricos:
    - `int size()`;
    - `boolean isEmpty()`;
- Métodos de Vetor:
    - `Object elemAtRank(int r)`;
    - `Object replaceAtRank(int r, Object e)`;
    - `void insertAtRank(int r, Object e)`;
    - `Object removeAtRank(int r)`;
- Métodos de Lista:
    - `Node first()`;
    - `Node last()`;
    - `Node before(Node p)`;
    - `Node after(Node p)`;
    - `Object replaceElement(Node p, Object e)`;
    - `void swapElements(Node p, Node q)`;
    - `Node insertBefore(Node p, Object e)`;
    - `Node insertAfter(Node p, Object e)`;
    - `Node insertFirst(Object e)`;
    - `Node insertLast(Object e)`;
    - `Object remove(Node p)`;
- Métodos "ponte":
    - `Node atRank(int r)`: Retorna o Node daquela posição;
    - `int rankOf(Node p)`: Recebe o Node e retorna seu índice.

### Implementações

#### [Lista duplamente encadeada](src/sequencia/LinkedSequence.java)

---

## Iteradores

JAVA possui uma API específica para coleções de objetos;

- Vector e ArrayList são exemplos de implementações desta API;

Um iterador (iterator) abstrai o processo de percorrer uma coleção de elementos

Métodos do TAD **ObjectIterator**:
- `object object()`;
- `boolean hasNext()`;
- `object nextObject()`;
- `reset()`;

Extende o conceito de posição adicionando a capacidade de travessia

Implementação com array ou lista ligada

Um iterador é, tipicamente, associado com outra estrutura de dados

Podemos aumentar os TAD Pilha, Fila, Deque, Vetor, Lista e Sequencia com o método
- `ObjectIterator elements()`

Duas noções de iteradores:
- **Estático**: congela o conteúdo da estrutura de dados em um dado momento
- **Dinâmico**: segue as mudanças da estrutura de dados

### Implementações

#### [PositionList](src/lista/PositionList.java)
#### [LinkedPositionList (Impl de Lista duplamente encadeada - TAD lista)](src/lista/LinkedPositionList.java)

---

## Árvore

### O que é uma árvore
Em Computação, é um modelo abstrato de uma estrutura hierárquica;

Uma árvore consiste de nós com uma relação pai-filho.

### Terminologias da árvore

                A
         ______/|\______
        /       |       \
       B        C        D
      / \      / \
     E   F    G   H
        /|\
       I J K

- **Raiz (root)**: Nó sem pai (A);
- **Nó interno**: Nó com, pelo menos, um filho (A, B, C, F);
- **Nó externo**: Nó sem filhos (E, I, J, K, G, H, D);
- **Ancestral de um nó**: pai, avô, bisavô, etc.;
- **Profundidade de um nó**: Número de ancestrais;
- **Altura de um árvore**: Profundidade máxima (3);
- **Descendente de um nó**: filho, neto, bisneto, etc;
- **Sub-árvore**: árvore formada por um nó e seus descendentes.

### TAD Árvore

- Métodos genéricos:
  - `integer size()`: retorna o número de nós da árvore;
  - `integer height()`: Retorna a altura;
  - `boolean isEmpty()`: indica se a árvore é vazia;
  - `Iterator elements()`: retorna um iterador para os elementos da árvore;
  - `Iterator nos()`: retorna um iterador para os nós da árvore.
- Métodos de acesso:
  - `No root()`: retorna o nó raiz;
  - `No parent(No)`: retorna o nó pai de um nó;
  - `Iterator children(No)`: retorna um iterador para os filhos de um nó;
- Métodos de consulta:
  - `boolean isInternal(No)`: Verifica se o Nó é interno;
  - `boolean isExternal(No)`: Verifica se o Nó é externo ou folha;
  - `boolean isRoot(No)`: Verifica se o Nó é Raiz;
  - `integer depth(No)`: Retorna a profundidade de um No.
- Métodos de atualização:
  - `Object replace(No, o)`: Altera o objeto armazenado em um Nó.

### Como a árvore é estruturada

                               ┌───────────────┐
                               │   Node(A)     │
                               ├───────────────┤
                               │ element = A   │
                               │ parent  = ∅   │
                               │ children -----┼──────────────────────────────┐
                               └───────────────┘                              │
                                                                              │   
            ┌───────────────────────────────┬─────────────────────────────────┴─────────────────────────────┐
            │                               │                                                               │
    ┌───────▼───────┐               ┌───────▼───────┐                                                ┌──────▼──────┐
    │   Node(B)     │               │   Node(C)     │                                                │  Node(D)    │
    ├───────────────┤               ├───────────────┤                                                ├─────────────┤
    │ element = B   │               │ element = C   │                                                │ element = D │
    │ parent = @A   │               │ parent = @A   │                                                │ parent = @A │
    │ children=[]   │               │ children=[]   │                                                │ children=[] │
    └───────────────┘               └───────────────┘                                                └─────────────┘

### Interface

#### [Árvore](src/arvore/Tree.java)

### Implementações

#### [Estrutura encadeada](src/arvore/LinkedGenericTree.java)

---

## Árvore binária

Uma árvore binária é uma árvore com as seguintes propriedades:

- Cada nó interno tem, no máximo, dois filhos;
  - Arvore binária própria é aquela em que cada nó tem exatemente zero ou dois filhos;
- Os filhos de um nó é um par ordenado.

Chamamos os filhos de um nó de **filho da esquerda** e **filho da direita**;

Podemos, também, definir uma árvore binária recursivamente como:
- Uma árvore consistindo de um único nó;
- Uma árvore cuja raiz tem um par ordenado de filho, cada um dos quais é uma árvore binária.

### Interfaces

#### [Árvore Binária](src/arvore/binaria/BinaryTree.java)