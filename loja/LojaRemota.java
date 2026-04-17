package loja;

import common.Veiculo;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LojaRemota extends Remote {
    Veiculo comprarVeiculo(int clienteId) throws RemoteException;
}
