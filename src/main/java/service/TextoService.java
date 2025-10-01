package service;

import model.Comanda;
import model.Produto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextoService {

    // NOTE: O valor de alinhamento 40 será usado diretamente nos métodos.

    public void salvarComandaTxt(Comanda comanda) {
        // Define a largura da linha de alinhamento
        final int TOTAL_WIDTH = 31;
        final String PRICE_PREFIX = "R$ ";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatando = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss");
        String dataFormatada = now.format(formatando);

        String nomeArquivo = "comanda_mesa_" + comanda.getMesa() + ".txt";

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        try (FileWriter fw = new FileWriter(nomeArquivo, true);
             BufferedWriter writer = new BufferedWriter(fw)) {

            // 1. NOME DA MERCEARIA (Alinhado à esquerda)
            writer.write("MERCEARIA DA CECELA\n");

            // 2. MESA (Centralizada na largura TOTAL_WIDTH)
            writer.write(center("Mesa: " + comanda.getMesa(), TOTAL_WIDTH) + "\n");
            writer.write("-".repeat(TOTAL_WIDTH) + "\n");

            double total = 0.0;
            for (Produto produto : comanda.getProdutos()) {

                double valorTotalLinha = produto.getIsMeia()
                        ? (produto.getPrecoMeia() != null ? produto.getPrecoMeia() * produto.getQuantidade() : 0.0)
                        : produto.getPrecoInteiro() * produto.getQuantidade();

                total += valorTotalLinha;

                // Formata a linha do produto
                writer.write(formatarLinhaProduto(produto, valorTotalLinha, df));
            }

            writer.write("-".repeat(TOTAL_WIDTH) + "\n");

            // RODAPÉ (Centralizado/Recuado)
            String totalStr = "TOTAL: R$ " + df.format(total);
            writer.write(center(totalStr, TOTAL_WIDTH) + "\n");

            String dataStr = "Data: " + dataFormatada;
            writer.write(center(dataStr, TOTAL_WIDTH) + "\n");

            writer.write(center("Obrigado e volte sempre!", TOTAL_WIDTH) + "\n");

        } catch (Exception e) {
            System.out.println("Erro ao salvar comanda: " + e.getMessage());
        }
    }

    /**
     * Formata a linha do produto com nome COMPLETO e espaçamento fixo de 6 caracteres.
     * O nome NUNCA será cortado, mesmo que a linha ultrapasse 40 caracteres.
     */
    private String formatarLinhaProduto(Produto produto, double valorTotalLinha, DecimalFormat df) {

        // Mantemos 40 e R$ para que os métodos possam usá-los internamente.
        final int TOTAL_WIDTH = 40;
        final String PRICE_PREFIX = "R$ ";
        final int SPACING_WIDTH = 6; // Espaçamento fixo desejado

        String precoStr = PRICE_PREFIX + df.format(valorTotalLinha);
        String prefixoQty = produto.getQuantidade() + "x ";

        String linhaDescricao = produto.getNomeProduto();
        String prefixoCompleto = prefixoQty + linhaDescricao;

        // O espaçamento agora é FIXO e não depende de cálculo de largura.
        String espacamento = " ".repeat(SPACING_WIDTH);

        // A linha final pode exceder 40 caracteres, mas não será cortada por esta lógica.
        return prefixoCompleto + espacamento + precoStr + "\n";
    }

    /** Alinha uma string ao centro. */
    private String center(String s, int n) {
        if (s == null) s = "";
        if (s.length() >= n) return s.substring(0, n);
        int padding = n - s.length();
        int padLeft = padding / 2;
        return " ".repeat(padLeft) + s + " ".repeat(padding - padLeft);
    }
}