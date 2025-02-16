package br.com.alura.clientelo.relatorio;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.utils.FormatUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class RelatorioSintetico {

    private final int quantidadeDePedidos;
    private final int totalDeProdutosVendidos;
    private final BigDecimal montanteDeVendas;
    private final Pedido pedidoMaisBarato;
    private final Pedido pedidoMaisCaro;
    private final int totalDeCategorias;

    private RelatorioSintetico(int quantidadeDePedidos, int totalDeProdutosVendidos, BigDecimal montanteDeVendas, Pedido pedidoMaisBarato, Pedido pedidoMaisCaro, int totalDeCategorias) {
        this.quantidadeDePedidos = quantidadeDePedidos;
        this.totalDeProdutosVendidos = totalDeProdutosVendidos;
        this.montanteDeVendas = montanteDeVendas;
        this.pedidoMaisBarato = pedidoMaisBarato;
        this.pedidoMaisCaro = pedidoMaisCaro;
        this.totalDeCategorias = totalDeCategorias;
    }

    public static RelatorioSintetico geraRelatorio(List<Pedido> pedidos) {
        int totalDeProdutosVendidos = 0;
        BigDecimal montanteDeVendas = BigDecimal.ZERO;

        Pedido pedidoMaisBarato = null;
        Pedido pedidoMaisCaro = null;

        Set<String> categoriasProcessadas = new HashSet<>();

        for (Pedido pedidoAtual : pedidos) {
            if (pedidoMaisBarato == null || pedidoAtual.isMaisBaratoQue(pedidoMaisBarato)) {
                pedidoMaisBarato = pedidoAtual;
            } else if (pedidoMaisCaro == null || pedidoAtual.isMaisCaroQue(pedidoMaisCaro)) {
                pedidoMaisCaro = pedidoAtual;
            }

            montanteDeVendas = montanteDeVendas.add(pedidoAtual.getValorTotal());
            totalDeProdutosVendidos += pedidoAtual.getQuantidade();

            categoriasProcessadas.add(pedidoAtual.getCategoria());
        }

        return new RelatorioSintetico(
            pedidos.size(),
            totalDeProdutosVendidos,
            montanteDeVendas,
            pedidoMaisBarato,
            pedidoMaisCaro,
            categoriasProcessadas.size()
        );
    }

    public void exibir() {
        System.out.println("##### RELATÓRIO SINTÉTICO #####");
        System.out.println();

        System.out.println("TOTAL DE PEDIDOS REALIZADOS: " + quantidadeDePedidos);
        System.out.println("TOTAL DE PRODUTOS VENDIDOS: " + totalDeProdutosVendidos);
        System.out.println("TOTAL DE CATEGORIAS: " + totalDeCategorias);
        System.out.println();

        System.out.println("MONTANTE DE VENDAS: " + FormatUtils.formataParaReal(montanteDeVendas));
        System.out.println();

        System.out.println("PEDIDO MAIS BARATO: " + FormatUtils.formataParaReal(pedidoMaisBarato.getValorTotal()) + " (" + pedidoMaisBarato.getProduto() + ")");
        System.out.println("PEDIDO MAIS CARO: " + FormatUtils.formataParaReal(pedidoMaisCaro.getValorTotal()) + " (" + pedidoMaisCaro.getProduto() + ")");
        System.out.println();

        System.out.println("### FIM DO RELATÓRIO ###");
    }
}
