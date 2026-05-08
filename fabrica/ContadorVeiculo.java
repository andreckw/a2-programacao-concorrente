package fabrica;

import java.util.concurrent.Semaphore;

public class ContadorVeiculo {
    private int valor;
    private final Semaphore mutex;
    
    public ContadorVeiculo() {
        this.valor = 0;
        this.mutex = new Semaphore(1);
    }
    public int incrementar() throws InterruptedException {
        mutex.acquire();
        int novoValor = ++valor;  // int comum, protegido pelo semáforo
        mutex.release();
        return novoValor;
    }
}