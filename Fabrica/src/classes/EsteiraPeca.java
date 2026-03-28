package classes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class EsteiraPeca extends Thread {
    
    private static Queue<Peca> estoque = new LinkedList<>();
    protected Queue<Peca> pecas = new LinkedList<>();
    protected Semaphore semaphore;

    public EsteiraPeca() {
        for(int i = 0; i < 500; i++) {
            pecas.add(new Peca(i));
        }
        
        semaphore = new Semaphore(5);
    }

    
    @Override
    public void run() {
        try {
            semaphore.acquire();
            pecas.add(estoque.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Peca pegarPeca() {
        semaphore.release();
        return pecas.poll();
    }
}
