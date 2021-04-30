package pt.ubi.di;

import pt.ubi.di.interfaces.BuyerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;
import pt.ubi.di.utils.ReadUtils;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class BuyerClient extends UnicastRemoteObject implements BuyerClientInterface{
    public BuyerClient() throws RemoteException{
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

    public void printOnClientNoNL(String s) throws RemoteException {
        System.out.print(s);
    }

    public char readCharClient() throws RemoteException{
        return ReadUtils.readChar();
    }

    public static void main(String[] args) {

        System.setSecurityManager(new SecurityManager());

        try{
            LocateRegistry.createRegistry(1199);
            ServerInterface server = (ServerInterface) Naming.lookup("server");
            server.subscribeBuyer("Buyer Client 1", new BuyerClient());

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.print(
                        "----- Buyer Manager Menu\n -----" +
                                "----- Choose an action: -----\n" +
                                "1. Buy a product\n" + // DONE need valid (caio), int bug (vitor)
                                "2. List existing parts\n" + // DOING (caio)
                                "3. List sells\n" + // DOING (vitor)
                                "0. Exit\n" + // DONE
                                "Your action:"
                );
                String option = "";
                option = ReadUtils.readString();

                switch (option) {
                    case "1":
                        server.buyerOption1();
                        break;
                    case "2":
                        System.out.println("2");
                        break;
                    case "3":
                        System.out.println("3");
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
        } catch (Exception e){
            System.out.println("Buyer Client ->" + e.getMessage());
        }

    }

}
