package common;

import java.io.Serializable;

public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String cor;
    private final String tipo;
    private final int estacaoId;
    private final int funcionarioId;
    private int posicaoEsteiraFabrica;
    private int lojaId;
    private int posicaoEsteiraLoja;

    public Veiculo(int id, String cor, String tipo, int estacaoId, int funcionarioId) {
        this.id = id;
        this.cor = cor;
        this.tipo = tipo;
        this.estacaoId = estacaoId;
        this.funcionarioId = funcionarioId;
    }

    public int getId() { return id; }
    public String getCor() { return cor; }
    public String getTipo() { return tipo; }
    public int getEstacaoId() { return estacaoId; }
    public int getFuncionarioId() { return funcionarioId; }
    public int getPosicaoEsteiraFabrica() { return posicaoEsteiraFabrica; }
    public void setPosicaoEsteiraFabrica(int p) { this.posicaoEsteiraFabrica = p; }
    public int getLojaId() { return lojaId; }
    public void setLojaId(int l) { this.lojaId = l; }
    public int getPosicaoEsteiraLoja() { return posicaoEsteiraLoja; }
    public void setPosicaoEsteiraLoja(int p) { this.posicaoEsteiraLoja = p; }

    @Override
    public String toString() {
        return "[Veiculo #" + id + " | " + cor + " | " + tipo +
               " | Estacao=" + estacaoId + " | Func=" + funcionarioId +
               " | PosEsteira=" + posicaoEsteiraFabrica + "]";
    }
}
