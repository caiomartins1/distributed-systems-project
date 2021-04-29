package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.Part;
import pt.ubi.di.utils.ReadUtils;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ManagerClient extends UnicastRemoteObject implements ManagerClientInterface {
    public ManagerClient() throws RemoteException {
        super();
    }

    public void printOnClient(String s) throws RemoteException {
        System.out.println(s);
    }

    public String readStringClient() throws RemoteException{
        return ReadUtils.readString();
    }

    public int readIntClient() throws RemoteException{
        return ReadUtils.readInt();
    }

    private static Part generatePart() {
        System.out.println("----- Adding a new Part -----");

        System.out.println("Enter Part type: ");
        String type = ReadUtils.readString();

        System.out.println("Enter Part buy price: ");
        float buyPrice = ReadUtils.readFloat();

        System.out.println("Enter Part sell price: ");
        float sellPrice = ReadUtils.readFloat();

        System.out.println("Enter Part minimum stock: ");
        int minStock = ReadUtils.readInt();

        return new Part(type, buyPrice, sellPrice, minStock);
    }

    private static Part xd() {
        System.out.println("----- Adding a new Part -----");

        System.out.println("Enter Part type: ");
        String type = ReadUtils.readString();

        System.out.println("Enter Part buy price: ");
        float buyPrice = ReadUtils.readFloat();

        System.out.println("Enter Part sell price: ");
        float sellPrice = ReadUtils.readFloat();

        System.out.println("Enter Part minimum stock: ");
        int minStock = ReadUtils.readInt();

        return new Part(type, buyPrice, sellPrice, minStock);
    }

    public static void main(String[] args) {
        String option = "";
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1199);
            ServerInterface server = (ServerInterface) Naming.lookup("server");
            server.subscribeManager("Manager Client 1", new ManagerClient());

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.println(
                        "----- Choose an action: -----\n" +
                                "1. Register a product\n" +
                                "2. Add a unit of existing product\n" + // buying from supplier
                                "3. Remove a product\n" +
                                "4. List existing products\n" +
                                "5. List sells\n" +
                                "6. List buys\n" +
                                "0. Exit\n" +
                                "Your action: "
                );

                option = ReadUtils.readString();

                switch (option) {
                    case "1":
                        server.managerOption1(generatePart());
                        break;
                    case "2":
                        server.managerOption2();
                        break;
                    case "3":
                        server.managerOption3();
                        break;
                    case "4":
                        System.out.println("4");
                        break;
                    case "5":
                        System.out.println("5");
                        break;
                    case "6":
                        System.out.println("6");
                        break;
                    case "0":
                        server.managerOption0();
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Option");
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Manager Client -> " + e.getMessage());
        }
    }
}