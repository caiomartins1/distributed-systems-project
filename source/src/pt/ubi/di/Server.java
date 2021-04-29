package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.Part;
import pt.ubi.di.services.ManagerService;
import pt.ubi.di.utils.FileUtils;
import pt.ubi.di.utils.ReadUtils;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Server extends UnicastRemoteObject implements ServerInterface {
    private static ManagerClientInterface managerClient;
    private static BuyerClientInterface buyerClient;

    private static ManagerService mService;

    private static ArrayList<Part> parts;

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
        managerClient.printOnClient("\nPart \"" + p.getType() + "\" added with success");
    }

    public void managerOption2() throws RemoteException {

    }

    public void managerOption3() throws RemoteException {
        managerClient.printOnClient("Available products to remove: ");
        int i = 1;
        for (Part part: parts) {
            managerClient.printOnClient(i + ". " + part.getType());
            i++;
        }
        managerClient.printOnClient("Choose a number (1 to " +  parts.size() + "): ");
        int itemToDelete = managerClient.readIntClient();


        if (itemToDelete < 1 || itemToDelete > parts.size()) {
            managerClient.printOnClient("Invalid Input");
            return;
        }

        managerClient.printOnClient("Are you sure you want to delete the part: " + parts.get(itemToDelete - 1).getType() + "? (y/n)");
        String confirmation = managerClient.readStringClient();

        switch (confirmation) {
            case "y":
                parts.remove(itemToDelete - 1);
                managerClient.printOnClient("Item removed with success");
                FileUtils.saveParts(parts);
                break;
            case "n":
                managerClient.printOnClient("Action canceled");
                break;
            default:
                managerClient.printOnClient("Invalid input");
                break;
        }

    }

    public void managerOption0() throws RemoteException {

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
