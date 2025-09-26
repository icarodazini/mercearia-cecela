package service;

import model.Produto;

import java.util.List;
import java.util.Scanner;

public class CategoriaProdutoServiceImpl implements CategoriaProdutoService {

    @Override
    public String exibirOpcoesMenuProdutos(Integer escolhaUsuario) {
        System.out.println("=== Menu de Categorias ===");
        System.out.println("1. BEBIDAS");
        System.out.println("2. PORÇÕES");
        System.out.println("3. OUTROS");
        System.out.println("4. PESQUISAR PRODUTO");

        if (escolhaUsuario > 0 && escolhaUsuario < 4) {
            return escolhaUsuario.toString();
        }

        if (escolhaUsuario == 4) {
            System.out.println("Digite o nome do produto que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String nomeProduto = scanner.nextLine().trim();
            scanner.close();

            return nomeProduto;
        }

        return null;
    }

    @Override
    public Produto buscarProduto(List<Produto> produtosCadastrados, String nomeProduto) {

        if (nomeProduto == "1") {
            produtosCadastrados.forEach(produto -> {
                if (produto.getTipoProduto().equalsIgnoreCase("BEBIDA")) {
                    System.out.println(produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro());
                }
            });
        }

        if (nomeProduto == "2") {
            produtosCadastrados.forEach(produto -> {
                if (produto.getTipoProduto().equalsIgnoreCase("PORCAO")) {
                    System.out.println(produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro() + produto.getPrecoMeia());
                }
            });
        }

        if (nomeProduto == "3") {
            produtosCadastrados.forEach(produto -> {
                if (produto.getTipoProduto().equalsIgnoreCase("OUTROS")) {
                    System.out.println(produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro());
                }
            });
        }

        if (nomeProduto != "1" && nomeProduto != "2" && nomeProduto != "3") {
            produtosCadastrados.forEach(produto -> {
                if (produto.getNomeProduto().contains(nomeProduto)) {
                    System.out.println(produto.getNomeProduto() + " - R$ " + produto.getPrecoInteiro());
                }
            });
        }
        return null;
    }
}
