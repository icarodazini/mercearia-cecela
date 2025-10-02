package service;

import model.Comanda;
import model.Produto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextoService {

    public String salvarComandaTxt(Comanda comanda) {
        final int LARGURA_TOTAL = 31; // Define a largura total da linha

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatando = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss");
        String dataFormatada = now.format(formatando);

        String nomeArquivo = "comanda_mesa_" + comanda.getMesa() + ".txt";
        String pathCompleto = new File(nomeArquivo).getAbsolutePath();

        DecimalFormatSymbols simbolosBr = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatoDecimal = new DecimalFormat("#,##0.00", simbolosBr);

        try (FileWriter fw = new FileWriter(nomeArquivo, true);
             BufferedWriter writer = new BufferedWriter(fw)) {

            writer.write("BAR DA CECELA\n");
            writer.write(centralizar("Mesa: " + comanda.getMesa(), LARGURA_TOTAL) + "\n"); // Mesa centralizada de acordo com a largura total
            writer.write("-".repeat(LARGURA_TOTAL) + "\n");

            double total = 0.0;
            for (Produto produto : comanda.getProdutos()) {

                double valorTotalLinha = produto.getIsMeia()
                        ? (produto.getPrecoMeia() != null ? produto.getPrecoMeia() * produto.getQuantidade() : 0.0)
                        : produto.getPrecoInteiro() * produto.getQuantidade();

                total += valorTotalLinha;

                // Formata a linha do produto
                writer.write(formatarLinhaProduto(produto, valorTotalLinha, formatoDecimal));
            }

            writer.write("-".repeat(LARGURA_TOTAL) + "\n");

            // Centraliza o rodapé
            String totalStr = "TOTAL: R$ " + formatoDecimal.format(total);
            writer.write(centralizar(totalStr, LARGURA_TOTAL) + "\n");

            String dataStr = "Data: " + dataFormatada;
            writer.write(centralizar(dataStr, LARGURA_TOTAL) + "\n");

            writer.write(centralizar("Obrigado e volte sempre!", LARGURA_TOTAL) + "\n");

        } catch (Exception e) {
            System.out.println("Erro ao salvar comanda: " + e.getMessage());
        }
        return pathCompleto;
    }

    private String formatarLinhaProduto(Produto produto, double valorTotalLinha, DecimalFormat formatoDecimal) {

        final String PREFIXO_DE_PRECO = "R$ ";
        final int ESPACO_LARGURA = 6; // Espaçamento

        String precoStr = PREFIXO_DE_PRECO + formatoDecimal.format(valorTotalLinha);
        String prefixoQty = produto.getQuantidade() + "x ";

        String linhaDescricao = produto.getNomeProduto();
        String prefixoCompleto = prefixoQty + linhaDescricao;

        // Espaçamento fixo entre o nome do produto e preço
        String espacamento = " ".repeat(ESPACO_LARGURA);

        // Garante que a linha não exceda um certo comprimento
        return prefixoCompleto + espacamento + precoStr + "\n";
    }

    // Centraliza uma string ao centro.
    private String centralizar(String s, int n) {
        if (s == null) s = "";
        if (s.length() >= n) return s.substring(0, n);
        int padding = n - s.length();
        int padLeft = padding / 2;
        return " ".repeat(padLeft) + s + " ".repeat(padding - padLeft);
    }
}