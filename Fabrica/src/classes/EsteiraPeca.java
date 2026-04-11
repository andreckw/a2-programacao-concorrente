package classes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class EsteiraPeca extends Thread {
    
    private static Queue<Peca> estoque = new LinkedList<>();
    private static Fabrica fabrica;
    protected Semaphore semaphore;

    public EsteiraPeca() {
        fabrica = new Fabrica();
        semaphore = new Semaphore(5);
    }

    
    @Override
    public void run() {
        while (true) {
            if (estoque.size() >= 5) {
                continue;
            }
            try {
                semaphore.acquire();
                estoque.add(fabrica.retirarPeca());
                System.out.println(estoque.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Peca pegarPeca() {
        semaphore.release();
        return estoque.poll();
    }
}
