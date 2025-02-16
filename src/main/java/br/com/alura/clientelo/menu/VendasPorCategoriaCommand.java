package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import br.com.alura.clientelo.utils.FormatUtils;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class VendasPorCategoriaCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Map<String, List<Pedido>> pedidosPorCategoria = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCategoria));

        pedidosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String categoria = entry.getKey();
                    List<Pedido> pedidos = entry.getValue();

                    int quantidadeVendida = pedidos.stream()
                            .mapToInt(Pedido::getQuantidade)
                            .sum();

                    BigDecimal montante = pedidos.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    System.out.println("CATEGORIA: " + categoria);
                    System.out.println("QUANTIDADE VENDIDA: " + quantidadeVendida);
                    System.out.println("MONTANTE: " + FormatUtils.formataParaReal(montante));
                    System.out.println();
                });
    }

    @Override
    public String getDescricao() {
        return "Vendas por categoria";
    }


}
