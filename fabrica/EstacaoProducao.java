package fabrica;

import common.EsteiraCircular;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class EstacaoProducao {
    private final int id;
    private final EsteiraCircular esteira;
    private final Funcionario[] funcionarios;
    private final Thread[] threads;
    private static final int NUM_FUNCIONARIOS = 5;
    private static final int CAPACIDADE_ESTEIRA = 40;

    public EstacaoProducao(int id, EstoquePecas estoque, AtomicInteger contadorVeiculos) {
        this.id = id;
        this.esteira = new EsteiraCircular(CAPACIDADE_ESTEIRA);

        Ferramenta[] ferramentas = new Ferramenta[NUM_FUNCIONARIOS];
        for (int i = 0; i < NUM_FUNCIONARIOS; i++) {
            ferramentas[i] = new Ferramenta(id * 10 + i);
        }

        Semaphore semaforoEstacao = new Semaphore(NUM_FUNCIONARIOS - 1);

        funcionarios = new Funcionario[NUM_FUNCIONARIOS];
        threads = new Thread[NUM_FUNCIONARIOS];

        for (int i = 0; i < NUM_FUNCIONARIOS; i++) {
            Ferramenta esquerda = ferramentas[i];
            Ferramenta direita = ferramentas[(i + 1) % NUM_FUNCIONARIOS];
            funcionarios[i] = new Funcionario(i, id, esquerda, direita,
                    estoque, esteira, contadorVeiculos, semaforoEstacao);
            threads[i] = new Thread(funcionarios[i], "Estacao-" + id + "-Func-" + i);
        }
    }

    public void iniciar() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public EsteiraCircular getEsteira() {
        return esteira;
    }

    public int getId() {
        return id;
    }
}
