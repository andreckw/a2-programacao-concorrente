package cliente;

import loja.LojaRemota;
import common.Veiculo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {
    private final int id;
    private final List<Veiculo> garagem;
    private final Semaphore mutexGaragem;
    private final String[] lojaHosts;
    private final int[] lojaPorts;
    private final Random random = new Random();
    private static final int MAX_COMPRAS = 5;
    private static final int NUM_LOJAS = 3;

    public Cliente(int id, String[] lojaHosts, int[] lojaPorts) {
        this.id = id;
        this.garagem = new ArrayList<>();
        this.mutexGaragem = new Semaphore(1);
        this.lojaHosts = lojaHosts;
        this.lojaPorts = lojaPorts;
    }

    @Override
    public void run() {
        int numCompras = 1 + random.nextInt(MAX_COMPRAS);
        for (int i = 0; i < numCompras; i++) {
            try {
                int lojaIdx = random.nextInt(NUM_LOJAS);
                String host = lojaHosts[lojaIdx];
                int porta = lojaPorts[lojaIdx];
                int lojaId = lojaIdx + 1;

                Registry registry = LocateRegistry.getRegistry(host, porta);
                LojaRemota loja = (LojaRemota) registry.lookup("Loja" + lojaId);

                System.out.println("[CLIENTE " + id + "] Tentando comprar na Loja " + lojaId);
                Veiculo v = loja.comprarVeiculo(id);

                mutexGaragem.acquire();
                garagem.add(v);
                mutexGaragem.release();

                System.out.println("[CLIENTE " + id + "] Comprou " + v + " | Garagem=" + garagem.size());
                Thread.sleep(200 + random.nextInt(800));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.out.println("[CLIENTE " + id + "] Erro: " + e.getMessage());
                try { Thread.sleep(500); } catch (InterruptedException ie) { break; }
            }
        }
        System.out.println("[CLIENTE " + id + "] Finalizado. Veículos na garagem: " + garagem.size());
    }
}
