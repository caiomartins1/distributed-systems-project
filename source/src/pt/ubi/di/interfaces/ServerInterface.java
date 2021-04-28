package pt.ubi.di.interfaces;

import pt.ubi.di.model.Part;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
    public void subscribeManager(String name, ManagerClientInterface managerClient) throws RemoteException;
    public void managerOption1(Part p) throws RemoteException;

}
