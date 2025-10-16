package service;

import model.Produto;

import java.util.List;
import java.util.Scanner;

public class CategoriaProdutoServiceImpl {
    Scanner scannerGlobal = new Scanner(System.in);

    public Produto adicionarProdutosConsumidos(List<Produto> produtosCadastrados) {
        System.out.println("=== Menu de Categorias ===");
        System.out.println("1. BEBIDAS");
        System.out.println("2. PORÇÕES");
        System.out.println("3. OUTROS");

        Integer escolhaUsuario = Integer.parseInt(scannerGlobal.nextLine().trim());

        List<Produto> listaFiltradaProdutos = buscarProduto(produtosCadastrados, escolhaUsuario);

        Produto produtoSelecionado = selecionarProduto(listaFiltradaProdutos);

        adicionarQuantidadeProduto(produtoSelecionado);

        return produtoSelecionado;
    }

    public List<Produto> buscarProduto(List<Produto> produtosCadastrados, Integer idProduto) {

        if (idProduto == 1) {
            List<Produto> bebidas = produtosCadastrados
                    .stream()
                    .filter(produto -> produto.getTipoProduto().equalsIgnoreCase("BEBIDA"))
                    .toList();

            exibirBebidasEmTresColunas(bebidas);

            return bebidas;
        }

        if (idProduto == 2) {
            List<Produto> porcoes = produtosCadastrados
                    .stream()
                    .filter(produto -> produto.getTipoProduto().equalsIgnoreCase("PORCAO"))
                    .toList();

            porcoes.forEach(produto -> System.out.println(produto.getId() + " - " + produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro() + " / R$ " + produto.getPrecoMeia()));

            return porcoes;
        }

        if (idProduto == 3) {
            List<Produto> outros = produtosCadastrados
                    .stream()
                    .filter(produto -> produto.getTipoProduto().equalsIgnoreCase("OUTROS"))
                    .toList();

            outros.forEach(produto -> System.out.println(produto.getId() + " - " + produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro()));

            return outros;
        }
        return null;
    }

    public Produto selecionarProduto(List<Produto> produtosFiltrados) {
        System.out.println("Digite o ID do produto que deseja selecionar:");
        Integer idProdutoSelecionado = Integer.parseInt(scannerGlobal.nextLine().trim());

        Produto produtoEscolhido = produtosFiltrados
                .stream()
                .filter(produto -> produto.getId().equals(idProdutoSelecionado))
                .findFirst()
                .orElse(null);

        if (produtoEscolhido != null) {
            System.out.println("Você adicionou: " + produtoEscolhido.getNomeProduto() + " - R$ " + produtoEscolhido.getPrecoInteiro());
        } else {
            System.out.println("Produto não encontrado.");
        }
        return produtoEscolhido;
    }

    public Produto adicionarQuantidadeProduto(Produto produto) {
        if (produto.isPorcao()) {
            System.out.println("Você deseja a porção inteira ou meia? (DIGITE 1 PARA INTEIRA E 2 PARA MEIA)");
            Integer escolhaMeiaInteira = Integer.parseInt(scannerGlobal.nextLine().trim());
            if (escolhaMeiaInteira == 2) {
                System.out.println("Você escolheu a porção meia.");
                produto.setIsMeia(true);
            }
        }
        System.out.println("Digite a quantidade que deseja adicionar:");
        Integer quantidadeProduto = Integer.parseInt(scannerGlobal.nextLine().trim());

        produto.setQuantidade(quantidadeProduto);

        System.out.println("Você adicionou " + quantidadeProduto + "x " + produto.getNomeProduto() + " ao pedido.");

        return produto;
    }

