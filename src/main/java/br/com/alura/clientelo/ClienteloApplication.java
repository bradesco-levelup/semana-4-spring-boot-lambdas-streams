package br.com.alura.clientelo;

import br.com.alura.clientelo.pedido.ClienteLucrativo;
import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import br.com.alura.clientelo.relatorio.RelatorioSintetico;
import br.com.alura.clientelo.utils.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class ClienteloApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ClienteloApplication.class);

    private Scanner scanner = new Scanner(System.in);
    private RepositorioDePedidos repositorioDePedidos = new RepositorioDePedidos();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ClienteloApplication.class, args);
    }

    public void run(String... args) throws Exception {
        String menu = """
                1 - Relatório sintético
                2 - Produtos mais vendidos
                3 - Vendas por categoria
                4 - Produtos mais caros por categoria
                5 - Clientes mais fiéis
                6 - Clientes mais lucrativos
                7 - Cadastrar pedido
                8 - Excluir pedido
                0 - SAIR
                """;

        int opcaoDoMenu = -1;
        while (opcaoDoMenu != 0) {
            opcaoDoMenu = pedeOpcaoDoMenu(menu);

            switch (opcaoDoMenu) {
                case 1 -> exibeRelatorioSintetico();
                case 2 -> exibeProdutosMaisVendidos();
                case 3 -> exibeVendasPorCategoria();
                case 4 -> exibeProdutosMaisCarosPorCategoria();
                case 5 -> exibeClientesMaisFieis();
                case 6 -> exibeClientesMaisLucrativos();
                case 7 -> cadastrarPedido();
                case 8 -> excluirPedido();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println("========");
            System.out.println();
        }
    }

    private int pedeOpcaoDoMenu(String menu) {
        int opcaoDoMenu;
        System.out.println(menu);
        System.out.print("Digite a opção desejada: ");
        opcaoDoMenu = scanner.nextInt();
        scanner.nextLine();

        System.out.println();
        return opcaoDoMenu;
    }

    private void exibeRelatorioSintetico() {
        RelatorioSintetico relatorioSintetico = RelatorioSintetico.geraRelatorio(repositorioDePedidos.listaTodos());
        relatorioSintetico.exibir();
    }

    private void exibeProdutosMaisVendidos() {
        Map<String, Integer> produtosAgrupados = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getProduto, Collectors.summingInt(Pedido::getQuantidade)));

        produtosAgrupados.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3)
                .forEach(entry -> {
                    System.out.println("PRODUTO: " + entry.getKey());
                    System.out.println("QUANTIDADE: " + entry.getValue());
                    System.out.println();
                });


    }

    private void exibeVendasPorCategoria() {
        Map<String, List<Pedido>> pedidosPorCategoria = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCategoria));

        pedidosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String categoria = entry.getKey();
                    List<Pedido> pedidos = entry.getValue();

                    int quantidadeVendida = pedidos.stream()
                            .mapToInt(Pedido::getQuantidade)
                            .sum();

                    BigDecimal montante = pedidos.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    System.out.println("CATEGORIA: " + categoria);
                    System.out.println("QUANTIDADE VENDIDA: " + quantidadeVendida);
                    System.out.println("MONTANTE: " + FormatUtils.formataParaReal(montante));
                    System.out.println();
                });


    }

    private void exibeProdutosMaisCarosPorCategoria() {
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

    private void exibeClientesMaisFieis() {
        Map<String, Long> clientesMaisFieis = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCliente, Collectors.counting()));

        clientesMaisFieis.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> {
                    System.out.println("Nº DE PEDIDOS: " + entry.getValue());
                    System.out.println("NOME: " + entry.getKey());
                    System.out.println();
                });
    }

    private void exibeClientesMaisLucrativos() {
        Map<String, List<Pedido>> pedidosPorCliente = repositorioDePedidos.listaTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCliente));

        pedidosPorCliente.entrySet()
                .stream()
                .map(entry -> {
                    String cliente = entry.getKey();
                    List<Pedido> pedidos = entry.getValue();

                    int numeroDePedidos = pedidos.size();
                    BigDecimal montanteGasto = pedidos.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new ClienteLucrativo(cliente, numeroDePedidos, montanteGasto);
                })
                .sorted(Comparator.comparing(ClienteLucrativo::getMontanteGasto).reversed())
                .forEach(clienteRelatorio -> {
                    System.out.println("NOME: " + clienteRelatorio.getNome());
                    System.out.println("Nº DE PEDIDOS: " + clienteRelatorio.getNumeroDePedidos());
                    System.out.println("MONTANTE GASTO: " + FormatUtils.formataParaReal(clienteRelatorio.getMontanteGasto()));
                    System.out.println();
                });
    }

    private void cadastrarPedido() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Produto: ");
        String produto = scanner.nextLine();

        System.out.print("Preço: ");
        BigDecimal preco = new BigDecimal(scanner.nextLine());

        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.print("Cliente: ");
        String cliente = scanner.nextLine();

        Pedido novoPedido = new Pedido(categoria, produto, cliente, preco, quantidade, data);
        repositorioDePedidos.cadastrarPedido(novoPedido);

        System.out.println("Pedido cadastrado com sucesso!");
    }

    private void excluirPedido() {
        System.out.print("Digite o índice do pedido que deseja excluir: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        try {
            Pedido pedidoExcluido = repositorioDePedidos.excluiPorIndice(indice);
            System.out.println("Pedido excluído: " + pedidoExcluido);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}

