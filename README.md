# 🛒 Bar da Cecela - Documentação Completa

Este documento contém a documentação técnica do sistema de comandas, incluindo a estrutura, o fluxo e o detalhe de cada classe.

---

## 🚀 1. ARQUITETURA E VISÃO GERAL (Detalhes do Código)

O código segue o **Princípio da Responsabilidade Única (SRP)**, dividindo as tarefas em pacotes lógicos e focando na modularidade.

### 1.1. Estrutura de Pacotes

| Pacote | Conteúdo | Responsabilidade (Foco) |
| :--- | :--- | :--- |
| **`service`** | `FormatadorComandaService`, `ImpressaoService`, etc. | **Lógica de Negócio** (Formatação, I/O, Catálogo, Comanda). |
| **`model`** | `Comanda`, `Produto` | **Estruturas de Dados** (Os objetos do pedido). |
| **`ui`** | `MenuNovo` | **Interface com o Usuário** (Controle do fluxo de entrada de dados). |

---

## 🛠️ 2. DETALHE DAS CLASSES DE SERVIÇO (`service` Package)

### 2.1. Classe: `FormatadorComandaService` (Geração do Conteúdo TXT)

Responsável por todo o aspecto visual, alinhamento e cálculos do recibo, aderindo à **largura fixa de 32 caracteres**.

| Método | Função Principal | Detalhe da Lógica |
| :--- | :--- | :--- |
| **`salvarComandaTxt(...)`** | Orquestrador de Escrita | Utiliza `BufferedWriter` para escrita eficiente. Delega o cálculo do caminho ao `GerenciadorCaminhoArquivo`. |
| **`calcularValorTotalLinha(...)`** | Cálculo | Contém a regra de negócio: checa a flag **`isMeia`** para usar o preço correto (`precoMeia` ou `precoInteiro`). |
| **`centralizar(...)`** | Utilitário de Alinhamento | Calcula o *padding* para centralizar textos (`Mesa`, `TOTAL`) dentro da `LARGURA_TOTAL`. |

### 2.2. Classe: `GerenciadorCaminhoArquivo` (I/O)

Responsabilidade: **Gerenciamento de Estrutura de Diretórios e Nomes de Arquivo**.

| Método | Função Principal | Lógica de I/O |
| :--- | :--- | :--- |
| **`percorreCaminhoArquivoComanda(...)`** | Criação da Estrutura | Constrói o nome da pasta diária (`dd-MM`). Executa **`diretorio.mkdirs()`** para criar a pasta `comandas/dd-MM` se ela não existir. |

### 2.3. Classe: `ImpressaoService` (Comunicação ESC/POS)

Responsabilidade: **Tradução do TXT para a Impressora e Envio**.

| Método | Função Principal | Detalhe da Lógica |
| :--- | :--- | :--- |
| **`imprimirCupom(...)`** | Orquestrador de Impressão | Lê o TXT, insere comandos ESC/POS, ações de corte (`CUT`) e envia o *stream* de bytes para o dispositivo. |

### 2.4. Classe: `CategoriaProdutoServiceImpl` (Seleção de Produto)

| Método | Função Principal | Detalhe da Lógica |
| :--- | :--- | :--- |
| **`adicionarProdutosConsumidos(...)`**| Orquestrador de Seleção | Controla o fluxo de escolha: exibe categorias, filtra o produto, e chama `adicionarQuantidadeProduto`. |
| **`adicionarQuantidadeProduto(...)`**| Input de Quantidade | Se for Porção, solicita ao usuário se é **inteira (1) ou meia (2)** antes de pedir a quantidade. |

### 2.5. Classe: `CarregaListaProdutos` (Catálogo de Dados)

Responsabilidade: **Inicialização Estática do Cardápio**.

| Método | Retorno | Função |
| :--- | :--- | :--- |
| **`carregarCardapio()`** | `static List<Produto>` | **Carregamento Estático.** Inicializa e popula o catálogo completo do sistema, categorizado em PORÇÕES, BEBIDAS e OUTROS. |

### 2.6. Classe: `ComandaService` (Gerenciamento da Sessão)

