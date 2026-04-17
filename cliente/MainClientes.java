package cliente;

public class MainClientes {
    private static final int NUM_CLIENTES = 20;
    private static final int NUM_LOJAS = 3;

    public static void main(String[] args) throws Exception {
        String[] lojaHosts = new String[NUM_LOJAS];
        int[] lojaPorts = new int[NUM_LOJAS];

        for (int i = 0; i < NUM_LOJAS; i++) {
            lojaHosts[i] = args.length > i ? args[i] : "localhost";
            lojaPorts[i] = 1100 + (i + 1);
        }

        Thread[] threads = new Thread[NUM_CLIENTES];
        for (int i = 0; i < NUM_CLIENTES; i++) {
            Cliente c = new Cliente(i + 1, lojaHosts, lojaPorts);
            threads[i] = new Thread(c, "Cliente-" + (i + 1));
        }

        for (Thread t : threads) {
            t.start();
            Thread.sleep(100);
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("[CLIENTES] Todos os clientes finalizaram.");
    }
}
