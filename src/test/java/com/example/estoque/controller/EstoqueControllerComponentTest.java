package com.example.estoque.controller;

import com.example.estoque.domain.Pedido;
import com.example.estoque.domain.Produto;
import com.example.estoque.entity.ProdutoEntity;
import com.example.estoque.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class EstoqueControllerComponentTest {

    @MockitoBean
    private ProdutoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void dadoProdutoNovo_quando_TentoCadastrar_entaoCadastra() throws Exception {
        // Given
        var produto = new ProdutoEntity();
        //produto.setNome("dummy-value");

        // When
        var request = MockMvcRequestBuilders.post("/estoque")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                // objectMapper.writeValueAsString == escrever o objeto customerDto como Json
                .content(objectMapper.writeValueAsString(produto));
        var response = mockMvc.perform(request);

        // Then
        response.andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void atualizarEstoque_comMetodoPut_retornaMethodNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/estoque/atualizar"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }


    //Listar produtos com lista vazia, retornar lista vazia.

    @Test
    public void quandoListarProdutos_eNaoExistemProdutos_entaoRetornaListaVazia() throws Exception {
        when(service.encontrarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/estoque")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));
    }

    //Buscar o produto por nome, e retornar o produto quando existir.
    @Test
    public void quandoBuscarProdutoPorNome_eProdutoExiste_entaoRetornaProduto() throws Exception {
        when(service.encontrarPorNome("dummy-value")).thenReturn(new Produto());
        mockMvc.perform(MockMvcRequestBuilders.get("/estoque/dummy-value")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    //Buscar produto por nome e nao exister, entao retorna ok com body nulo.
    @Test
    public void quandoBuscarProdutoPorNome_eProdutoNaoExiste_entaoRetornaOkComBodyNulo() throws Exception {
        String nomeProdutoInexistente = "Produto Inexistente";
        when(service.encontrarPorNome(nomeProdutoInexistente)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/estoque/" + nomeProdutoInexistente)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").doesNotExist());
    }




    }




