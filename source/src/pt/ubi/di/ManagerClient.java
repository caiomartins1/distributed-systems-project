package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.Part;
import pt.ubi.di.utils.ReadUtils;
import pt.ubi.di.utils.ShowInterfaces;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ManagerClient extends UnicastRemoteObject implements ManagerClientInterface {
    private String id;

    public ManagerClient() throws RemoteException {
        super();
        id = "Caio 2";
    }

    public void printOnClient(String s) throws RemoteException {
        System.out.println(s);
    }

    public String readStringClient() throws RemoteException {
        return ReadUtils.readString();
    }

    public int readIntClient() throws RemoteException {
        return ReadUtils.readInt();
    }

    public char readCharClient() throws RemoteException {
        return ReadUtils.readChar();
    }

    public void printOnClientNoNL(String s) throws RemoteException {
        System.out.print(s);
    }

    private static Part generatePart() {
        System.out.println("----- Adding a new Part -----");

        System.out.print("Enter Part type: ");
        String type = ReadUtils.readString();


        System.out.print("Enter Part buy price: ");
        float buyPrice = ReadUtils.readFloat();

        System.out.print("Enter Part sell price: ");
        float sellPrice = ReadUtils.readFloat();

        System.out.print("Enter Part minimum stock: ");
        int minStock = ReadUtils.readInt();

        boolean isInfoValid = !type.isEmpty() && (buyPrice > 0) && (sellPrice > 0) && (minStock >= 0);

        if (!isInfoValid) {
            System.out.println("Part not created -> Invalid Input");
            return null;
        }
        return new Part(type, buyPrice, sellPrice, minStock);

    }

    public String getClientId() {
        return this.id;
    }

    public static void main(String[] args) {
        String option = "";
        System.setSecurityManager(new SecurityManager());

        try {
            System.out.print("Type Server ip: ");
            String ipServer = ReadUtils.readString();
            String ownIp = ShowInterfaces.getIp();
            System.out.println("Own ip is: "+ownIp);
            System.setProperty("java.rmi.server.hostname",ownIp);
            Registry registry=LocateRegistry.getRegistry(ipServer,1099);
            ServerInterface server = (ServerInterface) registry.lookup("server");
            ManagerClient mClient = new ManagerClient();
            server.subscribeManager(mClient.getClientId(), mClient);

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.print(
                        "----- Store Manager Menu -----\n" +
                                "----- Choose an action: -----\n" +
                                "1. Register a product\n" + // DONE need valid (caio), int bug (vitor)
                                "2. Add stock to existing part(s)\n" + // DONE -> maybe formatting improve (vitor)
                                "3. Remove a part\n" +
                                "4. List existing parts\n" +
                                "5. List purchases to suppliers\n" + // DONE -> maybe formatting improve (vitor)
                                "6. List sells\n" + // DOING (vitor)
                                "0. Exit\n" + // DONE
                                "Your action:"
                );

                option = ReadUtils.readString();

                switch (option) {
                    case "1":
                        server.managerOption1(mClient, generatePart());
                        break;
                    case "2":
                        server.managerOption2(mClient);
                        break;
                    case "3":
                        server.managerOption3(mClient);
                        break;
                    case "4":
                        server.managerOption4(mClient);
                        break;
                    case "5":
                        server.managerOption5(mClient);
                        break;
                    case "6":
                        server.managerOption6(mClient);
                        break;
                    case "0":
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