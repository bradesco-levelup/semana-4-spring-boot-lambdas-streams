package br.com.alura.clientelo.arquivo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.util.List;

public class ExtratorDeXml implements ExtratorDePedidos {

    @Override
    public List<PedidoArquivo> extraiPedidos(InputStream stream) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        return xmlMapper.readValue(stream, new TypeReference<List<PedidoArquivo>>() {});
    }
}
