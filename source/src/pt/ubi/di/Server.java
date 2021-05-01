package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.*;
import pt.ubi.di.services.BuyerService;
import pt.ubi.di.services.ManagerService;
import pt.ubi.di.utils.FileUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

// TODO: 2 -> Store balance
// TODO: 3 -> Remote Setup
// TODO: 4 -> Add date to part

public class Server extends UnicastRemoteObject implements ServerInterface {
    private static ArrayList<BuyerClientInterface> bClients;
    private static ArrayList<ManagerClientInterface> mClients;

    private static ManagerService mService;
    private static BuyerService bService;

    private static ArrayList<Part> parts;

    private static Purchase buyingService;
    private static Sale sellingService;

    private float storeBalance;

    public Server() throws RemoteException {
        super();
        mService = new ManagerService();
        bService = new BuyerService();
        buyingService = new Purchase();
        sellingService = new Sale();
        mClients = new ArrayList<>();
        bClients = new ArrayList<>();
        loadData();
    }

    public void subscribeManager(String name, ManagerClientInterface client) {
        System.out.println("*** Subscribing Manager " + name + " ***");
        mClients.add(client);
    }

    public void subscribeBuyer(String name, BuyerClientInterface client) {
        System.out.println("*** Subscribing Buyer " + name + " ***");
        bClients.add(client);
    }

    private void outOfStockCallback(Part p) throws RemoteException {
        System.out.println("*** Notifying Managers \"" + p.getType() + "\" is out of stock... ***");
        for (ManagerClientInterface mClient : mClients) {
            mClient.printOnClient("\n*** Warning " +
                    mClient.getClientId() + " ***\n" +
                    "Part \"" + p.getType() + "\" is out of stock!\n" +
                    "Please restock ASAP!\n"
            );
        }
    }

    private void loadData() {
        parts = FileUtils.retrieveParts();
        buyingService.setPurchaseHistory(FileUtils.retrievePurchaseHistory());
        sellingService.setSellHistory(FileUtils.retrieveSalesHistory());
        System.out.println("Loading data...");
    }

    public void managerOption0(ManagerClientInterface client) throws RemoteException {
        System.out.println("*** Manager " + client.getClientId() + " disconnected from Server ***");
        mClients.remove(client);
    }

    public void managerOption1(ManagerClientInterface client, Part p) throws RemoteException {

        if (p == null) {
            return;
        }

        for (Part p1 : parts) {
            if (p1.getType().compareToIgnoreCase(p.getType()) == 0) {
                client.printOnClient("Part: \"" + p.getType() + "\" already exists!");
                return;
            }
        }

        client.printOnClient("---- Registering new part ----");
        mService.registerPart(parts, p);
        client.printOnClient("\n---- Part " + p.getType() + " added with success ----");
        System.out.println("*** Manager " + client.getClientId() + " Added a new Part (" + p.getType() + ") ***");

        try {
            client.printOnClient("Do you wish to buy stock for this part?(y/n)");
            char decision = client.readCharClient();
            int quantity = -1;
            if (decision == 'y') {
                do {
                    client.printOnClient("Type quantity: ");
                    quantity = client.readIntClient();
                    if (quantity > 0) {
                        buyingService.buySingleOrder(new Order(p, quantity), client.getClientId());
                        client.printOnClient("---- Purchase made ----");
                        System.out.println("*** Manager " + client.getClientId() + " Added " + quantity + " items to " + p.getType() + " stock ***");
                    } else {
                        client.printOnClient("____Wrong value____");
                    }
                } while (quantity <= 0);
            } else {
                System.out.println("____ No purchase made for part ____");
            }

            if (p.getStock() < p.getMinStock()) {
                outOfStockCallback(p);
            }

        } catch (Exception e) {
            System.out.println("Error adding stock: " + e);
        }

        FileUtils.saveParts(parts);
        FileUtils.savePurchaseHistory(buyingService.getPurchaseHistory());
    }

