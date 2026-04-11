package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Estacao extends Thread {

    protected static EsteiraPeca esteiraPeca;
    protected static EsteiraCarro esteiraCarro;
    protected static int proximoFunc;
    protected int id;
    protected Semaphore ferEsquerda;
    protected Semaphore ferDireita;
    protected List<Funcionario> funcionarios = new ArrayList<>();

    public Estacao(int id, EsteiraPeca esteiraPeca, EsteiraCarro esteiraCarro) {
        this.id = id;
        Estacao.esteiraPeca = esteiraPeca;
        Estacao.esteiraCarro = esteiraCarro;
        ferEsquerda = new Semaphore(5);
        ferDireita = new Semaphore(5);
    }

    public void addFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    @Override
    public void run() {
        while (true) {
            Funcionario func = funcionarios.get(proximoFunc);
            proximoFunc++;
            if (proximoFunc >= funcionarios.size()) {
                proximoFunc = 0;
            }
            try {
                Peca peca = esteiraPeca.pegarPeca();
                if (peca == null) {
                    System.out.println("Sem peca");
                    continue;
                }
                ferEsquerda.acquire();
                ferDireita.acquire();
    
                Carro newCarro = func.construirCarro();
                if (newCarro != null) {
                    newCarro.setEstacao(this);
                    esteiraCarro.addCarro(newCarro);
                }
                ferDireita.release();
                ferEsquerda.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
