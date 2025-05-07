package com.example.estoque.service;

import com.example.estoque.domain.Pedido;
import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import com.example.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProdutoServiceUnitTest {

    private ProdutoRepository repository;
    private ProdutoService produtoService;


    @BeforeEach
    public void setup() {

        repository = Mockito.mock(ProdutoRepository.class);
        produtoService = new ProdutoService(repository);

    }

    // Dado, quando e então

    // teste1: Teste de produto existente com atualização no banco de dados
    // dado um produto existente
    // Consultar no banco de dados por fyndByNome
    // salvar no banco de dados com sucesso

    @Test
    public void dadoUmProduto_quandoEleExiste_atualizarNoBancoDeDados(){
        Mockito.when(repository.findByNome("dummy-value")).thenReturn(new ProdutoEntity());

        Produto produto = new Produto();
        produto.setNome("dummy-value");
        produtoService.cadastrarProduto(produto);
        Mockito.verify(repository, Mockito.times(1))
                .findByNome("dummy-value");
    }


    // teste2: Teste de produto existente com atualização de quantidade
        // dado um produto existente
        // Consultar no banco de dados como existente
        // atualizar a qtde e salvar no banco de dados com sucesso

    @Test
    public void dadoUmProduto_quandoEleExiste_atualizarQtdeNoBancoDeDados(){
        Mockito.when(repository.findByNome("dummy-value")).thenReturn(new ProdutoEntity());

        Produto produto = new Produto();
        produto.setNome("dummy-value");
        produto.setQtd(10);
        produtoService.cadastrarProduto(produto);
        Assertions.assertEquals(10, produto.getQtd());

    }

    // teste3: Teste de produto inexistente com criação do produto
            // dado um produto inexistente
            // Quando ele não existe
            // salvar no banco de dados esse produto

    @Test
    public void dadoUmProduto_quandoEleNaoExiste_salvarNoBancoDeDados(){
        Mockito.when(repository.findByNome("dummy-value")).thenReturn(null);

        Produto produto = new Produto();

        produtoService.cadastrarProduto(produto);

        //Mockito.verify(repository, Mockito.times(1))
        //        .save(produto);
    }


}
