# DERIVADA SIMBÓLICA E GRADIENTE
---

## 📋 Disciplina
**Paradigmas de Linguagens de Programação**  
**Professor:** Pedro Sampaio  
**Universidade Federal de Pernambuco (UFPE)**

## 👥 Integrantes
- **André Vinicius dos Santos Silva** - avss4@cin.ufpe.br
- **José Otávio C. Maciel** - jocm@cin.ufpe.br
- **Mateus Torres** - mtc@cin.ufpe.br

---

## 🎯 Sobre o Projeto

Este projeto estende uma linguagem funcional implementada em Java com suporte a **cálculo diferencial simbólico**, especificamente:

1. **Derivada Simbólica** (`derive`): Calcula a derivada de uma expressão em relação a uma variável
2. **Gradiente** (`grad`): Calcula todas as derivadas parciais de uma função em relação a múltiplas variáveis

### Utilidade

Essas funcionalidades são fundamentais em:
- **Otimização Matemática**: Encontrar máximos e mínimos de funções
- **Machine Learning**: Algoritmos de gradiente descendente
- **Física**: Cálculo de taxas de variação
- **Engenharia**: Análise de sistemas dinâmicos
- **Cálculo Numérico**: Resolução de equações diferenciais

---

## 🆕 Principais Adições e Modificações

### 1. **Nova Sintaxe no Parser**

#### **Operador Gradiente**
```
grad expressão by [lista_variáveis]
```

#### **Operador Derivada** (já existente, melhorado)
```
derive expressão by variável
```

### 2. **Classes Implementadas**

#### **ExpGradiente** (`ExpGradiente.java`)
- **O que faz:** Representa a expressão `grad f by [x, y, z]`
- **Responsabilidade:** Coordena o cálculo de todas as derivadas parciais
- **Como funciona:** 
  - Itera sobre cada variável da lista
  - Chama `ExpDeriv` para calcular cada derivada parcial
  - Avalia os corpos das funções derivadas no ambiente atual
  - Retorna um `ValorVetor` com todos os resultados

#### **ValorVetor** (`ValorVetor.java`)
- **O que faz:** Armazena o resultado do gradiente como um vetor matemático
- **Formato de saída:** `<v1,v2,v3,...>` (notação matemática padrão)
- **Características:**
  - Implementa interface `Valor`
  - Valida que todos os elementos têm o mesmo tipo
  - Suporta qualquer número de dimensões

#### **TipoVetor** (`TipoVetor.java`)
- **O que faz:** Define o tipo de dados "Vetor" para o sistema de tipos
- **Utilidade:** Permite checagem de tipos em tempo de compilação
- **Integração:** Trabalha com `AmbienteCompilacao` para validar expressões

#### **Derivador** (`Derivador.java`)
- **O que faz:** Implementa as regras matemáticas de derivação simbólica
- **Regras suportadas:**
  - Derivada de constantes e variáveis
  - Regra da soma e subtração
  - Regra do produto (fundamental para polinômios)
  - Simplificação automática de expressões
- **Técnica:** Algoritmo recursivo que percorre a AST aplicando regras de cálculo

### 3. **Modificações no Parser (JavaCC)**

#### **Correção Crítica de Precedência**
- **Problema original:** Multiplicação tinha a mesma precedência que soma
- **Impacto:** Expressões como `3 * x + 2 * y` eram parseadas incorretamente como `((3*x)+2)*y`
- **Solução:** Criação de `PExpMult()` em nível separado na hierarquia

**Hierarquia Corrigida:**
```
PExpBinaria      → Igualdade (==), Aplicação
    ↓
PExpBinaria2     → Soma (+), Subtração (-)
    ↓
PExpBinaria3     → AND (&&)
    ↓
PExpMult         → Multiplicação (*) [NOVO NÍVEL]
    ↓
PExpUnaria       → grad, derive, if-then-else, let
```

## 📖 Exemplos de Uso

### Exemplo 1: Derivada Simples
```
let fun f x = x * x in 
derive f by x
```
**Resultado:** `fn x . 2 * x`

---

### Exemplo 2: Gradiente 2D
```
let var x = 2 in let var y = 3 in 
grad (x * y + x) by [x, y]
```
**Resultado:** `<4,2>`

**Cálculo:**
- ∂f/∂x = y + 1 → 3 + 1 = 4
- ∂f/∂y = x → 2

---

### Exemplo 3: Gradiente 3D
```
let var x = 1 in let var y = 1 in let var z = 1 in 
grad (x * x + y * y + z * z) by [x, y, z]
```
**Resultado:** `<2,2,2>`

**Interpretação:** Gradiente de uma esfera, aponta radialmente para fora

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- **Java JDK 8** ou superior
- **Maven 3.6** ou superior
- **JavaCC** (gerenciado pelo Maven)

