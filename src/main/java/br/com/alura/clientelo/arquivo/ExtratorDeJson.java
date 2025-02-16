package br.com.alura.clientelo.arquivo;

import br.com.alura.clientelo.pedido.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExtratorDeJson implements ExtratorDePedidos {

    @Override
    public List<PedidoArquivo> extraiPedidos(InputStream stream) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(stream, new TypeReference<List<PedidoArquivo>>() {});
    }
}
