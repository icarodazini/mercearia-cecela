package service2;

import model.Comanda;
import model.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoriaProdutoServiceImpl2 {
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

            bebidas.forEach(produto -> System.out.println(produto.getId() + " - " + produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro()));

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

        System.out.println("Digite a quantidade que deseja adicionar:");
        Integer quantidadeProduto = Integer.parseInt(scannerGlobal.nextLine().trim());

        produto.setQuantidade(quantidadeProduto);

        System.out.println("Você adicionou " + quantidadeProduto + "x " + produto.getNomeProduto() + " ao pedido.");

        return produto;
    }

    /*
    public void salvarPedido(Comanda comanda) {
        System.out.println("MERCEARIA DA CECELA");
        System.out.println(comanda.getMesa());
        System.out.println("-------------------------------");
        for (Produto produto : comanda.getProduto()) {
            System.out.println(comanda.getProduto().getFirst().getNomeProduto() + " . . . . . R$ " + comanda.getProduto().getFirst().getPrecoInteiro());
        }
        System.out.println("-------------------------------");
        System.out.println("TOTAL:  R$ " + comanda.getSubtotal());
        System.out.println("Obrigado e volte sempre!");
    }
     */

    public Boolean desejaAdicionarMaisProdutos(){
        System.out.println("Voce deseja adicionar mais algum produto? (SIM/NAO)");
        String respostaUsuario = scannerGlobal.nextLine().trim().toUpperCase();

        if (respostaUsuario.equals("SIM")) {
            return true;
        }
        System.out.println("Finalizando pedido...");
        return false;
    }
}
