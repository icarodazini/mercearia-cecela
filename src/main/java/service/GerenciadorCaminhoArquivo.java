package service;

import model.Comanda;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerenciadorCaminhoArquivo {

    public String percorreCaminhoArquivoComanda(Comanda comanda, LocalDateTime dataAgora) {
        DateTimeFormatter formatoPastaDoDia = DateTimeFormatter.ofPattern("dd-MM");
        DateTimeFormatter formatoNomeArquivo = DateTimeFormatter.ofPattern("HH-mm");

        String nomePasta = dataAgora.format(formatoPastaDoDia);

        String nomeArquivo = "MESA-" + comanda.getMesa() + "H" + dataAgora.format(formatoNomeArquivo) + ".txt";

        String caminhoDiretorio = "comandas" + File.separator + nomePasta;

        File diretorio = new File(caminhoDiretorio);

        if (!diretorio.exists()) {
            if (!diretorio.mkdirs()) {
                System.out.println("Falha ao criar diretório: " + caminhoDiretorio);
            }
        }
        return caminhoDiretorio + File.separator + nomeArquivo;
    }
}
