package service;

import model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CarregaListaProdutos {

    public static List<Produto> carregarCardapio() {
        List<Produto> produtos = new ArrayList<>();

        // PORÇÕES DISPONÍVEIS ↓
        produtos.add(new Produto(1, "Contra File (so carne)", 70.0, 50.0, "PORCAO"));
        produtos.add(new Produto(2, "Contra File c/fritas ou mandioca", 62.0, 45.0, "PORCAO"));
        produtos.add(new Produto(3, "File de frango c/fritas ou mandioca", 50.0, 35.0, "PORCAO"));
        produtos.add(new Produto(4, "Aneis de cebola", 40.0, 20.0, "PORCAO"));
        produtos.add(new Produto(5, "Batata frita simples", 30.0, 25.0, "PORCAO"));
        produtos.add(new Produto(6, "Batata frita especial", 40.0, 30.0, "PORCAO"));
        produtos.add(new Produto(7, "Calabresa ou linguica c/fritas ou mandioca", 50.0, 37.0, "PORCAO"));
        produtos.add(new Produto(8, "Frango a passarinho ao alho", 30.0, null, "PORCAO"));
        produtos.add(new Produto(9, "Trio mineiro", 55.0, 40.0, "PORCAO"));
        produtos.add(new Produto(10, "File de tilapia", 60.0, 45.0, "PORCAO"));
        produtos.add(new Produto(11, "File de tilapia c/fritas ou mandioca", 70.0, 50.0, "PORCAO"));
        produtos.add(new Produto(12, "Quarteto da Cecela", 60.0, 45.0, "PORCAO"));
        produtos.add(new Produto(13, "Torresmo c/fritas ou mandioca", 50.0, 37.0, "PORCAO"));
        produtos.add(new Produto(14, "Trem da casa", 55.0, 40.0, "PORCAO"));
        produtos.add(new Produto(15, "Potes (torresmo/ frango/ calabresa/ linguica)", 25.0, null, "PORCAO"));
        produtos.add(new Produto(16, "Acrescimo de queijo ou bacon", 5.0, null, "PORCAO"));


        // BEBIDAS DISPONÍVEIS ↓
        produtos.add(new Produto(17, "Agua", 2.50, null, "BEBIDA"));
        produtos.add(new Produto(18, "Agua com gas", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(19, "Tonica Antartica 350ml", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(20, "Tonica Schweppes 350ml", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(21, "Ice Tea 450ml", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(22, "Ice Tea 1,5L", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(23, "Del vale 1,5L", 6.0, null, "BEBIDA"));
        produtos.add(new Produto(24, "Kapo", 3.50, null, "BEBIDA"));
        produtos.add(new Produto(25, "Guaravita Copo", 2.0, null, "BEBIDA"));
        produtos.add(new Produto(26, "Gatorade", 6.50, null, "BEBIDA"));
        produtos.add(new Produto(27, "Refri 200ml", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(28, "Refri 350ml", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(29, "Refri 600ml", 6.0, null, "BEBIDA"));
        produtos.add(new Produto(30, "H2O Limao", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(31, "H2O Limoneto", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(32, "Sprite Lemon 600ml", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(33, "Coca Cola 1L (retornavel)", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(34, "Coca Cola 2L", 12.0, null, "BEBIDA"));
        produtos.add(new Produto(35, "Coca Cola 2L (retornavel)", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(36, "Coca Zero 2L (retornavel)", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(37, "Pepsi 1L (retornavel)", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(38, "Pepsi 2L ", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(39, "Fanta Uva 2L (retornavel)", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(40, "Fanta Laranja 2L (retornavel)", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(41, "Antarctica 1L (retornavel)", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(42, "Antarctica 2L", 11.0, null, "BEBIDA"));
        produtos.add(new Produto(43, "It Guarana/Laranja 2L ", 6.0, null, "BEBIDA"));
        produtos.add(new Produto(44, "Kuat 2L", 9.0, null, "BEBIDA"));
        produtos.add(new Produto(45, "Sprite 1,5L", 8.50, null, "BEBIDA"));
        produtos.add(new Produto(46, "Sprite 2L", 11.0, null, "BEBIDA"));
        produtos.add(new Produto(47, "Caipirinha", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(48, "Caipirissima", 12.0, null, "BEBIDA"));
        produtos.add(new Produto(49, "Skol Beats", 7.50, null, "BEBIDA"));
        produtos.add(new Produto(50, "Chop de vinho (Stempel)", 11.0, null, "BEBIDA"));
        produtos.add(new Produto(51, "Taca de vinho", 8.0, null, "BEBIDA"));

        // WHISKY/DESTILADOS DISPONÍVEIS ↓
        produtos.add(new Produto(52, "Red Label (Dose)", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(53, "White Horse (Dose)", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(54, "Pinga da roca", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(55, "Canelinha", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(56, "Conhaque (presidente)", 4.0, null, "BEBIDA"));
        produtos.add(new Produto(57, "Araci (Amarela)", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(58, "Vodka", 5.0, null, "BEBIDA"));
        produtos.add(new Produto(59, "Campari", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(60, "Pinga com mel", 3.0, null, "BEBIDA"));
        produtos.add(new Produto(61, "Catuaba", 4.0, null, "BEBIDA"));

        // ENERGÉTICOS DISPONÍVEIS ↓
        produtos.add(new Produto(62, "Monster", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(63, "Mango Loco", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(64, "Fusion 1L", 10.0, null, "BEBIDA"));
        produtos.add(new Produto(65, "Fusion 2L", 15.0, null, "BEBIDA"));

        // CERVEJAS LATÃO 473ML DISPONÍVEIS ↓
        produtos.add(new Produto(66, "Brahma 473ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(67, "Skol 473ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(68, "Amstel 473ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(69, "Petra 473ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(70, "Heineken 473ml", 8.0, null, "BEBIDA"));

        // CERVEJAS 600ML DISPONÍVEIS ↓
        produtos.add(new Produto(71, "Petra 600ml", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(72, "Skol 600ml", 8.0, null, "BEBIDA"));
        produtos.add(new Produto(73, "Brahma 600ml", 8.50, null, "BEBIDA"));
        produtos.add(new Produto(74, "Antartica Original 600ml", 11.0, null, "BEBIDA"));
        produtos.add(new Produto(75, "Amstel 600ml", 8.50, null, "BEBIDA"));
        produtos.add(new Produto(76, "Eisenbahn Unfiltered 600ml", 8.50, null, "BEBIDA"));

        // CERVEJAS 1L DISPONÍVEIS ↓
        produtos.add(new Produto(77, "Brahma 1L", 9.50, null, "BEBIDA"));
        produtos.add(new Produto(78, "Bohemia 1L", 9.50, null, "BEBIDA"));
        produtos.add(new Produto(79, "Budweiser 1L", 11.0, null, "BEBIDA"));
        produtos.add(new Produto(80, "Skol 1L", 9.50, null, "BEBIDA"));
        produtos.add(new Produto(81, "Antartica 1L", 8.50, null, "BEBIDA"));
        produtos.add(new Produto(82, "Petra 1L", 9.0, null, "BEBIDA"));
        produtos.add(new Produto(83, "Amstel 1L", 9.0, null, "BEBIDA"));

        // CERVEJAS ESPECIAIS DISPONÍVEIS ↓
        produtos.add(new Produto(84, "Caracu 350ml", 6.0, null, "BEBIDA"));
        produtos.add(new Produto(85, "Malzebier 350ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(86, "Heineken zero 350ml", 7.0, null, "BEBIDA"));
        produtos.add(new Produto(87, "Brahma zero 350ml", 5.50, null, "BEBIDA"));
        produtos.add(new Produto(88, "Stella 600ml", 13.0, null, "BEBIDA"));
        produtos.add(new Produto(89, "Spaten 600ml", 12.0, null, "BEBIDA"));
        produtos.add(new Produto(90, "Heineken 600ml", 13.0, null, "BEBIDA"));

        // CERVEJAS LONG NECK DISPONÍVEIS ↓
        produtos.add(new Produto(91, "Stella  Long Neck", 9.0, null, "BEBIDA"));
        produtos.add(new Produto(92, "Corona Long Neck", 9.0, null, "BEBIDA"));

        // OUTROS DISPONÍVEIS ↓
        produtos.add(new Produto(93, "Batata Mix", 3.0,null, "OUTROS"));
        produtos.add(new Produto(94, "Trident/Halls", 3.0,null, "OUTROS"));
        produtos.add(new Produto(95, "Trento", 3.0,null, "OUTROS"));
        produtos.add(new Produto(96, "Snickers", 4.0,null, "OUTROS"));
        produtos.add(new Produto(97, "Bala Fini", 1.0,null, "OUTROS"));
        produtos.add(new Produto(98, "Chiclete Terror Zone", 0.50,null, "OUTROS"));
        produtos.add(new Produto(99, "Chiclets", 0.25,null, "OUTROS"));
        produtos.add(new Produto(100, "Crock Bacon/Barbecue", 3.0,null, "OUTROS"));
        produtos.add(new Produto(101, "Biscoito Recheado", 5.0,null, "OUTROS"));

        return produtos;
    }
}
