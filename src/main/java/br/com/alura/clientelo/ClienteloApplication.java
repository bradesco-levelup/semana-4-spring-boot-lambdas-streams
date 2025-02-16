package br.com.alura.clientelo;

import br.com.alura.clientelo.menu.*;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.function.BiConsumer;

@SpringBootApplication
public class ClienteloApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ClienteloApplication.class);

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ClienteloApplication.class, args);
    }

    public void run(String... args) throws Exception {
        RepositorioDePedidos repositorioDePedidos = new RepositorioDePedidos();

        Map<Integer, FuncionalidadeCommand> opcoesDeMenu = new LinkedHashMap<>();
        opcoesDeMenu.put(1, new RelatorioSinteticoCommand(repositorioDePedidos));
        opcoesDeMenu.put(2, new ProdutosMaisVendidosCommand(repositorioDePedidos));
        opcoesDeMenu.put(3, new VendasPorCategoriaCommand(repositorioDePedidos));
        opcoesDeMenu.put(4, new ProdutosMaisCarosPorCategoriaCommand(repositorioDePedidos));
        opcoesDeMenu.put(5, new ClientesMaisFieisCommand(repositorioDePedidos));
        opcoesDeMenu.put(6, new ClientesMaisLucrativosCommand(repositorioDePedidos));
        opcoesDeMenu.put(7, new CadastrarPedidoCommand(repositorioDePedidos));
        opcoesDeMenu.put(8, new ExcluirPedidoCommand(repositorioDePedidos));
        opcoesDeMenu.put(0, new SairCommand());

        int opcaoDoMenu = -1;
        while (opcaoDoMenu != 0) {
            opcaoDoMenu = pedeOpcaoDoMenu(opcoesDeMenu);

            FuncionalidadeCommand comando = opcoesDeMenu.getOrDefault(opcaoDoMenu, new OpcaoInvalidaCommand());
            comando.executa();

            System.out.println("========");
            System.out.println();
        }
    }

    private int pedeOpcaoDoMenu(Map<Integer, FuncionalidadeCommand> opcoesDeMenu) {
        BiConsumer<Integer, FuncionalidadeCommand> impressor = (key, value) ->
                System.out.println(key + " - " + value.getDescricao());

        opcoesDeMenu.forEach(impressor);

        int opcaoDoMenu;
        System.out.print("Digite a opção desejada: ");

        opcaoDoMenu = scanner.nextInt();
        scanner.nextLine();

        System.out.println();
        return opcaoDoMenu;
    }

}

