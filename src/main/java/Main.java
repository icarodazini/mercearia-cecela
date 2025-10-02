import model.Produto;
import service.CarregaListaProdutos;
import service.ImpressaoService;
import ui.MenuNovo;

import javax.print.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Produto> listaprodutos = CarregaListaProdutos.carregarCardapio();

        MenuNovo.exibirMenu(listaprodutos);

    }
}
