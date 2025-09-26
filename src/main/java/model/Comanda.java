package model;

import java.util.List;

public class Comanda {
    private int id;
    private int mesa;
    private String status; // "ABERTA" ou "FECHADA"
    private List<Produto> produtos;
    private Double subtotal;

    public Comanda() {
    }

    public Comanda(int id, int mesa) {
        this.id = id;
        this.mesa = mesa;
        this.status = "ABERTA";
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", mesa=" + mesa +
                ", status='" + status + '\'' +
                ", produtos=" + produtos +
                ", subtotal=" + subtotal +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
