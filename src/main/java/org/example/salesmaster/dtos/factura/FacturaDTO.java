package org.example.salesmaster.dtos.factura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long idFactura;
    private Long idPedido;
    private String nro;
    private LocalDateTime fecha;
    private BigDecimal total;
}

