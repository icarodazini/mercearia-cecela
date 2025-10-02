package ui;

import model.Comanda;
import model.Produto;
import service2.CategoriaProdutoServiceImpl2;
import service2.ComandaService2;

import java.util.ArrayList;
import java.util.List;

public class MenuNovo {

    public static void exibirMenu(List<Produto> produtosCadastrados) {
        ComandaService2 numeroComandaAberta = new ComandaService2();
        CategoriaProdutoServiceImpl2 categorias = new CategoriaProdutoServiceImpl2();
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

        textoService.salvarComandaTxt(comandaFechada);

        //fazer o metodo final de IMPRIMIR A COMANDA
        //impressaoService.imprimir()

        String path = "C:\\Users\\Karine\\Documents\\mercearia-cecela\\test.txt";
        try {
            ImpressaoService.imprimirCupom(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }
}