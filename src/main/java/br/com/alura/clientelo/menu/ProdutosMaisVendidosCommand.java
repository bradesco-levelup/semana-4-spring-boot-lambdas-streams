package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProdutosMaisVendidosCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Map<String, Integer> produtosAgrupados = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getProduto, Collectors.summingInt(Pedido::getQuantidade)));

        produtosAgrupados.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3)
                .forEach(entry -> {
                    System.out.println("PRODUTO: " + entry.getKey());
                    System.out.println("QUANTIDADE: " + entry.getValue());
                    System.out.println();
                });
    }

    @Override
    public String getDescricao() {
        return "Produtos mais vendidos";
    }
}
