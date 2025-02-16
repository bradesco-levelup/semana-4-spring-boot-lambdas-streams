package br.com.alura.clientelo.arquivo;

import br.com.alura.clientelo.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PedidoArquivo {

    @CsvBindByName(column = "CATEGORIA")
    private String categoria;

    @CsvBindByName(column = "PRODUTO")
    private String produto;

    @CsvBindByName(column = "CLIENTE")
    private String cliente;

    @CsvBindByName(column = "PRECO")
    private BigDecimal preco;

    @CsvBindByName(column = "QUANTIDADE")
    private int quantidade;

    @CsvBindByName(column = "DATA")
    @CsvDate(value = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;


    public Pedido toPedido() {
        return new Pedido(categoria, produto, cliente, preco, quantidade, data);
    }
}
