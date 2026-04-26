package com.api.produto.controller;

import com.api.produto.controller.adapter.ProdutoControllerAdapter;
import com.api.produto.controller.dto.request.ProdutoRequest;
import com.api.produto.controller.dto.response.ProdutoResponse;
import com.api.produto.entity.Produto;
import com.api.produto.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProdutoController {

    public final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/produto/cadastrar")
    public ProdutoResponse cadastrar(@RequestBody ProdutoRequest request) {
        Produto produto = ProdutoControllerAdapter.castRequest(request);
        Produto produtoSalvo = produtoRepository.cadastrar(produto);
        return ProdutoControllerAdapter.castResponse(produtoSalvo);
    }

    @GetMapping("/produto/{id}")
    public ProdutoResponse consultar(@PathVariable String id) {
        Produto produto = produtoRepository.consultar(id);
        return ProdutoControllerAdapter.castResponse(produto);
    }

    @PutMapping("/produto/atualizar")
    public ProdutoResponse atualizar(@RequestBody ProdutoRequest request) {
        Produto produto = ProdutoControllerAdapter.castRequest(request);
        Produto produtoAtualizado = produtoRepository.atualizar(produto);
        return ProdutoControllerAdapter.castResponse(produtoAtualizado);
    }

    @DeleteMapping("/produto/{id}")
    public String deletar(@PathVariable String id) {
        produtoRepository.deletar(id);
        return "Produto deletado com sucesso";
    }

    @GetMapping("/produto/listar")
    public java.util.Collection<ProdutoResponse> listar() {
        return produtoRepository.listar().stream()
                .map(ProdutoControllerAdapter::castResponse)
                .collect(java.util.stream.Collectors.toList());
    }

}
