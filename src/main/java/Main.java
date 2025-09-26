import model.Produto;
import service.CarregaListaProdutos;
import ui.MenuNovo;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Produto> listaprodutos = CarregaListaProdutos.carregarCardapio();

        MenuNovo.exibirMenu(listaprodutos);
    }
}