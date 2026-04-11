package classes;

public class Carro {

    private static int proximoId = 1;
    protected int id;
    protected Funcionario funcionario;
    protected Estacao estacao;

    public Carro(Funcionario funcionario) {
        this.id = proximoId;
        this.funcionario = funcionario;
        proximoId++;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }
}
