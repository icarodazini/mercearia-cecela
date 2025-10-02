
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
    private static final String ENCODING = "CP437";

    public static final byte[] INIT = new byte[]{0x1B, '@'};
    public static final byte[] ALIGN_LEFT = new byte[]{0x1B, 'a', 0};
    public static final byte[] ALIGN_CENTER = new byte[]{0x1B, 'a', 1};
    public static final byte[] ALIGN_RIGHT = new byte[]{0x1B, 'a', 2};
    public static final byte[] FONT_NORMAL = new byte[]{0x1B, '!', 0};
    public static final byte[] FONT_DOUBLE_HEIGHT = new byte[]{0x1B, '!', 0x01};
    public static final byte[] CUT = new byte[]{0x1D, 'V', 66, 0};


    public static void imprimirCupom(String caminhoArquivo) throws IOException, PrintException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(INIT);

        // 1. LER O ARQUIVO E ADAPTAR CADA LINHA
        processarArquivoParaImpressao(caminhoArquivo, output);

        // 2. Finalização
        output.write("\n\n\n".getBytes(ENCODING));
        output.write(CUT);

        // 3. Envia para a impressora (Parte inalterada)
        enviarParaImpressora(output);
    }
    private static String formatarLinhaItem(String produto, String preco) throws UnsupportedEncodingException {
        // Implementação da quebra de linha...
        int precoLength = preco.getBytes(ENCODING).length;
        int maxProductLengthForPriceLine = LARGURA_MAXIMA - precoLength - 1;

        if (maxProductLengthForPriceLine < 1) {
            return produto + "\n" + preencherCaracteres(' ', LARGURA_MAXIMA - precoLength) + preco;
        }

        List<String> productLines = new ArrayList<>();
        String currentProduct = produto;

        while (currentProduct.length() > maxProductLengthForPriceLine) {
            int cutPoint = LARGURA_MAXIMA;

            int lastSpace = currentProduct.substring(0, LARGURA_MAXIMA).lastIndexOf(' ');

            if (lastSpace > 0) {
                cutPoint = lastSpace;
            }

            String linePart = currentProduct.substring(0, cutPoint).trim();
            productLines.add(linePart);
            currentProduct = currentProduct.substring(cutPoint).trim();
        }

        if (!currentProduct.isEmpty()) {
            productLines.add(currentProduct);
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < productLines.size(); i++) {
            String currentLine = productLines.get(i);

            if (i < productLines.size() - 1) {
                output.append(currentLine).append('\n');
            } else {
                int espacos = LARGURA_MAXIMA - currentLine.length() - precoLength;
                output.append(currentLine).append(preencherCaracteres(' ', espacos)).append(preco);
            }
        }

        return output.toString();
    }

    private static String preencherCaracteres(char caractere, int num) {
        if (num <= 0) return "";
        return String.valueOf(caractere).repeat(num);
    }

    private static void processarArquivoParaImpressao(String caminhoArquivo, ByteArrayOutputStream output) throws IOException, UnsupportedEncodingException {
        final byte[] LINE_FEED = "\n".getBytes(ENCODING);
        boolean isHeader = true;
        boolean isItemSection = false;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(caminhoArquivo))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String upperCaseLine = line.toUpperCase();
                String trimmedLine = upperCaseLine.trim();

                if (trimmedLine.isEmpty()) {
                    output.write(LINE_FEED);
                    continue;
                }

                if (trimmedLine.startsWith("-") && trimmedLine.endsWith("-")) {
                    isHeader = false;
                    isItemSection = true;
                    output.write(ALIGN_LEFT);
                    output.write(preencherCaracteres('-', LARGURA_MAXIMA).getBytes(ENCODING));
                    output.write(LINE_FEED);
                    continue;
                }

                if (isItemSection && !trimmedLine.contains("TOTAL")) {
                    int lastR$Index = trimmedLine.lastIndexOf("R$");
                    if (lastR$Index != -1) {
                        String produto = trimmedLine.substring(0, lastR$Index).trim();
                        String preco = trimmedLine.substring(lastR$Index).trim();
                        output.write(FONT_NORMAL);
                        output.write(formatarLinhaItem(produto, preco).getBytes(ENCODING));
                        output.write(LINE_FEED);
                        continue;
                    }
                }

                if (trimmedLine.contains("TOTAL")) {
                    isItemSection = false;
                    output.write(ALIGN_RIGHT);
                    output.write(FONT_DOUBLE_HEIGHT);
                    output.write(trimmedLine.getBytes(ENCODING));
                    output.write(LINE_FEED);
                    output.write(LINE_FEED);
                    continue;
                }

                if (isHeader || lineNumber <= 3) {
                    output.write(ALIGN_CENTER);
                    output.write(lineNumber == 1 ? FONT_DOUBLE_HEIGHT : FONT_NORMAL);
                    output.write(trimmedLine.getBytes(ENCODING));
                    output.write(LINE_FEED);
                    continue;
                }

                output.write(ALIGN_CENTER);
                output.write(FONT_NORMAL);
                output.write(trimmedLine.getBytes(ENCODING));
                output.write(LINE_FEED);
            }
        }
    }

    private static void enviarParaImpressora(ByteArrayOutputStream output) throws PrintException {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService pos58 = null;
        for (PrintService s : services) {
            if (s.getName().toUpperCase().contains("POS-58")) {
                pos58 = s;
                break;
            }
        }

        if (pos58 == null) {
            throw new RuntimeException("Impressora POS-58 não encontrada! Verifique o nome.");
        }

        DocPrintJob job = pos58.createPrintJob();
        Doc doc = new SimpleDoc(output.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        job.print(doc, (PrintRequestAttributeSet) null);

        System.out.println("Cupom enviado para impressão!");
    }
}