### Compilação

```bash
# Na pasta raiz do projeto
mvn clean install
```

Este comando irá:
1. Limpar builds anteriores
2. Gerar o parser a partir do arquivo `.jj` usando JavaCC
3. Compilar todas as classes Java
4. Executar os testes automatizados

### Executar Testes

```bash
# Rodar todos os testes
mvn test

# Rodar um teste específico
mvn test -Dtest=FunctionalTest#testGradiente
```

### Executar com Arquivo de Entrada

```bash
# Na pasta do módulo Funcional2
cd Funcional2

# Executar com arquivo
mvn exec:java -Dexec.args="input"
```

**Estrutura do arquivo `input`:**
```
let var x = 2 in let var y = 3 in 
grad (x * y + x) by [x, y]
```

### Executar Interativamente

```bash
# Ler da entrada padrão
mvn exec:java

# Digite o código e pressione Ctrl+D (Linux/Mac) ou Ctrl+Z (Windows)
```

### Via Linha de Comando Direta

```bash
# Compilar primeiro
mvn compile

# Executar
java -cp target/classes lf2.plp.functional2.parser.Func2Parser input
```

---

## 📊 Estrutura do Projeto

```
Funcional2/
├── src/
│   ├── lf2/plp/functional2/
│   │   ├── expression/
│   │   │   ├── ExpGradiente.java      ← Nova classe
│   │   │   ├── ExpDeriv.java          ← Existente, usado pelo gradiente
│   │   │   ├── ValorVetor.java        ← Nova classe
│   │   │   └── ValorFuncao.java
│   │   ├── util/
│   │   │   ├── Derivador.java         ← Regras de derivação
│   │   │   └── TipoVetor.java         ← Novo tipo
│   │   └── parser/
│   │       └── Func2Parser.jj         ← Parser JavaCC (modificado)
│   └── test/java/
│       └── FunctionalTest.java        ← Testes do gradiente
├── input                               ← Arquivo de exemplo
├── input2                              ← Outro exemplo
└── pom.xml                             ← Configuração Maven
```

---

**Executar todos:**
```bash
mvn test
```

---

## ⚠️ Limitações Conhecidas

### O que NÃO é suportado:

❌ **Divisão**: `grad (x / y) by [x]`  
❌ **Potenciação**: `grad (x ^ 3) by [x]`  
❌ **Funções transcendentais**: `sin, cos, ln, exp`  
❌ **Composição de funções**: `grad (f(g(x))) by [x]`  
❌ **Derivadas de ordem superior**: `grad(grad(...))`  

### O que funciona perfeitamente:

✅ **Polinômios** de qualquer grau (via multiplicações repetidas)  
✅ **Soma, subtração, multiplicação**  
✅ **Qualquer número de dimensões** (1D, 2D, 3D, ..., nD)  
✅ **Constantes e variáveis**  
✅ **Expressões mistas** com múltiplas variáveis  

---

## 🔧 Tecnologias Utilizadas

- **Java 8+**: Linguagem base
- **JavaCC 7.0.13**: Gerador de parser
- **Maven 3.x**: Gerenciamento de dependências e build
- **JUnit 4.13**: Framework de testes

---

## 📚 Referências

- Stewart, James. *Calculus: Early Transcendentals*
- Aho, Alfred V. et al. *Compilers: Principles, Techniques, and Tools*
- Griewank, Andreas. *Evaluating Derivatives: Principles and Techniques of Algorithmic Differentiation*
- JavaCC Documentation: https://javacc.github.io/javacc/

---

## 📝 BNF Estendida

### Expressões Adicionadas

```bnf
Expressao ::= Valor
            | ExpUnaria 
            | ExpBinaria 
            | Id 
            | Derivada
            | Gradiente          ← NOVO

Derivada ::= "derive" Expressao "by" Id

Gradiente ::= "grad" Expressao "by" ListaVariaveis   ← NOVO

ListaVariaveis ::= "[" ListaId "]"                   ← NOVO

ListaId ::= Id 
          | Id "," ListaId

Tipo ::= "string" 
       | "int" 
       | "boolean" 
       | "deriv_expr"
       | "vetor"                                      ← NOVO
```

---

## 🎥 Link da Apresentação

[Inserir link da apresentação aqui]

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos na disciplina de Paradigmas de Linguagens de Programação da UFPE.

---

## 🤝 Contribuições

Contribuições foram feitas igualmente por todos os integrantes do grupo:
- Implementação do gradiente e correção de precedência
- Criação das classes de suporte (ValorVetor, TipoVetor)
- Desenvolvimento da suite de testes
- Documentação técnica

---

**Data de Conclusão:** Novembro 2025  
**Versão:** 1.0