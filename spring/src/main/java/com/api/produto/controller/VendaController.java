package com.api.produto.controller;

import com.api.produto.controller.adapter.VendaControllerAdapter;
import com.api.produto.controller.dto.request.VendaRequest;
import com.api.produto.controller.dto.response.VendaResponse;
import com.api.produto.entity.ItemVenda;
import com.api.produto.entity.Produto;
import com.api.produto.entity.Venda;
import com.api.produto.integration.ClienteClient;
import com.api.produto.integration.dto.ClienteResponse;
import com.api.produto.repository.ProdutoRepository;
import com.api.produto.repository.VendaRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VendaController {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteClient clienteClient;

    public VendaController(VendaRepository vendaRepository, ProdutoRepository produtoRepository, ClienteClient clienteClient) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteClient = clienteClient;
    }

    @PostMapping("/produto/venda/cadastrar")
    public VendaResponse cadastrar(@RequestBody VendaRequest request) {
        // 1. Valida se o cliente existe através do Feign Client.
        validarCliente(request.clienteId());

        // 2. Busca detalhes de cada produto e monta os itens da venda
        List<ItemVenda> itensCalculados = new ArrayList<>();
        for (var itemReq : request.itens()) {
            Produto produto = produtoRepository.consultar(itemReq.produtoId());
            if (produto == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + itemReq.produtoId());
            }
            itensCalculados.add(new ItemVenda(
                    produto.id(),
                    produto.nome(),
                    itemReq.quantidade(),
                    produto.preco()
            ));
        }

        // 3. Cria a venda com totais calculados
        Venda venda = VendaControllerAdapter.castRequest(request, itensCalculados);
        Venda vendaSalva = vendaRepository.cadastrar(venda);
        return VendaControllerAdapter.castResponse(vendaSalva);
    }

    @GetMapping("/produto/venda/{id}")
    public VendaResponse consultar(@PathVariable String id) {
        Venda venda = vendaRepository.consultarPorId(id);
        if (venda == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada.");
        }
        return VendaControllerAdapter.castResponse(venda);
    }

    @GetMapping("/produto/venda/cliente/{clienteId}")
    public Collection<VendaResponse> listarPorCliente(@PathVariable String clienteId) {
        Collection<Venda> vendas = vendaRepository.consultarPorCliente(clienteId);
        return vendas.stream()
                .map(VendaControllerAdapter::castResponse)
                .collect(Collectors.toList());
    }

    private void validarCliente(String clienteId) {
        try {
            ClienteResponse cliente = clienteClient.consultarCliente(clienteId);
            if (cliente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
            }
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao se comunicar com a API de Cliente.");
        }
    }
}
