package common;

import java.util.concurrent.Semaphore;

public class EsteiraCircular {
    private final Object[] buffer;
    private final int capacidade;
    private int cabeca;
    private int cauda;
    private int count;

    private final Semaphore mutex;
    private final Semaphore slots;
    private final Semaphore itens;

    public EsteiraCircular(int capacidade) {
        this.capacidade = capacidade;
        this.buffer = new Object[capacidade];
        this.cabeca = 0;
        this.cauda = 0;
        this.count = 0;
        this.mutex = new Semaphore(1);
        this.slots = new Semaphore(capacidade);
        this.itens = new Semaphore(0);
    }

    public int inserir(Object item) throws InterruptedException {
        slots.acquire();
        mutex.acquire();
        buffer[cauda] = item;
        int posicao = cauda;
        cauda = (cauda + 1) % capacidade;
        count++;
        mutex.release();
        itens.release();
        return posicao;
    }

    public Object remover() throws InterruptedException {
        itens.acquire();
        mutex.acquire();
        Object item = buffer[cabeca];
        buffer[cabeca] = null;
        cabeca = (cabeca + 1) % capacidade;
        count--;
        mutex.release();
        slots.release();
        return item;
    }

    public int getCount() {
        return count;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
