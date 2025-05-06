package com.example.estoque.service;

import com.example.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProdutoServiceUnitTest {

    private ProdutoRepository repository;
    private ProdutoService produtoService;

    @BeforeEach
    public void setup() {
        System.out.println("Executando o before each");

        repository = Mockito.mock(ProdutoRepository.class);

        produtoService = new ProdutoService(repository);
    }

    // Dado, quando e então

    // teste1: Tentar cadastrar um produto que não existe no banco de dados.
    @Test
    public void dadoUmProduto_quandoEleNaoExiste_tentarCadastrar(){

    }

    // teste2: Setar a quantidade do pedido em um pedido existente.
    @Test
    public void dadoUmProduto_quandoEleExiste_setarQuantidade(){

    }

    // teste3: Testar a falha na comunicação com o BD (repository) ao criar produto.
    @Test
    public void dadoUmProduto_quandoEleNaoExiste_gravarNoRepository(){

    }
}
