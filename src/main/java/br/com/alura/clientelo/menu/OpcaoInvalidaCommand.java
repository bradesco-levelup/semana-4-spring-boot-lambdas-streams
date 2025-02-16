package br.com.alura.clientelo.menu;

public class OpcaoInvalidaCommand implements FuncionalidadeCommand {

    @Override
    public void executa() {
        System.out.println("Opção inválida. Tente novamente.");
    }

    @Override
    public String getDescricao() {
        return "Opção inválida";
    }
}
