package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.*;
import pt.ubi.di.services.ManagerService;
import pt.ubi.di.utils.FileUtils;
import pt.ubi.di.utils.ReadUtils;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

// TODO: Not allow same name part
public class Server extends UnicastRemoteObject implements ServerInterface {
    private static ManagerClientInterface managerClient;
    private static BuyerClientInterface buyerClient;

    private static ManagerService mService;

    private static ArrayList<Part> parts;

    private static Purchase buyingService;
    private static Sale sellingService;

    private float storeBalance;//TODO

    public Server() throws RemoteException {
        super();
        mService = new ManagerService();
        buyingService = new Purchase();
        loadData();
    }

    public void subscribeManager(String name, ManagerClientInterface client) {
        System.out.println("Subscribing..." + name);
        managerClient = client;
    }

    public void managerOption1(Part p) throws RemoteException {

        managerClient.printOnClient("---- Registering new part ----");
        mService.registerPart(parts, p);
        managerClient.printOnClient("\n---- Part " + p.getType() + " added with success ----");

        try {
            managerClient.printOnClient("Do you wish to buy stock for this part?(y/n)");
            char decision = managerClient.readCharClient();
            int quantity = -1;
            if (decision == 'y') {
                do {//TODO not working correctly (not reading int throwing exception)
                    managerClient.printOnClient("Type quantity: ");
                    quantity = managerClient.readIntClient();
                    if (quantity > 0) {
                        buyingService.buySingleOrder(new Order(p, quantity));
                        managerClient.printOnClient("Purchase made");
                    } else {
                        managerClient.printOnClient("____Wrong value____");
                    }
                }while(quantity>0);
            } else {
                System.out.println("No purchase made for part");
            }
        }catch (Exception e){
            System.out.println("Error adding stock: "+e);
        }
        managerClient.printOnClient("\nPart \"" + p.getType() + "\" added with success");
    }

    public void managerOption2() throws RemoteException {

        //menu print
        managerClient.printOnClient("---- Buying from supplier ----");
        for(int i=0;i<parts.size();++i)//Print all parts available and their index on list
            managerClient.printOnClient(i+":------>"+parts.get(i).toStringClean());
        managerClient.printOnClient("choose what to buy, by number:\nType -2 to complete the order\nType -1 to cancel the order");

        ArrayList<Order> orders = new ArrayList<>();

        boolean eval=true;
        while(eval){// loop to decide quantities for parts
            managerClient.printOnClientNoNL("Type product: ");
            int option = managerClient.readIntClient();

            if(option==-1){
                managerClient.printOnClient("____Order canceled____");
                return;
            }
            else if (option==-2) {
                managerClient.printOnClient("Confirming Order.....");
                eval = false;
            }
            else{
                if(0<=option && option<parts.size()){
                    managerClient.printOnClientNoNL("Product: "+parts.get(option).getType()+" ");
                    managerClient.printOnClientNoNL("type quantity: ");
                    int quantity = managerClient.readIntClient();
                    Part part = parts.get(option);
                    System.out.println(part.toString());
                    if(quantity>0) {
                        orders.add(new Order(part, quantity));
                    }
                    else {
                        managerClient.printOnClient("____No valid number____");
                    }
                }
                else {
                    managerClient.printOnClient("____Wrong value____");
                }
            }
        }

        if(orders.isEmpty()) {
            managerClient.printOnClient("____No orders made, exiting menu____");
        }
        else {
            try {
                for (Order value : orders) {
                    managerClient.printOnClient(value.getPart().toStringClean());
                }
                AdvanceReceipt advSlip = buyingService.buyOrder(orders);
                managerClient.printOnClientNoNL("Checking order");
                Thread.sleep(500);
                managerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                managerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                managerClient.printOnClientNoNL(".");
                Thread.sleep(500);
                managerClient.printOnClientNoNL(".\n");
                managerClient.printOnClient("---- Success!!!! ----\n"+advSlip.toString());
            } catch (Exception e) {
                System.out.println("ERROR on Thread: " + e.getMessage());
            }
        }
    }

    public void managerOption5() {
        try {
            managerClient.printOnClient(buyingService.getPurchaseHistory().toString());
        } catch (Exception e) {
            System.out.println("Error printing purchase history" + e.getMessage());
        }
    }

    public void managerOption3() throws RemoteException {
        // TODO: Empty list fix
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
