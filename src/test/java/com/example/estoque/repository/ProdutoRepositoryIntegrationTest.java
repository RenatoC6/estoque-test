package com.example.estoque.repository;

import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProdutoRepositoryIntegrationTest {

    @Autowired
    private ProdutoRepository repository;

    @Test
    public void dadoProdutoExiste_quandoFindByNome_entaoAchouProduto() {
        //var nome = RandomStringUtils.randomNumeric(15);
        var produto = new ProdutoEntity();
        produto.setNome("Jose");
        produto.setDescricao("xxxxxx");
        produto.setPreco(10.0);
        produto.setQtd(5);
        repository.save(produto);

        var found = repository.findByNome("jose");
        Assertions.assertNotNull(found);

    }
}
