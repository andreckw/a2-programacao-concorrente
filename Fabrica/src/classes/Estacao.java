package classes;

import java.util.ArrayList;
import java.util.List;

public class Estacao {
    protected int id;
    protected List<Funcionario> funcionarios = new ArrayList<>();

    public Estacao(int id) {
        this.id = id;
    }

    public void addFuncionario(Funcionario f) {
        funcionarios.add(f);
    }
}
