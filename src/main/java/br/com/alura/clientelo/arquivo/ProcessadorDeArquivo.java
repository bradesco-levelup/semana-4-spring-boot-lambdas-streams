package br.com.alura.clientelo.arquivo;

import br.com.alura.clientelo.pedido.Pedido;

import java.io.InputStream;
import java.util.List;

public class ProcessadorDeArquivo {

    public List<Pedido> processaArquivo(String nomeDoArquivo, ExtratorDePedidos extrator) throws Exception {
        try (InputStream stream = ExtratorDeCsv.class.getClassLoader()
                .getResourceAsStream(nomeDoArquivo)) {

            return extrator.extraiPedidos(stream)
                    .stream()
                    .map(PedidoArquivo::toPedido)
                    .toList();
        }
    }
}
