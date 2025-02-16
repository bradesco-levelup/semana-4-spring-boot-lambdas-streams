package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.ClienteLucrativo;
import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import br.com.alura.clientelo.utils.FormatUtils;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientesMaisLucrativosCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Map<String, List<Pedido>> pedidosPorCliente = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCliente));

        pedidosPorCliente.entrySet()
                .stream()
                .map(entry -> {
                    String cliente = entry.getKey();
                    List<Pedido> pedidos = entry.getValue();

                    int numeroDePedidos = pedidos.size();
                    BigDecimal montanteGasto = pedidos.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new ClienteLucrativo(cliente, numeroDePedidos, montanteGasto);
                })
                .sorted(Comparator.comparing(ClienteLucrativo::getMontanteGasto).reversed())
                .forEach(clienteRelatorio -> {
                    System.out.println("NOME: " + clienteRelatorio.getNome());
                    System.out.println("Nº DE PEDIDOS: " + clienteRelatorio.getNumeroDePedidos());
                    System.out.println("MONTANTE GASTO: " + FormatUtils.formataParaReal(clienteRelatorio.getMontanteGasto()));
                    System.out.println();
                });
    }

    @Override
    public String getDescricao() {
        return "Clientes mais lucrativos";
    }
}
