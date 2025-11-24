package org.example.salesmaster.dtos.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long idPedido;
    
    @NotNull(message = "El idCliente es obligatorio")
    private Long idCliente;
    private String nombreCliente;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<PedidoProductoDTO> productos;
}

