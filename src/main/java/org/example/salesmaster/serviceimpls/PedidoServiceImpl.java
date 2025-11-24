package org.example.salesmaster.serviceimpls;

import org.example.salesmaster.dtos.pedido.PedidoDTO;
import org.example.salesmaster.dtos.pedido.PedidoProductoDTO;
import org.example.salesmaster.entities.Cliente;
import org.example.salesmaster.entities.Pedido;
import org.example.salesmaster.entities.PedidoProducto;
import org.example.salesmaster.entities.Producto;
import org.example.salesmaster.exceptions.ResourceNotFoundException;
import org.example.salesmaster.mappers.PedidoMapper;
import org.example.salesmaster.repositories.ClienteRepository;
import org.example.salesmaster.repositories.PedidoRepository;
import org.example.salesmaster.repositories.ProductoRepository;
import org.example.salesmaster.services.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
        // Validar que idCliente no sea null
        if (pedidoDTO.getIdCliente() == null) {
            throw new IllegalArgumentException("El idCliente es obligatorio y no puede ser null");
        }
        
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con id: " + pedidoDTO.getIdCliente()));

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(pedidoDTO.getFecha() != null ? pedidoDTO.getFecha() : LocalDateTime.now());

        // Agregar productos al pedido
        if (pedidoDTO.getProductos() != null && !pedidoDTO.getProductos().isEmpty()) {
            for (PedidoProductoDTO productoDTO : pedidoDTO.getProductos()) {
                Producto producto = productoRepository.findById(productoDTO.getIdProd())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Producto no encontrado con id: " + productoDTO.getIdProd()));

                pedido.agregarProducto(producto, productoDTO.getCantidad());
            }
        }

        // Calcular total
        pedido.calcularTotal();

        // Guardar pedido
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Actualizar IDs de las relaciones después de guardar
        for (PedidoProducto pp : savedPedido.getPedidoProductos()) {
            pp.getId().setIdPedido(savedPedido.getIdPedido());
        }
        pedidoRepository.save(savedPedido);

        return PedidoMapper.mapPedidoToPedidoDTO(savedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO updatePedido(Long pedidoId, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + pedidoId));

        // Actualizar cliente si cambió
        if (!pedido.getCliente().getIdCliente().equals(pedidoDTO.getIdCliente())) {
            Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cliente no encontrado con id: " + pedidoDTO.getIdCliente()));
            pedido.setCliente(cliente);
        }

        // Actualizar fecha
        if (pedidoDTO.getFecha() != null) {
            pedido.setFecha(pedidoDTO.getFecha());
        }

        // Limpiar productos existentes
        pedido.getPedidoProductos().clear();

        // Agregar nuevos productos
        if (pedidoDTO.getProductos() != null && !pedidoDTO.getProductos().isEmpty()) {
            for (PedidoProductoDTO productoDTO : pedidoDTO.getProductos()) {
                Producto producto = productoRepository.findById(productoDTO.getIdProd())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Producto no encontrado con id: " + productoDTO.getIdProd()));

                pedido.agregarProducto(producto, productoDTO.getCantidad());
            }
        }

        // Recalcular total
        pedido.calcularTotal();

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return PedidoMapper.mapPedidoToPedidoDTO(updatedPedido);
    }

    @Override
    @Transactional
    public String deletePedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + pedidoId));
        pedidoRepository.delete(pedido);
        return "Pedido ha sido eliminado";
    }

    @Override
    public PedidoDTO getPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + pedidoId));
        return PedidoMapper.mapPedidoToPedidoDTO(pedido);
    }

    @Override
    public List<PedidoDTO> getPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(PedidoMapper::mapPedidoToPedidoDTO)
                .collect(Collectors.toList());
    }
}

