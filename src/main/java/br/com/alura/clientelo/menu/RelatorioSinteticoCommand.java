package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import br.com.alura.clientelo.relatorio.RelatorioSintetico;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RelatorioSinteticoCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        RelatorioSintetico relatorioSintetico = RelatorioSintetico.geraRelatorio(repositorioDePedidos.listaTodos());
        relatorioSintetico.exibir();
    }

    @Override
    public String getDescricao() {
        return "Relatório sintético";
    }
}
