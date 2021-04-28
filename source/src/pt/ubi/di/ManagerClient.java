package pt.ubi.di;

import pt.ubi.di.interfaces.ManagerClientInterface;
import pt.ubi.di.interfaces.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ManagerClient extends UnicastRemoteObject implements ManagerClientInterface {
    public ManagerClient() throws RemoteException {
        super();
    }

    public void printOnClient(String s) throws RemoteException {
        System.out.println("Message from the server: " + s);
    }

    public static String readString() {
        String s = "";

        System.out.println();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
            s = in.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }


    // TODO: Manager Menu
    public static void main(String[] args) {
        String option = "";
        System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(1199);
            ServerInterface server = (ServerInterface) Naming.lookup("server");
            ManagerClient managerClient = new ManagerClient();
            server.subscribeManager("Manager Client 1", managerClient);

            System.out.println("----- Connected to server -----");

            while (true) {
                System.out.println(
                        "----- Choose an action: -----\n" +
                                "1. Register a product\n" +
                                "2. Add a unit of existing product\n" +
                                "3. Remove a product\n" +
                                "4. List existing products\n" +
                                "5. List sells\n" +
                                "6. List buys\n" +
                                "0. Exit\n" +
                                "Your action: "
                );

                option = readString();

                switch(option) {
                    case "1":
                        System.out.println("1");
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
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Option");
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}