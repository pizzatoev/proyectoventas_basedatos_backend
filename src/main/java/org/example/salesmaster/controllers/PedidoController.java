package org.example.salesmaster.controllers;

import jakarta.validation.Valid;
import org.example.salesmaster.dtos.pedido.PedidoDTO;
import org.example.salesmaster.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    // localhost:8080/api/pedidos
    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO savedPedidoDTO = pedidoService.createPedido(pedidoDTO);
        return new ResponseEntity<>(savedPedidoDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // localhost:8080/api/pedidos/1
    @GetMapping("{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
        PedidoDTO pedidoDTO = pedidoService.getPedido(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    // localhost:8080/api/pedidos/1
    @PutMapping("{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id,
                                                    @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO updatedPedidoDTO = pedidoService.updatePedido(id, pedidoDTO);
        return ResponseEntity.ok(updatedPedidoDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.ok("Pedido eliminado exitosamente !!!.");
    }
}

