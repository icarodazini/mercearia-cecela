import model.Produto;
import service.CarregaListaProdutos;
import ui.MenuNovo;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            try {
                List<Produto> listaProdutos = CarregaListaProdutos.carregarCardapio();

                if (listaProdutos == null || listaProdutos.isEmpty()) {
                    System.out.println("ERRO DE DADOS: A lista de produtos está vazia ou nula. Verifique o caminho do arquivo.");
                    continuar = false;
                    continue;
                }
                continuar = MenuNovo.exibirMenu(listaProdutos);


            } catch (Exception e) {
                System.err.println("Ocorreu um erro fatal na inicialização:");
                e.printStackTrace();
                continuar = false;
            }
        }
        System.out.println("\n--- PROGRAMA ENCERRADO ---");
        System.out.println("Pressione ENTER para fechar a janela...");

        new Scanner(System.in).nextLine();
    }
}