Responsabilidade: **Controle do Ciclo de Vida Lógico da Comanda**.

| Método | Função Principal | Detalhe da Lógica |
| :--- | :--- | :--- |
| **`abrirComanda()`** | Input da Mesa | Solicita e retorna o número da mesa, iniciando a comanda. |
| **`fecharComanda(...)`** | Finalização | Exibe um resumo simples na tela, calcula o **Total** de forma básica e atualiza o `status` para `"FECHADA"`. |

---

## 📊 3. DICIONÁRIO DE DADOS (`model` Package)

Define a estrutura completa dos objetos que o sistema utiliza.

### 3.1. Classe: `Comanda`

| Atributo | Tipo | Detalhe |
| :--- | :--- | :--- |
| **`id`** | `int` | Identificador único da comanda (uso interno/DB). |
| **`mesa`** | `int` | O número da mesa do pedido. |
| **`status`**** | `String` | O estado atual da comanda ("ABERTA" ou "FECHADA"). |
| **`produtos`** | `List<Produto>` | A lista de todos os itens contidos nesta comanda. |
| **`subtotal`** | `Double` | O valor total dos produtos (não utilizado na impressão, mas presente no modelo). |

### 3.2. Classe: `Produto`

| Atributo | Tipo | Detalhe |
| :--- | :--- | :--- |
| **`id`** | `Integer` | Identificador único do produto no cardápio. |
| **`nomeProduto`** | `String` | Descrição do item. |
| **`precoInteiro`** | `double` | Preço da porção inteira. |
| **`precoMeia`** | `Double` | Preço da meia porção (pode ser `null` se não aplicável). |
| **`tipoProduto`** | `String` | Categoria do item ("PORCAO", "BEBIDA", "OUTROS"). |
| **`quantidade`** | `int` | Quantidade solicitada pelo cliente. |
| **`isMeia`** | `boolean` | Flag que define se o item será cobrado como meia porção. |

---

## 🖥️ 4. INTERFACE E INICIALIZAÇÃO

### 4.1. Classe: `Main`

Ponto de entrada do programa.

| Método | Função Principal | Detalhe |
| :--- | :--- | :--- |
| **`main(String[] args)`** | Loop Principal | Mantém o programa rodando em um loop `while (continuar)`. Carrega a lista de produtos e chama o `MenuNovo`. |

### 4.2. Classe: `MenuNovo` (Controlador de Fluxo)

| Atributo / Método | Detalhe | Função |
| :--- | :--- | :--- |
| **`MODO_TESTE`** | `static final Boolean` | **Flag de Configuração.** Se `true`, o sistema **salva o TXT, mas desabilita a impressão**. |
| **`exibirMenu(...)`** | Controlador de Sessão | Orquestra a abertura, adição de itens, fechamento, salvamento e impressão do cupom. |

---

## 🛠️ 2. GUIA PARA DESENVOLVEDORES (Build e Empacotamento)

Este guia é para quem possui o código-fonte, o JDK e o **Maven** instalados.

### 2.1. Comandos de Criação do Executável (Build)

Estes comandos são executados na pasta raiz do seu projeto (onde está o `pom.xml`) e são necessários para compilar o código e criar o arquivo JAR final.

| Comando | Onde Rodar | Função |
| :--- | :--- | :--- |
| **`mvn clean package`** | Na pasta raiz do projeto. | **Comando principal.** Compila o código-fonte e empacota tudo (incluindo as dependências) em um único **Uber-JAR** dentro da pasta `target/`. Este processo usa o `maven-shade-plugin`. |

### 2.2. Comandos de Execução

Estes comandos são usados para testar ou rodar a aplicação a partir do terminal.

| Comando | Onde Rodar | Função |
| :--- | :--- | :--- |
| **`java -jar mercearia-executavel.jar`** | Na pasta de execução (`target/`). | **Comando de inicialização.** A JVM lê e executa o código a partir da `Main-Class` definida no `pom.xml`. |
| **`Iniciar.bat`** | Execução por **dois cliques**. | Script de conveniência que contém o comando acima, permitindo que o cliente inicie o programa facilmente sem usar o CMD. |

