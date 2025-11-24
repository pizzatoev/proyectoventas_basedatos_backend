package org.example.salesmaster.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoDTO {
    private Long idProd;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}

