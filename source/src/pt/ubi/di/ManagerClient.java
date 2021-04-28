package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public String getInput() {
        String s = "";

        System.out.println();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
            s = in.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }


    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1199);
            ServerInterface server = (ServerInterface) Naming.lookup("server");
            ManagerClient managerClient = new ManagerClient();
            server.subscribeManager("Manager Client 1", managerClient);

            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}