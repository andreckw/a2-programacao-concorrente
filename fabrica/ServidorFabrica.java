package fabrica;

import common.EsteiraCircular;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorFabrica {
    private static final int PORTA_BASE = 9000;
    private static final int NUM_ESTACOES = 4;
    private static final int CAPACIDADE_ESTOQUE = 500;
    private static final int CAPACIDADE_ESTEIRA_CENTRAL = 160;
    private static final int CAPACIDADE_ESTEIRA_PECAS = 5;
    private static final int NUM_LOJAS = 3;

    public static void main(String[] args) throws Exception {
        AtomicInteger contadorVeiculos = new AtomicInteger(0);
        EstoquePecas estoque = new EstoquePecas(CAPACIDADE_ESTOQUE, CAPACIDADE_ESTEIRA_PECAS);
        EsteiraCircular esteiraCentral = new EsteiraCircular(CAPACIDADE_ESTEIRA_CENTRAL);

        EstacaoProducao[] estacoes = new EstacaoProducao[NUM_ESTACOES];
        for (int i = 0; i < NUM_ESTACOES; i++) {
            estacoes[i] = new EstacaoProducao(i + 1, estoque, contadorVeiculos);
            estacoes[i].iniciar();
        }

        Despachante despachante = new Despachante(estacoes, esteiraCentral);
        new Thread(despachante, "Despachante").start();

        System.out.println("[FABRICA] Iniciada. Aguardando lojas...");

        for (int lojaId = 1; lojaId <= NUM_LOJAS; lojaId++) {
            final int id = lojaId;
            final int porta = PORTA_BASE + lojaId;
            new Thread(() -> {
                try (ServerSocket server = new ServerSocket(porta)) {
                    System.out.println("[FABRICA] Aguardando Loja " + id + " na porta " + porta);
                    while (true) {
                        Socket cliente = server.accept();
                        System.out.println("[FABRICA] Loja " + id + " conectada.");
                        new Thread(new LojaHandler(cliente, esteiraCentral, id)).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Servidor-Loja-" + lojaId).start();
        }
    }
}
