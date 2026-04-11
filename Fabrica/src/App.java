import java.util.LinkedList;
import java.util.List;

import classes.Estacao;
import classes.EsteiraCarro;
import classes.EsteiraPeca;
import classes.Funcionario;

public class App {
    public static void main(String[] args) {
        EsteiraPeca esteiraPeca = new EsteiraPeca();
        List<Estacao> estacao = new LinkedList<>();
        EsteiraCarro esteiraCarro = new EsteiraCarro();

        esteiraPeca.start();

        for (int i = 0; i < 4; i++) {
            Estacao newEstacao = new Estacao(i, esteiraPeca, esteiraCarro);
            for (int j = 0; j < 5; j++) {
                newEstacao.addFuncionario(new Funcionario(i));
            }

            estacao.add(newEstacao);
            estacao.get(i).start();
        }
    }
}
