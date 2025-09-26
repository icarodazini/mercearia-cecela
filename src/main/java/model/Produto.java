package model;

public class Produto {
    private Integer id;
    private String nomeProduto;
    private Double precoInteiro;
    private Double precoMeia;
    private String tipoProduto;
    private Integer quantidade;

    public Produto(int id, String nomeProduto, Double precoInteiro, Double precoMeia, String tipoProduto) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoInteiro = precoInteiro;
        this.precoMeia = precoMeia;
        this.tipoProduto = tipoProduto;
    }
    public Produto(int id, String nomeProduto, Double precoInteiro) {
        this(id, nomeProduto, precoInteiro, null, null);
    }

    public boolean temMeia() {
        return precoMeia != null;
    }

    public Double getPreco(boolean meia) {
        if (meia && precoMeia != null) {
            return precoMeia;
        }
        return precoInteiro;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPrecoInteiro() {
        return precoInteiro;
    }

    public void setPrecoInteiro(Double precoInteiro) {
        this.precoInteiro = precoInteiro;
    }

    public Double getPrecoMeia() {
        return precoMeia;
    }


    public void setPrecoMeia(Double precoMeia) {
        this.precoMeia = precoMeia;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}
