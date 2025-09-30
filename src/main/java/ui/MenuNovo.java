package ui;

import model.Comanda;
import model.Produto;
import service.CategoriaProdutoServiceImpl;
import service.ComandaService;

import java.util.ArrayList;
import java.util.List;

public class MenuNovo {

    public static void exibirMenu(List<Produto> produtosCadastrados) {
       //objetos necessarios
        ComandaService numeroComandaAberta = new ComandaService();
        CategoriaProdutoServiceImpl categorias = new CategoriaProdutoServiceImpl();
        Comanda comanda = new Comanda();
        List<Produto> listaConsumoDaComandaAserCriada = new ArrayList<>();

        //pedindo um numero pro usuario abrir comanda
        Integer numeroDaMesaAberta = numeroComandaAberta.abrirComanda();

        //montar a comanda
        comanda.setMesa(numeroDaMesaAberta);
        comanda.setStatus("ABERTA");

        //ADIÇÃO DE PRODUTOS INICIAL A COMANDA.
        Produto produtoAdicionado = categorias.adicionarProdutosConsumidos(produtosCadastrados);
        Produto produtoCopia = new Produto(produtoAdicionado); // cria uma cópia independente
        listaConsumoDaComandaAserCriada.add(produtoCopia);

        Boolean respostaUsuario = categorias.desejaAdicionarMaisProdutos();

        while (respostaUsuario) {
            Produto produtoSelecionado = categorias.adicionarProdutosConsumidos(produtosCadastrados);
            Produto novoProdutoCopia = new Produto(produtoSelecionado); // cria uma nova cópia a cada iteração
            listaConsumoDaComandaAserCriada.add(novoProdutoCopia);
            respostaUsuario = categorias.desejaAdicionarMaisProdutos();
        }

        comanda.setProdutos(listaConsumoDaComandaAserCriada);

        numeroComandaAberta.fecharComanda(comanda);

    }
}