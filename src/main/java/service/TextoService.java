package service;

import model.Comanda;
import model.Produto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextoService {

    public void salvarComandaTxt(Comanda comanda) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatando = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss");
        String dataFormatada = now.format(formatando);

        String nomeArquivo = "mesa_" + comanda.getMesa() + "_" + dataFormatada.replace("/", "-").replace(":", "-") + ".txt";

        try (FileWriter fw = new FileWriter(nomeArquivo, true);
             BufferedWriter writer = new BufferedWriter(fw)) {

            writer.write("MERCEARIA DA CECELA\n");
            writer.write("Mesa: " + comanda.getMesa() + "\n");
            writer.write("-------------------------------\n");

            for (Produto produto : comanda.getProdutos()) {
                String descricaoProduto = produto.getQuantidade() + "x " + produto.getNomeProduto();
                writer.write(formatarLinha(descricaoProduto, produto.getPrecoInteiro() * produto.getQuantidade()) + "\n");
            }

            writer.write("-------------------------------\n");
            writer.write("TOTAL: R$ " + comanda.getSubtotal() + "\n");
            writer.write("Data: " + dataFormatada + "\n");
            writer.write("Obrigado e volte sempre!\n");

        } catch (Exception e) {
            System.out.println("Erro ao salvar comanda: " + e.getMessage());
        }
    }

    private String formatarLinha(String descricao, double preco) {
        int larguraMaxima = 60; // Largura máxima para alinhar os preços
        String espacos = " . ".repeat(larguraMaxima - descricao.length());
        return descricao + espacos + "R$ " + String.format("%.2f", preco);
    }

}
