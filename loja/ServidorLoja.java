package loja;

import common.EsteiraCircular;
import common.Protocolo;
import common.Veiculo;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorLoja extends UnicastRemoteObject implements LojaRemota {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final EsteiraCircular esteira;
    private Socket socketFabrica;
    private PrintWriter out;
    private BufferedReader in;
    private final Object lockSocket = new Object();
    private static final int CAPACIDADE_ESTEIRA_LOJA = 40;

    public ServidorLoja(int id, String fabricaHost, int fabricaPorta) throws Exception {
        this.id = id;
        this.esteira = new EsteiraCircular(CAPACIDADE_ESTEIRA_LOJA);
        conectarFabrica(fabricaHost, fabricaPorta);
        new Thread(this::solicitarVeiculos, "Loja-" + id + "-Solicitante").start();
    }

    private void conectarFabrica(String host, int porta) throws Exception {
        socketFabrica = new Socket(host, porta);
        out = new PrintWriter(socketFabrica.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socketFabrica.getInputStream()));
        System.out.println("[LOJA " + id + "] Conectada à fábrica em " + host + ":" + porta);
    }

    private void solicitarVeiculos() {
        while (true) {
            try {
                synchronized (lockSocket) {
                    out.println(Protocolo.SOLICITAR_VEICULO);
                    String resposta = in.readLine();
                    if (resposta != null && resposta.startsWith(Protocolo.VEICULO_DISPONIVEL)) {
                        String dados = resposta.split(Protocolo.SEPARADOR_REGEX, 2)[1];
                        Veiculo v = desserializarVeiculo(dados);
                        int posicao = esteira.inserir(v);
                        v.setPosicaoEsteiraLoja(posicao);
                        System.out.println("[LOJA " + id + "][RECEBIMENTO] " + v +
                                " | PosLoja=" + posicao);
                    }
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.out.println("[LOJA " + id + "] Erro na comunicação com fábrica: " + e.getMessage());
                try { Thread.sleep(1000); } catch (InterruptedException ie) { break; }
            }
        }
    }

    @Override
    public Veiculo comprarVeiculo(int clienteId) throws RemoteException {
        try {
            Veiculo v = (Veiculo) esteira.remover();
            System.out.println("[LOJA " + id + "][VENDA_CLIENTE] Cliente=" + clienteId +
                    " comprou " + v);
            return v;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrompido aguardando veículo");
        }
    }

    private Veiculo desserializarVeiculo(String dados) {
        String[] p = dados.split(",");
        Veiculo v = new Veiculo(
                Integer.parseInt(p[0]),
                p[1], p[2],
                Integer.parseInt(p[3]),
                Integer.parseInt(p[4])
        );
        v.setPosicaoEsteiraFabrica(Integer.parseInt(p[5]));
        v.setLojaId(Integer.parseInt(p[6]));
        return v;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Uso: java loja.ServidorLoja <lojaId> <fabricaHost> <fabricaPorta>");
            return;
        }
        int lojaId = Integer.parseInt(args[0]);
        String fabricaHost = args[1];
        int fabricaPorta = Integer.parseInt(args[2]);
        int rmiPorta = 1099 + lojaId;

        ServidorLoja loja = new ServidorLoja(lojaId, fabricaHost, fabricaPorta);

        Registry registry = LocateRegistry.createRegistry(rmiPorta);
        registry.bind("Loja" + lojaId, loja);

        System.out.println("[LOJA " + lojaId + "] RMI registrado na porta " + rmiPorta);
    }
}
