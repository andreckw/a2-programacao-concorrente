package classes;

public class Funcionario {
    protected int id;

    public Funcionario(int id) {
        this.id = id;
    }

    public Carro construirCarro() {
        return new Carro(this);
    }
}
