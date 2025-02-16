package br.com.alura.clientelo.arquivo;

import br.com.alura.clientelo.pedido.Pedido;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class ExtratorDeCsv implements ExtratorDePedidos {

    @Override
    public List<PedidoArquivo> extraiPedidos(InputStream stream) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            CsvToBean<PedidoArquivo> build = new CsvToBeanBuilder<PedidoArquivo>(reader)
                    .withSeparator(',')
                    .withType(PedidoArquivo.class)
                    .build();

            return build.parse();
        } catch (IOException e) {
            throw new Exception("Erro ao processar arquivo CSV!", e);
        }
    }
}
