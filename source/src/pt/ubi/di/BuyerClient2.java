package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.utils.ReadUtils;
import pt.ubi.di.utils.ShowInterfaces;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BuyerClient2 extends UnicastRemoteObject implements BuyerClientInterface {
    private String id;

    public BuyerClient2() throws RemoteException {
        super();
        this.id = "Caio";
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
            String ownIp = ShowInterfaces.getIp();
            System.out.println("Own ip is: "+ownIp);

            System.out.print("Type Server ip: ");
            String ipServer = ReadUtils.readString();
            if(ipServer.equals("")){
                ipServer = ownIp;
            }

            System.setProperty("java.rmi.server.hostname",ownIp);
            Registry registry = LocateRegistry.getRegistry(ipServer,1099);
            ServerInterface server = (ServerInterface) registry.lookup("server");
            BuyerClient2 bClient = new BuyerClient2();
            server.subscribeBuyer(bClient.getClientId(), bClient);

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.print(
                        "----- Buyer Manager Menu -----\n" +
                                "----- Choose an action: -----\n" +
                                "1. Buy a product\n" + // DONE need valid (caio), int bug (vitor)
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
                        server.buyerOption3(bClient);
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
