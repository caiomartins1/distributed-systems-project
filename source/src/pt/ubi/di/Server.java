package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.*;
import pt.ubi.di.services.ManagerService;
import pt.ubi.di.utils.FileUtils;
import pt.ubi.di.utils.ReadUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

// TODO: 0 -> format receipt print
// TODO: 0 -> format IDs
// TODO: 2 -> Store balance
// TODO: 3 -> Remote Setup
// TODO: 4 -> Add date to receipt and part
// TODO: 4 -> add name to receipt
// TODO: 5 -> change arraylist of string to array list of items (receipts, Part...)
// TODO: 5 -> Validate prod name
// TODO: 5 -> Write Receipts
// TODO: 5 -> make sure stock are written
// TODO: 5 -> Convert buyer to multi buyers
// TODO: 5 -> check all listings client and manager;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private static BuyerClientInterface buyerClient;
    private static ArrayList<ManagerClientInterface> mClients;

    private static ManagerService mService;

    private static ArrayList<Part> parts;

    private static Purchase buyingService;
    private static Sale sellingService;

    private float storeBalance;

    public Server() throws RemoteException {
        super();
        mService = new ManagerService();
        buyingService = new Purchase();
        sellingService = new Sale();
        mClients = new ArrayList<>();
        loadData();
    }

    public void subscribeManager(String name, ManagerClientInterface client) {
        System.out.println("Subscribing..." + name);
        mClients.add(client);
    }

    public void subscribeBuyer(String name, BuyerClientInterface client2) {
        System.out.println("Subscribing..." + name);
        buyerClient = client2;
    }

    private void loadData() {
        parts = FileUtils.retrieveParts();
        System.out.println("Loading data...");
        // TODO: Remove debug log
        System.out.println(parts.toString());
    }

    public void managerOption0() throws RemoteException {

    }

    public void managerOption1(ManagerClientInterface client, Part p) throws RemoteException {

        client.printOnClient("---- Registering new part ----");
        mService.registerPart(parts, p);
        client.printOnClient("\n---- Part " + p.getType() + " added with success ----");

        try {
            client.printOnClient("Do you wish to buy stock for this part?(y/n)");
            char decision = client.readCharClient();
            int quantity = -1;
            if (decision == 'y') {
                do {
                    client.printOnClient("Type quantity: ");
                    quantity = client.readIntClient();
                    if (quantity > 0) {
                        buyingService.buySingleOrder(new Order(p, quantity));
                        client.printOnClient("Purchase made");
                    } else {
                        client.printOnClient("____Wrong value____");
                    }
                } while (quantity <= 0);
            } else {
                System.out.println("No purchase made for part");
            }
        } catch (Exception e) {
            System.out.println("Error adding stock: " + e);
        }
        client.printOnClient("\nPart \"" + p.getType() + "\" added with success");
    }

    public void managerOption2(ManagerClientInterface client) throws RemoteException {

        //menu print
        client.printOnClient("---- Buying from supplier ----");
        for (int i = 0; i < parts.size(); ++i)//Print all parts available and their index on list
            client.printOnClient(i + ":------>" + parts.get(i).toStringClean());
        client.printOnClient("choose what to buy, by number:\nType -2 to complete the order\nType -1 to cancel the order");

        ArrayList<Order> orders = new ArrayList<>();

        boolean eval = true;
        while (eval) {// loop to decide quantities for parts
            client.printOnClientNoNL("Type product: ");
            int option = client.readIntClient();

            if (option == -1) {
                client.printOnClient("____Order canceled____");
                return;
            } else if (option == -2) {
                client.printOnClient("Confirming Order.....");
                eval = false;

            } else {
                if (0 <= option && option < parts.size()) {
                    client.printOnClientNoNL("Product: " + parts.get(option).getType() + " -> type quantity:");
                    int quantity = client.readIntClient();
                    Part part = parts.get(option);
                    System.out.println(part.toString());
                    if (quantity > 0) {
                        orders.add(new Order(part, quantity));
                    } else {
                        client.printOnClient("____No valid number____");
                    }
                } else {
                    client.printOnClient("____Wrong value____");
                }
            }
        }

        if (orders.isEmpty()) {
            client.printOnClient("____No orders made, exiting menu____");
        } else {
            try {
                for (Order value : orders) {
                    client.printOnClient(value.getPart().toStringClean());
                }
                AdvanceReceipt advSlip = buyingService.buyOrder(orders);
                client.printOnClientNoNL("Checking order");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(".\n");
                client.printOnClient("---- Success!!!! ----\n" + advSlip.toString());
            } catch (Exception e) {
                System.out.println("ERROR on Thread: " + e.getMessage());
            }
        }
    }

    public void managerOption3(ManagerClientInterface managerClient) throws RemoteException {

        if (parts.size() == 0) {
            managerClient.printOnClient("No part registered yet!");
            return;
        }
        managerClient.printOnClient("Available products to remove: ");
        int i = 1;
        for (Part part : parts) {
            managerClient.printOnClient(i + ". " + part.getType());
            i++;
        }
        managerClient.printOnClient("Choose a number (1 to " + parts.size() + "): ");
        int itemToDelete = managerClient.readIntClient();


        if (itemToDelete < 1 || itemToDelete > parts.size()) {
            managerClient.printOnClient("Invalid Input");
            return;
        }

        managerClient.printOnClient("Are you sure you want to delete the part: " + parts.get(itemToDelete - 1).getType() + "? (y/n)");
        String confirmation = managerClient.readStringClient();

        switch (confirmation) {
            case "y":
                mService.deletePart(parts, itemToDelete - 1);
                managerClient.printOnClient("Item removed with success");
                break;
            case "n":
                managerClient.printOnClient("Action canceled");
                break;
            default:
                managerClient.printOnClient("Invalid input");
                break;
        }

    }

    public void managerOption4(ManagerClientInterface managerClient) throws RemoteException {
        managerClient.printOnClient(
                "----- Choose a filter -----\n" +
                        "1. Type\n" +
                        "2. Buy Price\n" +
                        "3. Sell price\n" +
                        "4. Items in stock\n" +
                        "Your Option: "
        );

        int option = managerClient.readIntClient();

        switch (option) {
            case 1:
                managerClient.printOnClient(mService.listByTypeAlpha(parts));
                break;
            case 2:
                managerClient.printOnClient(mService.listByBuyPrice(parts));
                break;
            case 3:
                managerClient.printOnClient(mService.listBySellPrice(parts));
                break;
            case 4:
                managerClient.printOnClient(mService.listByStockItems(parts));
                break;

            default:
                managerClient.printOnClient("Invalid option!");
                break;
        }
    }

    public void managerOption5(ManagerClientInterface managerClient) {
        try {
            managerClient.printOnClient(buyingService.getPurchaseHistory().toString());
        } catch (Exception e) {
            System.out.println("Error printing purchase history" + e.getMessage());
        }
    }


    public void buyerOption1() throws RemoteException {

        //menu print
        buyerClient.printOnClient("---- Buying from Store ----");
        for (int i = 0; i < parts.size(); ++i)//Print all parts available and their index on list
            buyerClient.printOnClient(i + ":------>" + parts.get(i).toStringClean());
        buyerClient.printOnClient("choose what to buy, by number:\nType -2 to complete the order\nType -1 to cancel the order");

        ArrayList<Order> orders = new ArrayList<>();

        boolean eval = true;
        while (eval) {// loop to decide quantities for parts
            buyerClient.printOnClientNoNL("Type product: ");
            int option = buyerClient.readIntClient();

            if (option == -1) {
                buyerClient.printOnClient("____Order canceled____");
                return;
            } else if (option == -2) {
                buyerClient.printOnClient("Confirming Order.....");
                eval = false;
            } else {
                if (0 <= option && option < parts.size()) {
                    buyerClient.printOnClientNoNL("Product: " + parts.get(option).getType() + " -> type quantity:");
                    int quantity = buyerClient.readIntClient();
                    Part part = parts.get(option);
                    System.out.println(part.toString());
                    if (quantity > 0) {
                        if (quantity < parts.get(option).getStock()) {
                            orders.add(new Order(part, quantity));
                        } else {
                            buyerClient.printOnClient("Not enough stock");
                        }
                    } else {
                        buyerClient.printOnClient("____No valid number____");
                    }
                } else {
                    buyerClient.printOnClient("____Wrong value____");
                }
            }
        }

        if (orders.isEmpty()) {
            buyerClient.printOnClient("____No orders made, exiting menu____");
        } else {
            try {
                for (Order value : orders) {
                    buyerClient.printOnClient(value.getPart().toStringClean());
                }
                AdvanceReceipt advSlip = sellingService.sellOrder(orders);
                buyerClient.printOnClientNoNL("Checking order");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".\n");
                buyerClient.printOnClient("---- Success!!!! ----\n" + advSlip.toString());
            } catch (Exception e) {
                System.out.println("ERROR on Thread: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
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
