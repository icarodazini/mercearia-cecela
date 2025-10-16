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

            // --- COLUNA 1 ---
            Produto produto1 = bebidas.get(i);
            String coluna1 = formatarLinhaProduto(produto1, LARGURA_COLUNA, false);

            // --- COLUNA 1 ---
            String coluna2 = "";
            int indiceColuna2 = i + itensPorColuna;

            if (indiceColuna2 < totalItens) {
                Produto produto2 = bebidas.get(indiceColuna2);
                coluna2 = formatarLinhaProduto(produto2, LARGURA_COLUNA, false);
            } else {
                coluna2 = " ".repeat(LARGURA_COLUNA);
            }

            // --- COLUNA 1 ---
            String coluna3 = "";
            int indiceColuna3 = i + (2 * itensPorColuna);

            if (indiceColuna3 < totalItens) {
                Produto produto3 = bebidas.get(indiceColuna3);
                coluna3 = formatarLinhaProduto(produto3, LARGURA_COLUNA, true);
            }

            System.out.println(coluna1 + coluna2 + coluna3);
        }
    }

    private String formatarLinhaProduto(Produto produto, int larguraDesejada, boolean isUltimaColuna) {
        String precoStr = String.format("R$ %.2f", produto.getPrecoInteiro());
        String idStr = String.valueOf(produto.getId());
        String nomeOriginal = produto.getNomeProduto();

        final int TAMANHO_FIXO_SEM_NOME = idStr.length() + precoStr.length() + 8;

        int espacoDisponivelParaNome = larguraDesejada - TAMANHO_FIXO_SEM_NOME;

        String nomeDisplay;

        if (nomeOriginal.length() > espacoDisponivelParaNome) {
            int pontoDeCorte = Math.max(0, espacoDisponivelParaNome - 3);
            nomeDisplay = nomeOriginal.substring(0, pontoDeCorte).trim() + "...";
        } else {
            nomeDisplay = nomeOriginal;
        }

        String linhaDisplay = String.format("%s - %s - %s",
                idStr,
                nomeDisplay,
                precoStr
        );

        if (isUltimaColuna) {
            return linhaDisplay;
        }

        if (linhaDisplay.length() >= larguraDesejada) {
            return linhaDisplay.substring(0, larguraDesejada);
        }

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
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite 1 para SIM ou 0 para NAO.");
        }
        System.out.println("Finalizando pedido...");
        return false;
    }
}
