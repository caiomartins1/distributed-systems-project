package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ManagerClient extends UnicastRemoteObject implements ManagerClientInterface {
    public ManagerClient() throws RemoteException {
        super();
    }

    public void printOnClient(String s) throws RemoteException {
        System.out.println("Message from the server: " + s);
    }

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1199);
            ServerInterface server = (ServerInterface) Naming.lookup("server");
            ManagerClient managerClient = new ManagerClient();
            server.subscribeManager("Manager Client 1", managerClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
