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

public class FormatadorComandaService {

    private static final int LARGURA_TOTAL = 32;
    private static final int ESPACO_FIXO_PRODUTO = 6;
    private static final String PREFIXO_PRECO = "R$ ";
    private static final String NOME_EMPRESA = "BAR DA CECELA";
    private static final String FORMATO_DATA_HORA = "dd/MM/yyyy_HH:mm:ss";
    private static final DecimalFormat FORMATO_DECIMAL;
    private static final String PREFIXO_MEIA = " (MEIA) ";

    static {
        DecimalFormatSymbols simbolosBr = new DecimalFormatSymbols(new Locale("pt", "BR"));
        FORMATO_DECIMAL = new DecimalFormat("#,##0.00", simbolosBr);
    }

    public String salvarComandaTxt(Comanda comanda) {
        LocalDateTime dataAgora = LocalDateTime.now();
        String dataFormatada = dataAgora.format(DateTimeFormatter.ofPattern(FORMATO_DATA_HORA));
        GerenciadorCaminhoArquivo gerenciadorCaminhoArquivo = new GerenciadorCaminhoArquivo();

        String nomeArquivo = gerenciadorCaminhoArquivo.percorreCaminhoArquivoComanda(comanda, dataAgora);

        String pathCompleto = new File(nomeArquivo).getAbsolutePath();

        try (FileWriter fw = new FileWriter(nomeArquivo, true);
             BufferedWriter escritorBuffer = new BufferedWriter(fw)) {

            double totalGeral = 0.0;

            escritorBuffer.write(escreverCabecalho(comanda, LARGURA_TOTAL));

            for (Produto produto : comanda.getProdutos()) {
                double valorTotalLinha = calcularValorTotalLinha(produto);
                totalGeral += valorTotalLinha;
                escritorBuffer.write(formatarLinhaProduto(produto, valorTotalLinha));
            }

            escritorBuffer.write(escreverRodape(totalGeral, dataFormatada, LARGURA_TOTAL));

        } catch (Exception e) {
            System.out.println("Erro ao salvar comanda: " + e.getMessage());
        }
        return pathCompleto;
    }

    private double calcularValorTotalLinha(Produto produto) {
        if (produto.getIsMeia()) {
            if (produto.getPrecoMeia() != null) {
                return produto.getPrecoMeia() * produto.getQuantidade();
            }
            return 0.0;
        }
        return produto.getPrecoInteiro() * produto.getQuantidade();
    }

    private String formatarLinhaProduto(Produto produto, double valorTotalLinha) {
        if (produto.getIsMeia()) {
            String precoStrMeia = PREFIXO_PRECO + FORMATO_DECIMAL.format(valorTotalLinha);
            String prefixoQtdeMeia = produto.getQuantidade() + "x ";
            String nomeCompletoMeia = prefixoQtdeMeia + produto.getNomeProduto() + PREFIXO_MEIA;

            return nomeCompletoMeia + precoStrMeia + "\n";
        }

        String precoStr = PREFIXO_PRECO + FORMATO_DECIMAL.format(valorTotalLinha);
        String prefixoQtde = produto.getQuantidade() + "x ";

        String nomeCompleto = prefixoQtde + produto.getNomeProduto();
        String espacamento = " ".repeat(ESPACO_FIXO_PRODUTO);

        return nomeCompleto + espacamento + precoStr + "\n";
    }

    private String escreverCabecalho(Comanda comanda, int largura) {
        StringBuilder construtorString = new StringBuilder();
        construtorString.append(NOME_EMPRESA).append("\n");
        construtorString.append(centralizar("Mesa: " + comanda.getMesa(), largura)).append("\n");
        construtorString.append("-".repeat(largura)).append("\n");
        return construtorString.toString();
    }

    private String escreverRodape(double totalGeral, String dataFormatada, int largura) {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("-".repeat(largura)).append("\n");

        String totalStr = "TOTAL: R$ " + FORMATO_DECIMAL.format(totalGeral);
        construtorString.append(centralizar(totalStr, largura)).append("\n");

        String dataStr = "Data: " + dataFormatada;
        construtorString.append(centralizar(dataStr, largura)).append("\n");

        construtorString.append(centralizar("Obrigado e volte sempre!", largura)).append("\n");

        return construtorString.toString();
    }

    private String centralizar(String texto, int largura) {
        if (texto == null) texto = "";
        if (texto.length() >= largura) return texto.substring(0, largura);
        int preenchimento = largura - texto.length();
        int preenchimentoEsquerda = preenchimento / 2;
        return " ".repeat(preenchimentoEsquerda) + texto + " ".repeat(preenchimento - preenchimentoEsquerda);
    }
}