package fabrica;

import common.EsteiraCircular;
import common.Veiculo;

public class Despachante implements Runnable {
    private final EstacaoProducao[] estacoes;
    private final EsteiraCircular esteiraCentral;
    private volatile boolean rodando = true;

    public Despachante(EstacaoProducao[] estacoes, EsteiraCircular esteiraCentral) {
        this.estacoes = estacoes;
        this.esteiraCentral = esteiraCentral;
    }

    @Override
    public void run() {
        int idx = 0;
        while (rodando) {
            try {
                EstacaoProducao estacao = estacoes[idx % estacoes.length];
                idx++;
                Veiculo v = (Veiculo) estacao.getEsteira().remover();
                esteiraCentral.inserir(v);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void parar() {
        rodando = false;
    }
}
