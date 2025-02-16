package br.com.alura.clientelo.arquivo;

import java.io.InputStream;
import java.util.List;

@FunctionalInterface
public interface ExtratorDePedidos {

    List<PedidoArquivo> extraiPedidos(InputStream stream) throws Exception;

}
