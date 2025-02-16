package br.com.alura.clientelo.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ClienteLucrativo {

    private final String nome;
    private final int numeroDePedidos;
    private final BigDecimal montanteGasto;

}
