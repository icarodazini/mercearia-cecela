package ui;

import model.Comanda;
import model.Produto;
import org.w3c.dom.Text;
import service.CategoriaProdutoServiceImpl;
import service.ComandaService;
import service.TextoService;

import java.util.ArrayList;
import java.util.List;

public class MenuNovo {

    public static void exibirMenu(List<Produto> produtosCadastrados) {
       //objetos necessarios
        ComandaService numeroComandaAberta = new ComandaService();
        CategoriaProdutoServiceImpl categorias = new CategoriaProdutoServiceImpl();
        Comanda comanda = new Comanda();
        List<Produto> listaConsumoDaComandaAserCriada = new ArrayList<>();
        TextoService   textoService = new TextoService();

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

        Comanda comandaFechada = numeroComandaAberta.fecharComanda(comanda);

        textoService.salvarComandaTxt(comandaFechada);

        //fazer o metodo final de IMPRIMIR A COMANDA
        //impressaoService.imprimir()
    }
}