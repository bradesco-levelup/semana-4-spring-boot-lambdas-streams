package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@AllArgsConstructor
public class CadastrarPedidoCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
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

    @Override
    public String getDescricao() {
        return "Cadastrar pedido";
    }
}
