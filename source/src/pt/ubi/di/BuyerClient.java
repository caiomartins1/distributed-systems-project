package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.utils.ReadUtils;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BuyerClient extends UnicastRemoteObject implements BuyerClientInterface {
    private String id;

    public BuyerClient() throws RemoteException {
        super();
        this.id = "Marcos";
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

    public void printOnClientNoNL(String s) throws RemoteException {
        System.out.print(s);
    }

    public char readCharClient() throws RemoteException {
        return ReadUtils.readChar();
    }

    public String getClientId() throws RemoteException {
        return this.id;
    }

    public static void main(String[] args) {

        System.setSecurityManager(new SecurityManager());

        try {

            Registry registry = LocateRegistry.getRegistry(1099);
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
                                "3. List sells\n" + // DOING (vitor)
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
                        System.out.println("3");
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
            System.out.println("Buyer Client ->" + e.getMessage());
        }

    }

}
