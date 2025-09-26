package service2;

import java.util.Scanner;

public class ComandaService2 {
    Scanner scanner = new Scanner(System.in);

    public Integer abrirComanda() {
        System.out.println("Digite o número da mesa:");
        Integer numeroDigitado = scanner.nextInt();
        return numeroDigitado;
    }
}
