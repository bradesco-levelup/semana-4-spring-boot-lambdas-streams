package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientesMaisFieisCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Map<String, Long> clientesMaisFieis = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCliente, Collectors.counting()));

        clientesMaisFieis.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> {
                    System.out.println("Nº DE PEDIDOS: " + entry.getValue());
                    System.out.println("NOME: " + entry.getKey());
                    System.out.println();
                });
    }

    @Override
    public String getDescricao() {
        return "Clientes mais fieis";
    }
}
