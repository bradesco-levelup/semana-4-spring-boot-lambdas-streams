package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import br.com.alura.clientelo.utils.FormatUtils;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProdutosMaisCarosPorCategoriaCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Map<String, Optional<Pedido>> produtosMaisCarosPorCategoria = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(
                        Pedido::getCategoria,
                        Collectors.maxBy(Comparator.comparing(Pedido::getPreco))
                ));

        produtosMaisCarosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String categoria = entry.getKey();
                    Pedido pedido = entry.getValue().orElseThrow();

                    System.out.println("CATEGORIA: " + categoria);
                    System.out.println("PRODUTO: " + pedido.getProduto());
                    System.out.println("PREÇO: " + FormatUtils.formataParaReal(pedido.getPreco()));
                    System.out.println();
                });
    }

    @Override
    public String getDescricao() {
        return "Produtos mais caros por categoria";
    }
}
