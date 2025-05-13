package com.example.estoque.service;

import com.example.estoque.domain.ItemPedido;
import com.example.estoque.domain.Pedido;
import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import com.example.estoque.exception.ForaDeEstoqueException;
import com.example.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

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
        produto.setQtd(Integer.valueOf(10));
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

        verify(repository, times(1)).save(any(ProdutoEntity.class));


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


    // teste6: Teste de produto existente com busca pelo nome
    // dado um produto
    // quando ele existe
    // buscar pelo nome

    @Test
    public void dadoUmProduto_quandoEleExiste_buscarPeloNome(){
        when(repository.findByNome("dummy-value")).thenReturn(new ProdutoEntity());

        Produto produto = new Produto();
        produtoService.encontrarPorNome("dummy-value");
        verify(repository, times(1))
                .findByNome("dummy-value");
    }

    // TESTE GARANTIR QUE UM PRODUTO INEXISTENTE GERA UMA EXCEÇÃO
    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {

        ItemPedido item = new ItemPedido();
        item.setId(Long.valueOf(3L));
        item.setQtd(Integer.valueOf(1));

        Pedido pedido = new Pedido();
        pedido.setItens(List.of(item));

        when(repository.findById(Long.valueOf(3L))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> produtoService.atualizarEstoque(pedido));
    }


    //TESTE ATUALIZAR ESTOQUE DEVE LANÇAR EXCEÇÃO QUANDO A QUANTIDADE DE ITEM FOR MAIOR QUE O ESTOQUE
    @Test
    void atualizarEstoque_DeveLancarExcecao_QuandoQuantidadeItemMaiorQueEstoque() {

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(Long.valueOf(1L));
        produtoEntity.setQtd(Integer.valueOf(5));
        produtoEntity.setNome("Produto A");

        ItemPedido item = new ItemPedido();
        item.setId(Long.valueOf(1L));
        item.setQtd(Integer.valueOf(10));

        Pedido pedido = new Pedido();
        pedido.setItens(List.of(item));

        when(repository.findById(Long.valueOf(1L))).thenReturn(Optional.of(produtoEntity));


        ForaDeEstoqueException exception = assertThrows(
                ForaDeEstoqueException.class,
                () -> produtoService.atualizarEstoque(pedido)
        );

        assertEquals(
                "Produto Produto A possui apenas: 5 em estoque",
                exception.getMessage()
        );

        verify(repository, never()).save(any());
    }


    //TESTE ATUALIZAR ESTOQUE DEVE BUSCAR PELO ID CORRETO DO ITEM
    @Test
    void atualizarEstoque_DeveBuscarPeloIdCorretoDoItem() {
        Long idProduto = (Long) 123L;
        ItemPedido item = new ItemPedido();
        item.setId(idProduto);
        item.setQtd(Integer.valueOf(1));

        Pedido pedido = new Pedido();
        pedido.setItens(List.of(item));

        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(idProduto);
        produto.setQtd(Integer.valueOf(10));

        when(repository.findById(idProduto)).thenReturn(Optional.of(produto));

        produtoService.atualizarEstoque(pedido);

        verify(repository, times(1)).findById(idProduto);
    }

    //TESTE ATUALIZAR ESTOQUE DEVE ATUALIZAR O ESTOQUE QUANDO A QUANTIDADE FOR SUFICIENTE
    @Test
    void atualizarEstoque_DeveAtualizarEstoque_QuandoQuantidadeSuficiente() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(Long.valueOf(1L));
        produtoEntity.setQtd(Integer.valueOf(15));

        ItemPedido item = new ItemPedido();
        item.setId(Long.valueOf(1L));
        item.setQtd(Integer.valueOf(10));

        Pedido pedido = new Pedido();
        pedido.setItens(List.of(item));

        when(repository.findById(Long.valueOf(1L))).thenReturn(Optional.of(produtoEntity));

        produtoService.atualizarEstoque(pedido);

        assertEquals(5, produtoEntity.getQtd());
        verify(repository, times(1)).save(produtoEntity);
    }

}




