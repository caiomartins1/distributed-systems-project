package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private static ManagerClientInterface managerClient;
    private static BuyerClientInterface buyerClient;


    public Server() throws RemoteException {
        super();
    }

    public void subscribeManager(String name, ManagerClientInterface client) {
        System.out.println("Subscribing..." + name);
        managerClient = client;
    }

    // Testing purposes
    public static String lerString() {
        String s = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
            s = in.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public static void main(String[] args) {
        String s;
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1099);
            Server server = new Server();
            Naming.rebind("server", server);

            while (true) {
                System.out.println("Message to client: ");
                s = lerString();
                managerClient.printOnClient(s);
                System.out.println(managerClient.getInput());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