    public void managerOption2(ManagerClientInterface client) throws RemoteException {


        client.printOnClient("---- Buying from supplier ----");
        for (int i = 0; i < parts.size(); ++i)
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
                client.printOnClient("---- Confirming Order..... ----");
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
                AdvanceReceipt advSlip = buyingService.buyOrder(orders, client.getClientId());
                client.printOnClientNoNL("---- Checking order");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(".");
                Thread.sleep(500);
                client.printOnClientNoNL(". ----\n");
                client.printOnClient("---- Success!!!! ----\n" + advSlip.toString());
            } catch (Exception e) {
                System.out.println("ERROR on Thread: " + e.getMessage());
            }
        }
        FileUtils.saveParts(parts);
        FileUtils.savePurchaseHistory(buyingService.getPurchaseHistory());
    }

    public void managerOption3(ManagerClientInterface managerClient) throws RemoteException {

        if (parts.size() == 0) {
            managerClient.printOnClient("____ No part registered yet! ____");
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
            managerClient.printOnClient("____ Invalid Input ____");
            return;
        }

        managerClient.printOnClient("Are you sure you want to delete the part: " + parts.get(itemToDelete - 1).getType() + "? (y/n)");
        String confirmation = managerClient.readStringClient();

        switch (confirmation) {
            case "y":
                Part aux = parts.get(itemToDelete - 1);
                mService.deletePart(parts, itemToDelete - 1);
                managerClient.printOnClient("---- Item removed with success ----");
                System.out.println("*** Manager " + managerClient.getClientId() + " Removed (" + aux.getType() + ") from the inventory ***");
                break;
            case "n":
                managerClient.printOnClient("____ Action canceled ____");
                break;
            default:
                managerClient.printOnClient("____ Invalid input ____");
                break;
        }
        FileUtils.saveParts(parts);
    }

    public void managerOption4(ManagerClientInterface managerClient) throws RemoteException {
        managerClient.printOnClient(
                "----- Choose a filter -----\n" +
                        "1. Type\n" +
                        "2. Buy Price\n" +
                        "3. Sell price\n" +
                        "4. Items in stock\n" +
                        "0. Cancel\n" +
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
            case 0:
                managerClient.printOnClient("Canceling...");
                break;
            default:
                managerClient.printOnClient("____ Invalid option! ____");
                break;
        }
    }

    public void managerOption5(ManagerClientInterface managerClient) {
        try {
            if (buyingService.getPurchaseHistory().isEmpty()) {
                managerClient.printOnClient("____ No purchases to show ____");
                return;
            }

            managerClient.printOnClient(buyingService.getPurchaseHistory().toString());
        } catch (Exception e) {
            System.out.println("Error printing purchase history" + e.getMessage());
        }
    }

    public void managerOption6(ManagerClientInterface managerClient) {
        try {
            if (sellingService.getSellHistory().isEmpty()) {
                managerClient.printOnClient("____ No sales to show ____");
                return;
            }

            managerClient.printOnClient(sellingService.getSellHistory().toString());
        } catch (Exception e) {
            System.out.println("Error printing sales history" + e.getMessage());
        }
    }


    public void buyerOption0(BuyerClientInterface buyerClient) throws RemoteException {
        System.out.println("*** Buyer " + buyerClient.getClientId() + " disconnected from Server ***");
        bClients.remove(buyerClient);
    }

    public void buyerOption1(BuyerClientInterface buyerClient) throws RemoteException {

        buyerClient.printOnClient("---- Buying from Store ----");
        for (int i = 0; i < parts.size(); ++i)
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
                        if (quantity <= parts.get(option).getStock()) {
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
                AdvanceReceipt advSlip = sellingService.sellOrder(orders, buyerClient.getClientId());
                buyerClient.printOnClientNoNL("Checking order");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                buyerClient.printOnClientNoNL(". ----\n");
                buyerClient.printOnClient("---- Success!!!! ----\n" + advSlip.toString());
                System.out.println("*** Buyer " + buyerClient.getClientId() + " made a purchase ***");

                for (Order order : orders) {
                    if (order.getPart().getStock() < order.getPart().getMinStock()) {
                        outOfStockCallback(order.getPart());
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR on Thread: " + e.getMessage());
            }
        }
        FileUtils.saveParts(parts);
        FileUtils.saveSalesHistory(sellingService.getSellHistory());
    }

    public void buyerOption2(BuyerClientInterface buyerClient) throws RemoteException {
        buyerClient.printOnClient(
                "----- Choose a filter -----\n" +
                        "1. Type\n" +
                        "2. Buy Price\n" +
                        "3. Sell price\n" +
                        "4. Items in stock\n" +
                        "0. Cancel\n" +
                        "Your Option: "
        );

        int option = buyerClient.readIntClient();

        switch (option) {
            case 1:
                buyerClient.printOnClient(bService.listByTypeAlpha(parts));
                break;
            case 2:
                buyerClient.printOnClient(bService.listByBuyPrice(parts));
                break;
            case 3:
                buyerClient.printOnClient(bService.listBySellPrice(parts));
                break;
            case 4:
                buyerClient.printOnClient(bService.listByStockItems(parts));
                break;
            case 0:
                buyerClient.printOnClient("Canceling...");
                break;
            default:
                buyerClient.printOnClient("____ Invalid option! ____");
                break;
        }
    }

    public void buyerOption3(BuyerClientInterface buyerClient) throws RemoteException {
        try {
            if (sellingService.getSellHistory().isEmpty()) {
                buyerClient.printOnClient("____ No purchases to show ____");
                return;
            }
            for (AdvanceReceipt advSlip : sellingService.getSellHistory()) {
                if (buyerClient.getClientId().equals(advSlip.getName())) {
                    buyerClient.printOnClient(advSlip.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Error printing sales history" + e.getMessage());
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        String s;
        System.setSecurityManager(new SecurityManager());
        try {
//            String ipServer="192.168.1.83";
//            System.setProperty("java.rmi.server.hostname",ipServer);

            LocateRegistry.createRegistry(1099);
            Server server = new Server();
            Naming.rebind("server", server);

            System.out.println("----- Server Started -----");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
