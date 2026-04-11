package classes;

import java.util.LinkedList;
import java.util.Queue;

public class Fabrica {
    protected Queue<Peca> pecas = new LinkedList<>();

    public Fabrica() {
        for(int i = 0; i < 500; i++) {
            pecas.add(new Peca(i));
        }
    }

    public Peca retirarPeca() {
        return pecas.poll();
    }
}