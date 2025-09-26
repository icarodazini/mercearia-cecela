package service2;

import model.Comanda;
import model.Produto;

import java.util.Scanner;

public class ComandaService2 {
    Scanner scanner = new Scanner(System.in);

    public Integer abrirComanda() {
        System.out.println("Digite o número da mesa:");
        Integer numeroDigitado = scanner.nextInt();
        return numeroDigitado;
    }
    public Comanda fecharComanda(Comanda comanda) {
        System.out.println("Fechando comanda da mesa " + comanda.getMesa() + ":");
        double total = 0.0;
        for (Produto produto : comanda.getProdutos()) {
            double valorProduto = produto.getPrecoInteiro() * produto.getQuantidade();
            System.out.println(produto.getQuantidade() + " x  " + produto.getNomeProduto() + " | Preço: R$ " + valorProduto);
            total += valorProduto;
        }
        System.out.println("Valor total a pagar: R$ " + total);
        comanda.setStatus("FECHADA");
        return comanda;
    }

}
