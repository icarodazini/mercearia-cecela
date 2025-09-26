package service;

import model.Produto;

import java.util.List;

public interface CategoriaProdutoService {

    String exibirOpcoesMenuProdutos(Integer escolhaUsuario);

    Produto buscarProduto (List<Produto> produtosCadastrados, String nomeProduto);
}
