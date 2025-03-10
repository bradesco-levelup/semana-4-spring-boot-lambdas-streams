package br.com.alura.clientelo.pedido;

import br.com.alura.clientelo.arquivo.ExtratorDeCsv;
import br.com.alura.clientelo.arquivo.ProcessadorDeArquivo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDePedidos {

    private static final List<Pedido> pedidos = new ArrayList<>();

    static {
        try {
            pedidos.addAll(new ProcessadorDeArquivo()
                    .processaArquivo("pedidos.csv", new ExtratorDeCsv()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar pedidos na inicialização!", e);
        }
    }

    public List<Pedido> listaTodos() {
        return pedidos;
    }
}