    private void exibirBebidasEmTresColunas(List<Produto> bebidas) {
        final int LARGURA_COLUNA = 50;

        int totalItens = bebidas.size(); // 74
        int itensPorColuna = (int) Math.ceil((double) totalItens / 3); // 25

        for (int i = 0; i < itensPorColuna; i++) {

            Produto produto1 = bebidas.get(i);
            // Formata com padding (não é a última coluna)
            String linha1 = formatarLinhaProduto(produto1, LARGURA_COLUNA, false);

            String linha2 = "";
            int indiceColuna2 = i + itensPorColuna; // i + 25

            if (indiceColuna2 < totalItens) {
                Produto produto2 = bebidas.get(indiceColuna2);
                // Formata com padding (não é a última coluna)
                linha2 = formatarLinhaProduto(produto2, LARGURA_COLUNA, false);
            } else {
                // Se a coluna 2 acabou, preenche com espaços para alinhar a coluna 3 (se houver)
                linha2 = " ".repeat(LARGURA_COLUNA);
            }

            String linha3 = "";
            int indiceColuna3 = i + (2 * itensPorColuna); // i + 50

            if (indiceColuna3 < totalItens) {
                Produto produto3 = bebidas.get(indiceColuna3);
                // Formata sem padding (é a última coluna)
                linha3 = formatarLinhaProduto(produto3, LARGURA_COLUNA, true);
            }

            // Combina e imprime
            System.out.println(linha1 + linha2 + linha3);
        }
    }

    private String formatarLinhaProduto(Produto produto, int larguraDesejada, boolean isUltimaColuna) {
        // Usa o preço inteiro (bebidas não têm meia porção) formatado para 2 casas
        String precoStr = String.format("R$ %.2f", produto.getPrecoInteiro());
        String idStr = String.valueOf(produto.getId());
        String nomeOriginal = produto.getNomeProduto();

        // 1. Calcular o espaço TOTAL que o ID e o Preço ocupam
        // Ex: "ID" + " - " + "NOME" + " - " + "R$ 0.00"
        // Espaço fixo (ID, R$, espaços, hífens): ID(3) + " - " (3) + " - " (3) + Preco (7-8)
        // Usamos um valor seguro (8) para os separadores.
        final int TAMANHO_FIXO_SEM_NOME = idStr.length() + precoStr.length() + 8;

        int espacoDisponivelParaNome = larguraDesejada - TAMANHO_FIXO_SEM_NOME;

        String nomeDisplay;
        boolean nomeFoiTruncado = false;

        if (nomeOriginal.length() > espacoDisponivelParaNome) {
            // Se o nome é maior que o espaço disponível, trunca e adiciona "..."
            // Corta 3 caracteres para o "..."
            int pontoDeCorte = Math.max(0, espacoDisponivelParaNome - 3);
            nomeDisplay = nomeOriginal.substring(0, pontoDeCorte).trim() + "...";
            nomeFoiTruncado = true;
        } else {
            nomeDisplay = nomeOriginal;
        }

        // 2. Montar a Linha de Display
        String linhaDisplay = String.format("%s - %s - %s",
                idStr,
                nomeDisplay,
                precoStr
        );

        // 3. Aplicar o Alinhamento (Padding)
        if (isUltimaColuna) {
            // A última coluna não precisa de padding de alinhamento
            return linhaDisplay;
        }

        // Se não for a última coluna, garante que a linha tenha EXATAMENTE a largura desejada
        if (linhaDisplay.length() >= larguraDesejada) {
            // Se, por alguma razão, o cálculo acima falhou, trunca no limite da coluna
            return linhaDisplay.substring(0, larguraDesejada);
        }

        // Adiciona o padding (espaços) para alinhar a próxima coluna
        return linhaDisplay + " ".repeat(larguraDesejada - linhaDisplay.length());
    }

    public Boolean desejaAdicionarMaisProdutos() {
        System.out.println("Voce deseja adicionar mais algum produto? (Digite 1 para SIM ou 0 para NAO)");
        String respostaUsuario = scannerGlobal.nextLine().trim();

        try {
            Integer respostaInt = Integer.parseInt(respostaUsuario);

            if (respostaInt == 1) {
                return true;
            }
        }catch (NumberFormatException e){
            System.out.println("Entrada inválida. Por favor, digite 1 para SIM ou 0 para NAO.");
        }
        System.out.println("Finalizando pedido...");
        return false;
    }
}
