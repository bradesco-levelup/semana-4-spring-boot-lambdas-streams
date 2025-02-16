package br.com.alura.clientelo.menu;

import br.com.alura.clientelo.pedido.Pedido;
import br.com.alura.clientelo.pedido.RepositorioDePedidos;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class ExcluirPedidoCommand implements FuncionalidadeCommand {

    private RepositorioDePedidos repositorioDePedidos;

    @Override
    public void executa() {
        Scanner scanner = new Scanner(System.in);
        
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
    
    @Override
    public String getDescricao() {
        return "Excluir pedido";
    }
}
