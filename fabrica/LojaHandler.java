package fabrica;

import common.EsteiraCircular;
import common.Protocolo;
import common.Veiculo;

import java.io.*;
import java.net.Socket;

public class LojaHandler implements Runnable {
    private final Socket socket;
    private final EsteiraCircular esteiraCentral;
    private final int lojaId;

    public LojaHandler(Socket socket, EsteiraCircular esteiraCentral, int lojaId) {
        this.socket = socket;
        this.esteiraCentral = esteiraCentral;
        this.lojaId = lojaId;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
        ) {
            String linha;
            while ((linha = in.readLine()) != null) {
                if (linha.equals(Protocolo.SOLICITAR_VEICULO)) {
                    Veiculo v = (Veiculo) esteiraCentral.remover();
                    v.setLojaId(lojaId);
                    String serializado = serializarVeiculo(v);
                    out.println(Protocolo.VEICULO_DISPONIVEL + Protocolo.SEPARADOR + serializado);
                    System.out.println("[VENDA_LOJA] Veiculo #" + v.getId() + " -> Loja " + lojaId);
                }
            }
        } catch (Exception e) {
            System.out.println("[FABRICA] Loja " + lojaId + " desconectada.");
        }
    }

    private String serializarVeiculo(Veiculo v) {
        return v.getId() + "," + v.getCor() + "," + v.getTipo() + "," +
               v.getEstacaoId() + "," + v.getFuncionarioId() + "," +
               v.getPosicaoEsteiraFabrica() + "," + v.getLojaId();
    }
}
