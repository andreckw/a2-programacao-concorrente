package classes;

import java.util.ArrayList;
import java.util.List;

public class EsteiraCarro extends Thread {
    protected List<Carro> carros = new ArrayList<>();

    @Override
    public void run() {
        super.run();
        // TODO fazer para 
    }

    public void addCarro(Carro carro) {
        if (carros.size() >= 40) {
            return;
        }
        carros.add(carro);
    }
}
