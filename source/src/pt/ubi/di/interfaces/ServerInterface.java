package pt.ubi.di.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void subscribeManager(String name, ManagerClientInterface managerClient) throws RemoteException;

}
