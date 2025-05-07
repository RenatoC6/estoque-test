package com.example.estoque.service;

import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import com.example.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProdutoServiceUnitTest {

    private ProdutoRepository repository;
    private ProdutoService produtoService;


    @BeforeEach
    public void setup() {

        repository = Mockito.mock(ProdutoRepository.class);
        produtoService = new ProdutoService(repository);

    }

    // Dado, quando, e então

    // teste1: Teste de produto existente com atualização no banco de dados
    // dado um produto existente
    // Consultar no banco de dados por findByNome
    // salvar no banco de dados com sucesso

    @Test
    public void dadoUmProduto_quandoEleExiste_atualizarNoBancoDeDados(){
        when(repository.findByNome("dummy-value")).thenReturn(new ProdutoEntity());

        Produto produto = new Produto();
        produto.setNome("dummy-value");
        produtoService.cadastrarProduto(produto);
        verify(repository, times(1))
                .findByNome("dummy-value");
    }


    // teste2: Teste de produto existente com atualização de quantidade
        // dado um produto existente
        // Consultar no banco de dados como existente
        // atualizar a quantidade e salvar no banco de dados com sucesso

    @Test
    public void dadoUmProduto_quandoEleExiste_atualizarQtdeNoBancoDeDados(){
        when(repository.findByNome("dummy-value")).thenReturn(new ProdutoEntity());

        Produto produto = new Produto();
        produto.setNome("dummy-value");
        produto.setQtd(10);
        produtoService.cadastrarProduto(produto);
        assertEquals(10, produto.getQtd());

    }

    // teste3: Teste de produto inexistente com criação do produto
            // dado um produto inexistente
            // Quando ele não existe
            // salvar no banco de dados esse produto

    @Test
    public void dadoUmProduto_quandoEleNaoExiste_salvarNoBancoDeDados(){
        when(repository.findByNome("dummy-value")).thenReturn(null);

        Produto produto = new Produto();

        produtoService.cadastrarProduto(produto);

        verify(repository, times(1))
                .save(new ProdutoEntity());

    }


//Teste 4:


    @Test
    void encontrarTodos_DeveRetornarListaVazia_QuandoRepositorioVazio() {

        when(repository.findAll()).thenReturn(Collections.emptyList());


        List<Produto> resultado = produtoService.encontrarTodos();


        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }


    //Teste 5


    @Test
    void testEncontrarTodos_QuandoNaoHouverProdutos_DeveRetornarListaVazia() {

        when(repository.findAll()).thenReturn(List.of());

        List<Produto> produtos = produtoService.encontrarTodos();


        assertEquals(0, produtos.size());
        verify(repository, times(1)).findAll();
    }

}




