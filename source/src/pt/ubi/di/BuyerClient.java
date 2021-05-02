package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.utils.ReadUtils;
import pt.ubi.di.utils.ShowInterfaces;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class BuyerClient extends UnicastRemoteObject implements BuyerClientInterface {
    private String id;

    // Initialized the buyer client id
    public BuyerClient() throws RemoteException {
        super();
        this.id = "Junior";
    }

    // Enables server to print messages to the user
    public void printOnClient(String s) throws RemoteException {
        System.out.println(s);
    }

    // Enables server to read strings from client
    public String readStringClient() throws RemoteException {
        return ReadUtils.readString();
    }

    // Enables server to read int from client
    public int readIntClient() throws RemoteException {
        return ReadUtils.readInt();
    }

    // Enables server to print messages to the user (no new line for formatting purposes)
    public void printOnClientNoNL(String s) throws RemoteException {
        System.out.print(s);
    }

    // Enables server to read chat from client
    public char readCharClient() throws RemoteException {
        return ReadUtils.readChar();
    }

    // Enables server to get the client id
    public String getClientId() throws RemoteException {
        return this.id;
    }

    // Enables both localhost and connection from others PCs in the same network
    public static void main(String[] args) {

        System.setSecurityManager(new SecurityManager());

        try {
            String ownIp = ShowInterfaces.getIp();
            System.out.println("Own ip is: " + ownIp);

            System.out.print("Type Server ip: ");
            String ipServer = ReadUtils.readString();
            if (ipServer.equals("")) {
                ipServer = ownIp;
            }

            System.setProperty("java.rmi.server.hostname", ownIp);
            Registry registry = LocateRegistry.getRegistry(ipServer, 1099);
            ServerInterface server = (ServerInterface) registry.lookup("server");
            BuyerClient bClient = new BuyerClient();
            server.subscribeBuyer(bClient.getClientId(), bClient);

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.print(
                        "----- Buyer Manager Menu -----\n" +
                                "----- Choose an action: -----\n" +
                                "1. Buy a product\n" +
                                "2. List existing parts\n" +
                                "3. List sells\n" +
                                "0. Exit\n" +
                                "Your action:"
                );
                String option = "";
                option = ReadUtils.readString();

                switch (option) {
                    case "1":
                        server.buyerOption1(bClient);
                        break;
                    case "2":
                        server.buyerOption2(bClient);
                        break;
                    case "3":
                        server.buyerOption3(bClient);
                        break;
                    case "0":
                        server.buyerOption0(bClient);
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:

                        System.out.println("Invalid Option");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Buyer Client ->" + e.getMessage());
        }

    }

}
