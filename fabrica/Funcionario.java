package fabrica;

import common.EsteiraCircular;
import common.Veiculo;

public class Funcionario implements Runnable {
    private final int id;
    private final int estacaoId;
    private final Ferramenta ferramentaEsquerda;
    private final Ferramenta ferramentaDireita;
    private final EstoquePecas estoque;
    private final EsteiraCircular esteiraEstacao;
    private final ContadorVeiculo contadorVeiculos;

    private static final String[] CORES = {"RED", "GREEN", "BLUE"};
    private static final String[] TIPOS = {"SUV", "SEDAN"};

    public Funcionario(int id, int estacaoId, Ferramenta esquerda, Ferramenta direita,
                       EstoquePecas estoque, EsteiraCircular esteira,
                       ContadorVeiculo contador) {
        this.id = id;
        this.estacaoId = estacaoId;
        this.ferramentaEsquerda = esquerda;
        this.ferramentaDireita = direita;
        this.estoque = estoque;
        this.esteiraEstacao = esteira;
        this.contadorVeiculos = contador;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!estoque.retirarPeca()) {
                    Thread.sleep(500);
                    continue;
                }

                pegarFerramentas();

                int veiculoId = contadorVeiculos.incrementar();
                String cor = CORES[(veiculoId - 1) % CORES.length];
                String tipo = TIPOS[(veiculoId - 1) % TIPOS.length];

                Veiculo veiculo = new Veiculo(veiculoId, cor, tipo, estacaoId, id);

                Thread.sleep(100 + (int)(Math.random() * 200));

                largarFerramentas();

                int posicao = esteiraEstacao.inserir(veiculo);
                veiculo.setPosicaoEsteiraFabrica(posicao);

                System.out.println("[PRODUCAO] " + veiculo);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void pegarFerramentas() throws InterruptedException {
        if (ferramentaEsquerda.getId() < ferramentaDireita.getId()) {
            ferramentaEsquerda.pegar();
            ferramentaDireita.pegar();
        } else {
            ferramentaDireita.pegar();
            ferramentaEsquerda.pegar();
        }
    }

    private void largarFerramentas() {
        ferramentaEsquerda.largar();
        ferramentaDireita.largar();
    }
}
