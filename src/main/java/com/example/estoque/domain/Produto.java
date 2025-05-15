package com.example.estoque.domain;

import com.example.estoque.entity.ProdutoEntity;

public class Produto {

    private String nome;
    private String descricao;
    private Double preco;
    private Integer qtd;

    public Produto() {
    }



    public Produto(ProdutoEntity entity) {
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.qtd = entity.getQtd();
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }


    public Double getPreco() {
        return preco;
    }



    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}
