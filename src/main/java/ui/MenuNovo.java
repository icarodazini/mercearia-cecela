package ui;

import model.Comanda;
import model.Produto;
import service.CategoriaProdutoServiceImpl;
import service.ComandaService;
import service.ImpressaoService;
import service.TextoService;

import javax.print.PrintException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuNovo {

    public static void exibirMenu(List<Produto> produtosCadastrados) {
        ComandaService numeroComandaAberta = new ComandaService();
        CategoriaProdutoServiceImpl categorias = new CategoriaProdutoServiceImpl();
        TextoService textoService = new TextoService();
        //ImpressaoService impressaoService = new ImpressaoService();
        Comanda comanda = new Comanda();
        List<Produto> listaConsumoDaComandaAserCriada = new ArrayList<>();

        //pedindo um numero pro usuario abrir comanda
        Integer numeroDaMesaAberta = numeroComandaAberta.abrirComanda();

        //montar a comanda
        comanda.setMesa(numeroDaMesaAberta);
        comanda.setStatus("ABERTA");

        //ADIÇÃO DE PRODUTOS INICIAL A COMANDA.
        Produto produtoAdicionado = categorias.adicionarProdutosConsumidos(produtosCadastrados);
        listaConsumoDaComandaAserCriada.add(produtoAdicionado);

        Boolean respostaUsuario = categorias.desejaAdicionarMaisProdutos();

        while (respostaUsuario) {
            Produto novoProdutoAdicionado = categorias.adicionarProdutosConsumidos(produtosCadastrados);
            listaConsumoDaComandaAserCriada.add(novoProdutoAdicionado);
            respostaUsuario = categorias.desejaAdicionarMaisProdutos();
        }

        comanda.setProdutos(listaConsumoDaComandaAserCriada);

        Comanda comandaFechada = numeroComandaAberta.fecharComanda(comanda);


        String path = textoService.salvarComandaTxt(comandaFechada);

        try {
            ImpressaoService.imprimirCupom(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }
}