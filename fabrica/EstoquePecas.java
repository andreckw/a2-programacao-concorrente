package fabrica;

import java.util.concurrent.Semaphore;

public class EstoquePecas {
    private int quantidade;
    private final Semaphore mutex;
    private final Semaphore esteira;

    public EstoquePecas(int quantidadeInicial, int capacidadeEsteira) {
        this.quantidade = quantidadeInicial;
        this.mutex = new Semaphore(1);
        this.esteira = new Semaphore(capacidadeEsteira);
    }

    public boolean retirarPeca() throws InterruptedException {
        esteira.acquire();
        mutex.acquire();
        boolean sucesso = false;
        if (quantidade > 0) {
            quantidade--;
            sucesso = true;
        }
        mutex.release();
        esteira.release();
        return sucesso;
    }

    public int getQuantidade() throws InterruptedException {
        mutex.acquire();
        int q = quantidade;
        mutex.release();
        return q;
    }
}
