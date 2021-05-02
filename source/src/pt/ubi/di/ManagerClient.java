package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.model.Part;
import pt.ubi.di.utils.ReadUtils;
import pt.ubi.di.utils.ShowInterfaces;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ManagerClient extends UnicastRemoteObject implements ManagerClientInterface {
    private String id;

    // Initializes the Manager ID
    public ManagerClient() throws RemoteException {
        super();
        id = "Caio";
    }

    // Enables server to display messages to clients
    public void printOnClient(String s) throws RemoteException {
        System.out.println(s);
    }

    // Enables server to read strings from client input
    public String readStringClient() throws RemoteException {
        return ReadUtils.readString();
    }

    // Enables server to read ints from client input
    public int readIntClient() throws RemoteException {
        return ReadUtils.readInt();
    }

    // Enables server to read chars from client input
    public char readCharClient() throws RemoteException {
        return ReadUtils.readChar();
    }

    // Enables server to display messages to clients (no new line for formatting purposes)
    public void printOnClientNoNL(String s) throws RemoteException {
        System.out.print(s);
    }

    // Read input from user and creates a Part object (used for managerOption1())
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

    // Enables the server to retrieve the client id
    public String getClientId() {
        return this.id;
    }

    // Enables both connection from localhost or others PCs in the same network
    public static void main(String[] args) {
        String option = "";
        System.setSecurityManager(new SecurityManager());

        try {
            String ownIp = ShowInterfaces.getIp();
            System.out.println("Own ip is: "+ownIp);

            System.out.print("Type Server ip: ");
            String ipServer = ReadUtils.readString();
            if(ipServer.equals("")){
                ipServer = ownIp;
            }

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
                                "1. Register a product\n" +
                                "2. Add stock to existing part(s)\n" +
                                "3. Remove a part\n" +
                                "4. List existing parts\n" +
                                "5. List purchases to suppliers\n" +
                                "6. List sells\n" +
                                "7. Store balance\n" +
                                "0. Exit\n" +
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
                    case "7":
                        server.managerOption7(mClient);
                        break;
                    case "0":
                        server.managerOption0(mClient);
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