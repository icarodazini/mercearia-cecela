
package service;

import javax.print.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.print.attribute.PrintRequestAttributeSet;
import java.util.ArrayList;
import java.util.List;

public class ImpressaoService {

    // Largura máxima confirmada pelo usuário
    private static final int LARGURA_MAXIMA = 32;
    private static final String CODIFICACAO = "CP437";

    public static final byte[] INIT = new byte[]{0x1B, '@'};
    public static final byte[] ALIGN_LEFT = new byte[]{0x1B, 'a', 0};
    public static final byte[] ALIGN_CENTER = new byte[]{0x1B, 'a', 1};
    public static final byte[] ALIGN_RIGHT = new byte[]{0x1B, 'a', 2};
    public static final byte[] FONT_NORMAL = new byte[]{0x1B, '!', 0};
    public static final byte[] FONT_DOUBLE_HEIGHT = new byte[]{0x1B, '!', 0x01};
    public static final byte[] CUT = new byte[]{0x1D, 'V', 66, 0};


    public static void imprimirCupom(String caminhoArquivo) throws IOException, PrintException {

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        saida.write(INIT);

        // 1. LER O ARQUIVO E ADAPTAR CADA LINHA
        processarArquivoParaImpressao(caminhoArquivo, saida);

        // 2. Finalização
        saida.write("\n\n\n".getBytes(CODIFICACAO));
        saida.write(CUT);

        // 3. Envia para a impressora (Parte inalterada)
        enviarParaImpressora(saida);
    }

    private static String formatarLinhaProdutoPreco(String produto, String preco) throws UnsupportedEncodingException {
        int tamanhoPreco = preco.getBytes(CODIFICACAO).length;
        int maxTamanhoProdutoParaLinha = LARGURA_MAXIMA - tamanhoPreco - 1;

        if (maxTamanhoProdutoParaLinha < 1) {
            return produto + "\n" + preencherCaracteres(' ', LARGURA_MAXIMA - tamanhoPreco) + preco;
        }

        List<String> linhasProduto = new ArrayList<>();
        String produtoAtual = produto;

        while (produtoAtual.length() > maxTamanhoProdutoParaLinha) {
            int pontoDeCorte = LARGURA_MAXIMA;

            int ultimoEspaco = produtoAtual.substring(0, Math.min(LARGURA_MAXIMA, produtoAtual.length())).lastIndexOf(' ');

            if (ultimoEspaco > 0) {
                pontoDeCorte = ultimoEspaco;
            }

            String parteDaLinha = produtoAtual.substring(0, pontoDeCorte).trim();
            linhasProduto.add(parteDaLinha);
            produtoAtual = produtoAtual.substring(pontoDeCorte).trim();
        }

        if (!produtoAtual.isEmpty()) {
            linhasProduto.add(produtoAtual);
        }

        StringBuilder saidaLinha = new StringBuilder();

        for (int i = 0; i < linhasProduto.size(); i++) {
            String linhaAtual = linhasProduto.get(i);

            if (i < linhasProduto.size() - 1) {
                saidaLinha.append(linhaAtual).append('\n');
            } else {
                int espacos = LARGURA_MAXIMA - linhaAtual.length() - tamanhoPreco;
                saidaLinha.append(linhaAtual).append(preencherCaracteres(' ', espacos)).append(preco);
            }
        }

        return saidaLinha.toString();
    }

    private static String preencherCaracteres(char caractere, int num) {
        if (num <= 0) return "";
        return String.valueOf(caractere).repeat(num);
    }

    private static void processarArquivoParaImpressao(String caminhoArquivo, ByteArrayOutputStream output) throws IOException {
        final byte[] QUEBRA_LINHA = "\n".getBytes(CODIFICACAO);
        boolean ehCabecalho = true;
        boolean ehSecaoItens = false;

        try (BufferedReader leitor = Files.newBufferedReader(Path.of(caminhoArquivo))) {
            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                String linhaMaiuscula = linha.toUpperCase();
                String linhaFormatada = linhaMaiuscula.trim();

                if (linhaFormatada.isEmpty()) {
                    output.write(QUEBRA_LINHA);
                    continue;
                }

                if (linhaFormatada.startsWith("-") && linhaFormatada.endsWith("-")) {
                    ehCabecalho = false;
                    ehSecaoItens = true;
                    output.write(ALIGN_LEFT);
                    output.write(preencherCaracteres('-', LARGURA_MAXIMA).getBytes(CODIFICACAO));
                    output.write(QUEBRA_LINHA);
                    continue;
                }

                if (ehSecaoItens && !linhaFormatada.contains("TOTAL")) {
                    int ultimoIndiceR$ = linhaFormatada.lastIndexOf("R$");
                    if (ultimoIndiceR$ != -1) {
                        String produto = linhaFormatada.substring(0, ultimoIndiceR$).trim();
                        String preco = linhaFormatada.substring(ultimoIndiceR$).trim();
                        output.write(FONT_NORMAL);
                        output.write(formatarLinhaProdutoPreco(produto, preco).getBytes(CODIFICACAO));
                        output.write(QUEBRA_LINHA);
                        continue;
                    }
                }

                if (linhaFormatada.contains("TOTAL")) {
                    ehSecaoItens = false;
                    output.write(ALIGN_RIGHT);
                    output.write(FONT_DOUBLE_HEIGHT);
                    output.write(linhaFormatada.getBytes(CODIFICACAO));
                    output.write(QUEBRA_LINHA);
                    output.write(QUEBRA_LINHA);
                    continue;
                }

                if (ehCabecalho || numeroLinha <= 3) {
                    output.write(ALIGN_CENTER);
                    output.write(numeroLinha == 1 ? FONT_DOUBLE_HEIGHT : FONT_NORMAL);
                    output.write(linhaFormatada.getBytes(CODIFICACAO));
                    output.write(QUEBRA_LINHA);
                    continue;
                }

                output.write(ALIGN_CENTER);
                output.write(FONT_NORMAL);
                output.write(linhaFormatada.getBytes(CODIFICACAO));
                output.write(QUEBRA_LINHA);
            }
        }
    }

    private static void enviarParaImpressora(ByteArrayOutputStream saida) throws PrintException {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService impressoraPos58 = null;
        for (PrintService s : services) {
            if (s.getName().toUpperCase().contains("POS-58")) {
                impressoraPos58 = s;
                break;
            }
        }

        if (impressoraPos58 == null) {
            throw new RuntimeException("Impressora POS-58 não encontrada! Verifique o nome.");
        }

        DocPrintJob trabalhoImpressao = impressoraPos58.createPrintJob();
        Doc documento = new SimpleDoc(saida.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        trabalhoImpressao.print(documento, null);

        System.out.println("Cupom enviado para impressão!");
    }
}




