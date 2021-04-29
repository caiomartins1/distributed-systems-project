package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.Part;
import pt.ubi.di.services.ManagerService;
import pt.ubi.di.utils.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private static ManagerClientInterface managerClient;
    private static BuyerClientInterface buyerClient;

    private static ManagerService mService;

    private static ArrayList<Part> parts;


    // Discuss: Services are kinda redundant
    // Add arrays to service?

    // File access problems

    public Server() throws RemoteException {
        super();
        mService = new ManagerService();
        loadData();

    }

    public void subscribeManager(String name, ManagerClientInterface client) {
        System.out.println("Subscribing..." + name);
        managerClient = client;
    }

    public void managerOption1(Part p) throws RemoteException {
        mService.registerPart(parts, p);
        managerClient.printOnClient("\nPart " + p.getType() + " added with success");
    }

    public void managerOption2() throws RemoteException {

    }

    private void loadData() {
        parts = FileUtils.retrieveParts();
        System.out.println("Loading data...");
        // TODO: Remove debug log
        System.out.println(parts.toString());
    }

    public static void main(String[] args) {
        String s;
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1099);
            Server server = new Server();
            Naming.rebind("server", server);

            System.out.println("----- Server Started -----");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
