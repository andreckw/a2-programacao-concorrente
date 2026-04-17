package fabrica;

import java.util.concurrent.Semaphore;

public class Ferramenta {
    private final int id;
    private final Semaphore semaforo;

    public Ferramenta(int id) {
        this.id = id;
        this.semaforo = new Semaphore(1);
    }

    public void pegar() throws InterruptedException {
        semaforo.acquire();
    }

    public void largar() {
        semaforo.release();
    }

    public int getId() {
        return id;
    }
}
